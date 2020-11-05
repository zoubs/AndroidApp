package com.example.myapplication.ui.recordsleep;

import java.util.Date;

import lombok.Data;

@Data
public class SleepState {
    private int userId;
    private int sleepRecordId;
    private float sleepLength;
    private Date date;
}
