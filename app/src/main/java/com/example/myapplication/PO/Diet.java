package com.example.myapplication.PO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Diet {
    private Integer DietID;
    private Integer UserID;
    private Timestamp MealTime;
    private Integer FoodID;
    private Double FoodNumber;
    public Diet() {}
    public Diet(Integer DietID, Integer UserID,Timestamp MealTime,Integer FoodID,Double FoodNumber) {
        this.DietID = DietID;
        this.UserID = UserID;
        this.MealTime = MealTime;
        this.FoodID = FoodID;
        this.FoodNumber = FoodNumber;
    }
}
