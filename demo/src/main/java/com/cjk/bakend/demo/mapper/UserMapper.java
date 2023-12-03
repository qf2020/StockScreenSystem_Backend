package com.cjk.bakend.demo.mapper;


import com.cjk.bakend.demo.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User user);

    int checkUnique(String userName);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    User selectByPhone(String userPhone);
}