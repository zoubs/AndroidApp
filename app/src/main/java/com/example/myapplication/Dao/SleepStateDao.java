package com.example.myapplication.Dao;

import com.example.myapplication.PO.FoodData;
import com.example.myapplication.PO.SleepState;

import java.sql.Timestamp;
import java.util.List;

public interface SleepStateDao {
    Boolean insert(SleepState sleepState);
    Boolean delete(Integer SleepStateID);
    Boolean update(SleepState sleepState);
    List<SleepState> findAll();
    List<SleepState> findByUserID(Integer UserID);
    SleepState findBySleepStateID(Integer SleepStateID);
    List<SleepState> findByDate(Timestamp date);
}
