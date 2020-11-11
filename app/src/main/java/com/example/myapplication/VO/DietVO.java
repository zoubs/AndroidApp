package com.example.myapplication.VO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DietVO {
    private Integer DietID;
    private Integer UserID;
    private Timestamp MealTime;
    private Integer FoodID;
    private String FoodName;
    private Double FoodNumber;
    public DietVO() {}
    public DietVO(Integer DietID, Integer UserID,Timestamp MealTime,Integer FoodID,String FoodName,Double FoodNumber) {
        this.DietID = DietID;
        this.UserID = UserID;
        this.MealTime = MealTime;
        this.FoodID = FoodID;
        this.FoodName = FoodName;
        this.FoodNumber = FoodNumber;
    }
}
