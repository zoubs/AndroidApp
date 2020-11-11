package com.example.myapplication;

import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.DaoImpl.*;
import com.example.myapplication.PO.User;

import org.junit.Test;

import java.util.List;


public class DatabaseTest {
    @Test
    public void testUser() {
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> users = userDao.findAll();
    }
}
