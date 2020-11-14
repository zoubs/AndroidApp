package com.example.myapplication;

import com.example.myapplication.DaoImpl.*;
import com.example.myapplication.PO.User;
import com.example.myapplication.VO.DietVO;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class DatabaseTest {
    @Test
    public void testUser() {
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> users = userDao.findAll();
        User user = userDao.findUserByEmail("ans@ans.com");
    }

    @Test
    public void testFindAllFood() {
        FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
        List<String> foods = foodDataDao.findAllFoodName();
    }
    @Test
    public void testDietInsert() {
        FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
        List<String> foods = foodDataDao.findAllFoodName();
    }
    @Test
    public void testDeleteSleepState() {
        SleepStateDaoImpl sleepStateDao = new SleepStateDaoImpl();
        //sleepStateDao.delete(16);
    }
    /*@Test
    public void testDeleteUserInfo() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.deleteOrdinary()
    }*/
    @Test
    public void testSort() {   //怡会有问题
        String[] arr = {"张三", "李四", "王五", "赵六", "哈哈", "哈", "怡情"};
        List<String> list = Arrays.asList(arr);
        Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, CHINA_COMPARE);
    }
    @Test
    public void testDietByTime() {
        DietDaoImpl dietDao = new DietDaoImpl();
        List<DietVO> foods = dietDao.findTimeBetween(3, Timestamp.valueOf("2020-11-08 00:00:00"), Timestamp.valueOf("2020-11-08 23:29:59"));
    }
}
