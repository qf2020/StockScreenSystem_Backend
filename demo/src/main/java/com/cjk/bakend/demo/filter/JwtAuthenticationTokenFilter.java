package com.cjk.bakend.demo.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cjk.bakend.demo.pojo.SecurityUserDetails;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.JwtUtils;
import com.cjk.bakend.demo.utils.RedisUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

    private final static String AUTH_HEADER = "Authorization";
    private final static String AUTH_HEADER_TYPE = "Bearer";


    @Resource
    JwtUtils jwtUtils;

    @Resource
    UserService userService;

    @Resource
    RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //查看header是否是正确的
        String authHeader = request.getHeader(AUTH_HEADER);
        if(authHeader==null || !authHeader.startsWith(AUTH_HEADER_TYPE)){
            filterChain.doFilter(request, response);
            return;
        }
        //验证token是否符合要求
        String authToken = authHeader.split(" ")[1];
        if(!jwtUtils.isValidToken(authToken)){
            //log
            log.info("token不合法");
            filterChain.doFilter(request, response);
            return;
        }
        
        Long ttl = redisUtils.getExpire(authToken);
        //刷新token放到response中
        if(ttl!=null && ttl<24*60*60){
            response.addHeader(AUTH_HEADER, AUTH_HEADER_TYPE+authToken);
        }
        String userId = String.valueOf(redisUtils.get(authToken));
        User user = userService.selectByPrimaryKey(Long.valueOf(userId));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null,new SecurityUserDetails(user).getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //将认证过了凭证保存到security的上下文中以便于在程序中使用
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
    
}
