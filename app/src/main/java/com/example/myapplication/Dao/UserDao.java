package com.example.myapplication.Dao;

import android.service.autofill.UserData;

import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.PO.User;
import com.example.myapplication.VO.OrdinaryUser;

import java.util.List;

public interface UserDao {
    Boolean insertAdmin(User admin);
    Boolean insertOrdinaryUser(User user);

    Boolean insertUserDetailInfo(OrdinaryUserData userData);
    OrdinaryUserData findUserDetailInfo(Integer userId);
    Boolean deleteAdmin(Integer userID);
    Boolean deleteOrdinary(Integer userID);

    Boolean updateAdmin(User admin);
    Boolean updateOrdinary(OrdinaryUser user);
    Boolean updateUserBaseInfo(User user);


    User findUserByEmail(String userEmail);
    User findUserByName(String userName);
    List<User> findAll();
    List<User> findAdmin();
    List<OrdinaryUser> findOrdinary();
    User findAdminByID(Integer UserID);
    OrdinaryUser findOrdinaryByID(Integer UserID);
    User findAdminByName(String UserName);
    OrdinaryUser findOrdinaryByName(String UserName);
}
