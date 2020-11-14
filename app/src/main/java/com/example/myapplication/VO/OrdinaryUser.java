package com.example.myapplication.VO;

import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.PO.User;

import lombok.Data;

@Data
public class OrdinaryUser {
    private User baseInfo;
    private OrdinaryUserData userData;
    public OrdinaryUser() {}
    public OrdinaryUser(User baseInfo, OrdinaryUserData userData) {
        this.baseInfo = baseInfo;
        this.userData = userData;
    }
    public OrdinaryUser(User baseInfo){
        this.baseInfo = baseInfo;
    }
}
