package com.example.myapplication.PO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SleepState {
    private Integer SleepStateID;
    private Integer UserID;
    private Long SleepDuration;
    private Timestamp SleepDate;
    public SleepState() {}
    public SleepState(Integer SleepStateID, Integer UserID, Long SleepDuration, Timestamp SleepState) {
        this.SleepStateID = SleepStateID;
        this.UserID = UserID;
        this.SleepDuration = SleepDuration;
        this.SleepDate = SleepState;
    }
}
