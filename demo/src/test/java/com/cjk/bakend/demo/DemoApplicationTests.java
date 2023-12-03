package com.cjk.bakend.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjk.bakend.demo.controller.UserController;
import com.cjk.bakend.demo.mapper.UserMapper;
import com.cjk.bakend.demo.pojo.User;
import com.cjk.bakend.demo.service.UserService;

import jakarta.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

	@Resource
	UserMapper userMapper;

	@Resource
	UserController userController;

	@Resource
	UserService userService;

	@Test
	void contextLoads() {
		
	}
	@Test
	public void insertSelective(){
		
		User user  = userService.selectByPrimaryKey((long)1);
		System.out.println("返回的userid"+user.getUserInformation().getUserEmail());
	}

}
