package com.cjk.bakend.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjk.bakend.demo.config.PasswordEncoder;
import com.cjk.bakend.demo.constant.RsaProperties;
import com.cjk.bakend.demo.exception.MyException;
import com.cjk.bakend.demo.mapper.UserInformationMapper;
import com.cjk.bakend.demo.mapper.UserMapper;
import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;
import com.cjk.bakend.demo.utils.RSAUtils;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @Resource
    UserInformationMapper userInformationMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User selectByPhone(String phone) {
        User user = userMapper.selectByPhone(phone);
        return user;
    }
    
    @Override
    public User selectByPrimaryKey(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional
    public Result register(String phone,String password,String role) throws MyException{
        User user = userMapper.selectByPhone(phone);
        if(user != null){
            throw new MyException("用户已存在");
        }else{
            user = new User();
            user.setUserPhone(phone);
            //前端解码
            String pwd = RSAUtils.decryptByPrivate(password, RsaProperties.privateKey);
            //SpringSecurity 密码编码
            String encodePwd = passwordEncoder.encode(pwd);
            user.setUserPassword(encodePwd);
            user.setRole(role);
            int userId = userMapper.insertSelective(user);
            if(userId == -1){
                //这边要抛出异常，因为mysql并不会抛出异常
                throw new MyException("注册失败");
                //return "插入失败";
            }
            int checkResult = userMapper.checkUnique(user.getUserPhone());
            if(checkResult != 1){
                //这边要抛出异常
                throw new MyException("检查失败");
                
            }
            //成功注册插入用户信息
            userInformationMapper.insertByPrimaryKey((long)userId);
            return Result.succ("注册成功");
        }
    }
}
