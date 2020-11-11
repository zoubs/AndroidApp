package com.example.myapplication.Dao;

import com.example.myapplication.PO.Feedback;
import com.example.myapplication.PO.FoodData;

import java.util.List;

public interface FoodDataDao {
    Boolean insert(FoodData foodData);
    Boolean delete(Integer FoodID);
    Boolean update(FoodData foodData);
    List<FoodData> findAll();
    FoodData findByFoodID(Integer FoodID);
    List<FoodData> findBySpecies(String Species);
    List<FoodData> findByNameLike(String keyword);
}
