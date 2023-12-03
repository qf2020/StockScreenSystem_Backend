package com.cjk.bakend.demo.service;

import com.cjk.bakend.demo.pojo.UserInformation;

public interface UserInformationService {
    // public Result register(String phone,String password,String role) throws MyException;
    public int changeInfo(UserInformation userInformation);
}
