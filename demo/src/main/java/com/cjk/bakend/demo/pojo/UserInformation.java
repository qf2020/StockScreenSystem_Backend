package com.cjk.bakend.demo.pojo;

import java.io.Serializable;


public class UserInformation implements Serializable{
    private String userName;

    private static final long serialVersionUID = 1L;

    public void setUserName(String userName) {
        this.userName = userName == null? null : userName.trim();
    }

    public String getUserName() {
        return userName;
    }
}
