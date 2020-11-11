package com.example.myapplication;

import android.app.Application;

public class GlobalInfo extends Application {
    private String userName;
    private String userPassword;
    private int userId;

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserPassword(String password) {
        this.userPassword = password;
    }
    public void setUserId (int userId) {
        this.userId = userId;
    }
}
