package com.example.myapplication.Dao;

import com.example.myapplication.PO.Feedback;
import com.example.myapplication.PO.FoodData;

import java.util.List;
import java.util.Map;

public interface FoodDataDao {
    Boolean insert(FoodData foodData);
    Boolean delete(Integer FoodID);
    Boolean deleteByName(String foodName);
    Boolean update(FoodData foodData);
    //Map<String, Integer> findFoodIdAndName();
    List<FoodData> findAll();
    List<String> findAllFoodName();
    FoodData findByFoodID(Integer FoodID);
    FoodData findByFoodName(String foodName);
    List<FoodData> findBySpecies(String Species);
    List<FoodData> findByNameLike(String keyword);
}
