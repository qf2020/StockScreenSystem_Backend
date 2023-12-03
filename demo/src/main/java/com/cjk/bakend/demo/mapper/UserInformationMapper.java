package com.cjk.bakend.demo.mapper;

import com.cjk.bakend.demo.pojo.UserInformation;

public interface UserInformationMapper {
    int insertByPrimaryKey(Long userId); 

    int updateByPrimaryKeySelective(UserInformation userInformation);
} 
