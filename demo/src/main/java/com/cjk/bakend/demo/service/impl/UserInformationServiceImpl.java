package com.cjk.bakend.demo.service.impl;

import com.cjk.bakend.demo.mapper.UserInformationMapper;
import com.cjk.bakend.demo.pojo.UserInformation;
import com.cjk.bakend.demo.service.UserInformationService;

import jakarta.annotation.Resource;

public class UserInformationServiceImpl implements UserInformationService{
    
    @Resource
    UserInformationMapper userInformationMapper;

    @Override
    public int changeInfo(UserInformation userInformation) {
        return userInformationMapper.updateByPrimaryKeySelective(userInformation);
    }

}
