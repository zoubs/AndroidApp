package com.example.myapplication.Dao;

import com.example.myapplication.PO.Diet;
import com.example.myapplication.PO.Feedback;
import com.example.myapplication.VO.DietVO;

import java.sql.Timestamp;
import java.util.List;

public interface DietDao {
    Boolean insert(Diet diet);
    Boolean delete(Integer DietID);
    Boolean update(Diet diet);
    List<DietVO> findAll();
    List<DietVO> findByUserID(Integer UserID);
    DietVO findByDietID(Integer dietID);
    List<DietVO> findTimeBetween(Integer UserID, Timestamp start, Timestamp end);
}
