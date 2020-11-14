package com.example.myapplication;

import android.app.Application;

import java.util.List;

public class GlobalInfo extends Application {
    private String userEmail;
    private String userName;
    private String userPassword;
    private Boolean isAdmin;
    private int userId;
    private List<String> allFood;


    private float bmi;

    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    //getUserId与系统方法重名
    public int getNowUserId() {
        return userId;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }
    public List<String> getAllFood(){
        return allFood;
    }
    public float getBmi(){
        return bmi;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserPassword(String password) {
        this.userPassword = password;
    }
    public void setNowUserId(int userId) {
        this.userId = userId;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setAllFood(List<String> allFood) {
        this.allFood = allFood;
    }
    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
}
