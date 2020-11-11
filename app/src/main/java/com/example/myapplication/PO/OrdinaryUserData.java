package com.example.myapplication.PO;

import lombok.Data;

@Data
public class OrdinaryUserData {
    private Integer UserID;
    private Double UserSature;
    private Double UserWeight;
    private Double UserBP;
    private Integer UserAge;
    public OrdinaryUserData() {}
    public OrdinaryUserData(Integer userID, Double userSature, Double userWeight, Double userBP, Integer userAge) {
        this.UserID = userID;
        this.UserSature = userSature;
        this.UserWeight = userWeight;
        this.UserBP = userBP;
        this.UserAge = userAge;
    }
}
