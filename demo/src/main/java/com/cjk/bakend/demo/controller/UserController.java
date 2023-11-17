package com.cjk.bakend.demo.controller;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.JwtUtils;
import jakarta.annotation.Resource;

@Controller
public class UserController {
    
    @Resource
    UserService userService;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    JwtUtils jwtUtils;

    @GetMapping("/login")
    
    public ResponseEntity<Result> login(@RequestParam("phone")String phone,@RequestParam("password")String password){

        //认证操作
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(phone, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //认证成功，颁发令牌
        String token = jwtUtils.generateToken(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(token));
    }
    @GetMapping("/info")
    public ResponseEntity<Result> info(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.selectByPrimaryKey(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(user));
    }
}
