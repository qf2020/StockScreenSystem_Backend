package com.cjk.bakend.demo.service;


import com.cjk.bakend.demo.exception.MyException;
import com.cjk.bakend.demo.pojo.Result;
import com.cjk.bakend.demo.pojo.User;

public interface UserService {
    public User selectByPhone(String phone);
    public User selectByPrimaryKey(Long userId);
    public Result register(String phone,String password,String role) throws MyException;
    public int changePassword(User user);
}
