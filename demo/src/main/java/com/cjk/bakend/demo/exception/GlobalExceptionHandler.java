package com.cjk.bakend.demo.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSON;
import com.cjk.bakend.demo.pojo.Result;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    public GlobalExceptionHandler(){}

    

    // 定义一个通用的异常处理方法，用来处理所有的异常
    @ExceptionHandler(Exception.class)
    public Result handleException(HttpServletRequest req,Exception e) {
        Result result = Result.fail(e.getMessage());
        return this.printLogAndReturn(req,result,e);
    }

    // 定义一个空指针的异常处理方法
    @ExceptionHandler(value =NullPointerException.class)
    public Result handleException(HttpServletRequest req, NullPointerException e){
        Result result = Result.fail(e.getMessage());
        return this.printLogAndReturn(req,result,e);
    }


    

    private Result printLogAndReturn(HttpServletRequest request, Result result, Exception e) {
        String requestUrl = request.getRequestURL().toString() + request.getParameterMap().toString();
        log.error("<-异常返回-> 请求接口:{} | 异常时间:{} | 异常结果:{}", new Object[]{requestUrl, System.currentTimeMillis(), JSON.toJSONString(result)});
        log.error("<--异常堆栈信息-->");
        log.error(e.getStackTrace().toString());
        return result;
    }

}
