package com.cjk.bakend.demo.controller;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.JwtUtils;
import com.cjk.bakend.demo.utils.RedisUtils;

import jakarta.annotation.Resource;

@Controller
public class UserController {
    
    @Resource
    UserService userService;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisUtils redisUtils;

    @Resource
    JwtUtils jwtUtils;

    @GetMapping("/login")
    
    public ResponseEntity<Result> login(@RequestParam("phone")String phone,@RequestParam("password")String password,@RequestParam("key")String key,
            @RequestParam("code")String code){

        if (!code.equals(redisUtils.hget("CaptchaCode",key))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.fail("验证码错误"));
        }
        redisUtils.hdel("CaptchaCode", key);
        //认证操作
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(phone, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //认证成功，颁发令牌
        String token = jwtUtils.generateToken(authentication.getName());
        redisUtils.set(token, authentication.getName(), 7*24*60*60);
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(token));
    }
    @GetMapping("/info")
    public ResponseEntity<Result> info(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.selectByPrimaryKey(userId);
        //System.out.println(user.getUserInformation().getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(user));
    }
}
