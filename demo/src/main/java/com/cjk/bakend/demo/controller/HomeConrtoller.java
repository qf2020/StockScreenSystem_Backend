package com.cjk.bakend.demo.controller;

import javax.sql.DataSource;

import org.apache.ibatis.javassist.bytecode.analysis.Executor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeConrtoller {
    @RequestMapping("/test")
    public String test(){
        return "hello word";
        
    }
}
