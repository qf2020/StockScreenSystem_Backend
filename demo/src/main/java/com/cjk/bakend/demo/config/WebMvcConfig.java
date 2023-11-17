package com.cjk.bakend.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // addMapping表示要处理的请求地址
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("*")//允许所有源都可以访问
            .allowedHeaders("*")
            .allowCredentials(false)//不允许cookies
            .exposedHeaders("")//控制哪些请求头可以被用户访问
            .maxAge(3600);//预检请求结果缓存的时间
    }
}
