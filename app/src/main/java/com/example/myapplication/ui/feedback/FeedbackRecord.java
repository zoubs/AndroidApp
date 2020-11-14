package com.example.myapplication.ui.feedback;

import lombok.Data;

@Data
public class FeedbackRecord {
    private String tittle;
    private String context;
    private String reply;
    private String status;

    public FeedbackRecord(){

    }
    public FeedbackRecord(String tittle, String context, String reply, String status) {
        this.tittle = tittle;
        this.context = context;
        this.reply = reply;
        this.status = status;
    }
}
