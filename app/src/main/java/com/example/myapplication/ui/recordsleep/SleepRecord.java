package com.example.myapplication.ui.recordsleep;

import lombok.Data;

@Data
public class SleepRecord {
    private String index;
    private String sleepLength;

    public SleepRecord(){

    }
    public SleepRecord(String index, String length) {
        this.index = index;
        this.sleepLength = length;
    }
}
