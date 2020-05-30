package com.example.myapplication.ui.user;

public class UserInformation {
    private String name;
    private String email;
    private boolean is_admin;

    public UserInformation(String name, String email, boolean is_admin) {
        this.email = email;
        this.name = name;
        this.is_admin = is_admin;
    }

    public boolean getIsAdmin() {
        return is_admin;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
