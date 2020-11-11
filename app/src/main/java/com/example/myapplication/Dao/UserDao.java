package com.example.myapplication.Dao;

import android.service.autofill.UserData;

import com.example.myapplication.PO.User;
import com.example.myapplication.VO.OrdinaryUser;

import java.util.List;

public interface UserDao {
    Boolean insertAdmin(User admin);
    Boolean insertOrdinaryUser(OrdinaryUser user);
    Boolean deleteAdmin(Integer userID);
    Boolean deleteOrdinary(Integer userID);
    Boolean updateAdmin(User admin);
    Boolean updateOrdinary(OrdinaryUser user);
    List<User> findAll();
    List<User> findAdmin();
    List<OrdinaryUser> findOrdinary();
    User findAdminByID(Integer UserID);
    OrdinaryUser findOrdinaryByID(Integer UserID);
    User findAdminByName(String UserName);
    OrdinaryUser findOrdinaryByName(String UserName);
}
