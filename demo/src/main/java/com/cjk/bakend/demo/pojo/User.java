package com.cjk.bakend.demo.pojo;

import java.io.Serializable;


public class User implements Serializable {
    private Long userId;

    private String userPhone;

    private String userPassword;

    private UserInformation userInformation;

    private String role;

    private Integer userVersion;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }

    public Integer getUserVersion() {
        return userVersion;
    }
    
    public void setUserVersion(Integer userVersion) {
        this.userVersion = userVersion;
    }
}