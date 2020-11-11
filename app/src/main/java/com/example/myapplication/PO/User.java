package com.example.myapplication.PO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class User {
    private Integer UserID;
    private String UserEmail;
    private String UserName;
    private String UserPassword;
    private Timestamp RegisterTime;
    private String UserType;

    public User() { }
    public User(Integer userID, String userEmail, String userName,
                String userPassword, Timestamp registerTime, String userType) {
        this.UserID = userID;
        this.UserEmail = userEmail;
        this.UserName = userName;
        this.UserPassword = userPassword;
        this.RegisterTime = registerTime;
        this.UserType = userType;
    }
}
