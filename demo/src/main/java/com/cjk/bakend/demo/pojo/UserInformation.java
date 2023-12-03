package com.cjk.bakend.demo.pojo;

import java.io.Serializable;


public class UserInformation implements Serializable{
    private Long userInformationId;

    private String userName;

    private int userSex;

    private int userAge;

    private String userEmail;

    private static final long serialVersionUID = 1L;

    public void setUserName(String userName) {
        this.userName = userName == null? null : userName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserInformationId(Long userInformationId) {
        this.userInformationId = userInformationId;
    }

    public Long getUserInformationId() {
        return userInformationId;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
    public int getUserAge() {
        return userAge;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail() {
        return userEmail;
    }
}
