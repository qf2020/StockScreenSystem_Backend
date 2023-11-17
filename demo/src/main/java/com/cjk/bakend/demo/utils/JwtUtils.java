package com.cjk.bakend.demo.utils;

import java.util.Date;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@ConfigurationProperties("jwt")
public class JwtUtils {
    private long expire;
    private String secret;
    private String headers;

    //生成jwt
    public String generateToken(String userName){
        Date nowdate = new Date();
        Date expirDate = new Date(nowdate.getTime()+1000*604800);
        System.out.println("过期时间："+expirDate);
        return Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setSubject(userName)
                .setIssuedAt(nowdate)
                .setExpiration(expirDate)
                .signWith(SignatureAlgorithm.HS512, "abcdefghabcdefghabcdefghabcdefghdadada")
                .compact();
    }
    //解析jwt
    public Claims getClaimsByToken(String token){
        try {
            return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) { //因为解析可能出现违法的token或者过期的
            return null;
        }
        
        
    }

    public String getSubjectByToken(String token){
        return Jwts.parser()
            .setSigningKey("abcdefghabcdefghabcdefghabcdefghdadada")
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    //判断是否合法
    public boolean isValidToken(String token){
        try {
            Jwts.parser().setSigningKey("abcdefghabcdefghabcdefghabcdefghdadada").parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //可以日志处理

        }
        return false;
    }
    //判断是否过期
    //根据解密之后的claims判断是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
