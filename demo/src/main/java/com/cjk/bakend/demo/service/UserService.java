package com.cjk.bakend.demo.service;


import com.cjk.bakend.demo.pojo.User;

public interface UserService {
    public User selectByPhone(String phone);
    public User selectByPrimaryKey(Long userId);
}
