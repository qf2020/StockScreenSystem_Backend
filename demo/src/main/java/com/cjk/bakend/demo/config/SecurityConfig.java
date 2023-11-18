package com.cjk.bakend.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cjk.bakend.demo.filter.JwtAuthenticationTokenFilter;
import com.cjk.bakend.demo.handler.MyAccessDeniedHandler;
import com.cjk.bakend.demo.handler.UnAuthenticationHandler;
import com.cjk.bakend.demo.provider.JwtAuthenticationProvider;


@Configuration
@EnableWebSecurity

public class SecurityConfig {
    
    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    UnAuthenticationHandler unAuthenticationHandler;

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/login","captcha").permitAll()
            .anyRequest().authenticated());

        //添加自定义provider
        httpSecurity.authenticationProvider(jwtAuthenticationProvider());
        //添加自定义filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling(authorize -> authorize
            .authenticationEntryPoint(unAuthenticationHandler));   

        httpSecurity.exceptionHandling(authorize -> authorize
            .accessDeniedHandler(myAccessDeniedHandler)); 
        return httpSecurity.build();
    }

}
