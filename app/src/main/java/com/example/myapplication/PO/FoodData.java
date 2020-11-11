package com.example.myapplication.PO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FoodData {
    private Integer FoodID;
    private String FoodName;
    private String FoodSpecies;
    private Double Calorie;
    private Timestamp UpdateTime;
    private byte[] Image;
    public FoodData() { }
    public FoodData(Integer FoodID, String FoodName, String FoodSpecies, Double Calorie, Timestamp UpdateTime, byte[] Image) {
        this.FoodID = FoodID;
        this.FoodName = FoodName;
        this.FoodSpecies = FoodSpecies;
        this.Calorie = Calorie;
        this.UpdateTime = UpdateTime;
        this.Image = Image;
    }
}
