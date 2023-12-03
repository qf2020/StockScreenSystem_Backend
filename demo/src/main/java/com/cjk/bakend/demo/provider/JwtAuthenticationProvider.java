package com.cjk.bakend.demo.provider;



import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;

import jakarta.annotation.Resource;


public class JwtAuthenticationProvider implements AuthenticationProvider{

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    UserService userService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if(!userDetails.isEnabled()){
            throw new BadCredentialsException ("用户没找到");
        }else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }else{
            User user = userService.selectByPhone(userName);
            Authentication authentication2 = new UsernamePasswordAuthenticationToken(user.getUserId(), password,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication2);
            return authentication2;
        }
    }

    //supports函数用来指明该Provider是否适用于该类型的认证，如果不合适，则寻找另一个Provider进行验证处理
    //还得看看
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
    
}
