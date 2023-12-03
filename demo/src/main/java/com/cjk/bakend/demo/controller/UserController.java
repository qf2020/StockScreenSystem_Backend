package com.cjk.bakend.demo.controller;




import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjk.bakend.demo.exception.MyException;
import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.pojo.UserInformation;
import com.cjk.bakend.demo.service.UserInformationService;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.JwtUtils;
import com.cjk.bakend.demo.utils.RedisUtils;

import jakarta.annotation.Resource;

@Controller
public class UserController {
    
    @Resource
    UserService userService;

    @Resource
    UserInformationService userInformationService;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisUtils redisUtils;

    @Resource
    JwtUtils jwtUtils;


    @GetMapping("/login")
    public ResponseEntity<Result> login(@RequestParam("username")String phone,@RequestParam("password")String password,@RequestParam("key")String key,
            @RequestParam("code")String code,@RequestParam("role")String role){

        //通过redis判断图形验证码的正确性
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
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(user));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Result> logout(@RequestHeader("Authorization") String token){
        redisUtils.del(token);
        return ResponseEntity.ok().body(Result.succ("用户退出登陆成功"));
    }


    //修改意见：应该弄到service中处理
    //mapper中insert接口还要更改。还得处理注册账户，用户信息不能为空的情况
    //
    @PostMapping(value="/register")
    public ResponseEntity<Result> register(@RequestBody Map<String,String> map){

        String phone = map.get("username");
        String password = map.get("password");
        String role = map.get("role");
        String code = map.get("code");
        String key = map.get("key");
        if (!code.equals(redisUtils.hget("CaptchaCode",key))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.fail("验证码错误"));
        }
        redisUtils.hdel("CaptchaCode", key);
        
        return ResponseEntity.ok().body(userService.register(phone, password, role));
    }
    
    //信息更改功能
    @PostMapping("/changePassword")
    public ResponseEntity<Result> changePassword(@RequestParam("password") String password){
        //可改进地方，比如是否需要判断密码与原密码一样
        //还未使用乐观锁
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.selectByPrimaryKey(userId);

        user.setUserPassword(password);
        int updateResult = userService.changePassword(user);
        if(updateResult!=-1){
            return ResponseEntity.ok().body(Result.succ("更改信息成功"));
        }else{
            throw new MyException("更新信息失败");
            //return ResponseEntity.ok().body(Result.fail("更新信息失败"));
        }
    }

    @PostMapping("/changeInfo")
    public ResponseEntity<Result> changeInfo(@RequestBody UserInformation userInformation){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int updateResult = userInformationService.changeInfo(userInformation);
        if(updateResult!=-1){
            return ResponseEntity.ok().body(Result.succ("更新信息成功"));
        }else{
            throw new MyException("更新信息失败");
        }
    }
}
