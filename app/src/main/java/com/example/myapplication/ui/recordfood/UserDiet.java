package com.example.myapplication.ui.recordfood;

import java.util.Date;

import lombok.Data;

@Data
public class UserDiet {
    private int userId;
    private int foodId;
    private Date date;
    private String type;    //早，中，晚饭
    //private int dietId;
    private String carbohydrate;   //碳水
    private String fat;  //脂肪
    private String protein; //蛋白质
}
