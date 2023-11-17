package com.cjk.bakend.demo.service.impl;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.cjk.bakend.demo.mapper.UserMapper;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @Override
    public User selectByPhone(String phone) {
        User user = userMapper.selectByPhone(phone);
        return user;
    }
    
    @Override
    public User selectByPrimaryKey(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }
}
