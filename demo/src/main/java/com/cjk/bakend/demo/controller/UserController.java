package com.cjk.bakend.demo.controller;




import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjk.bakend.demo.constant.RsaProperties;
import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.JwtUtils;
import com.cjk.bakend.demo.utils.RSAUtils;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<Result> login(@RequestParam("phone")String phone,@RequestParam("password")String password,@RequestParam("key")String key,
            @RequestParam("code")String code){

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
        //System.out.println(user.getUserInformation().getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(Result.succ(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Result> logout(@RequestHeader("Authorization") String token){
        redisUtils.del(token);
        return ResponseEntity.ok().body(Result.succ("用户退出登陆成功"));
    }


    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestParam("phone")String phone,@RequestParam("password")String password,@RequestParam("role")String role){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            //查询当前账号是否存在
            User user = userService.selectByPhone(phone);
            if (user !=null){
                return ResponseEntity.badRequest().body(Result.fail("当前账号已经存在，请更换账号"));
            }else{
                user = new User();
                user.setUserPhone(phone);
                //前端解码
                String pwd = RSAUtils.decryptByPrivate(password, RsaProperties.privateKey);
                //SpringSecurity 密码编码
                String encodePwd = passwordEncoder.encode(pwd);
                user.setUserPassword(encodePwd);
                user.setRole(role);
            }
            //SpringSecurity 密码编码
            //注册
            try {
                userService.insertSelective(user);
                return ResponseEntity.ok().body(Result.succ("创建成功"));//这里应该返回token的
            } catch (Exception e) {
                // TODO: handle exception
                return ResponseEntity.badRequest().body(Result.fail("系统异常"));
            }
            

        }finally {
            lock.unlock();
        }

    }

    
}
