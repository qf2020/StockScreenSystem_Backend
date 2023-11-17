package com.cjk.bakend.demo.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cjk.bakend.demo.mapper.UserMapper;
import com.cjk.bakend.demo.pojo.SecurityUserDetails;
import com.cjk.bakend.demo.pojo.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByPhone(username);    
        return new SecurityUserDetails(user);
    } 
    
}
