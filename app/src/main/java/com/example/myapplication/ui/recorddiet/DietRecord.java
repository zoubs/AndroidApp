package com.example.myapplication.ui.recorddiet;

import lombok.Data;

@Data
public class DietRecord {
    private String foodName;
    private String foodNumber;

    public DietRecord(){

    }
    public DietRecord(String foodName, String foodNumber) {
        this.foodName = foodName;
        this.foodNumber = foodNumber;
    }
}
