package com.example.myapplication.ui.feedback;

import java.util.Date;

import lombok.Data;

@Data
public class FeedBack {
    private int questionId;
    private String quesTittle;
    private String questionContext;
    private Date dateSubmit;
    private int isResolved;
    private int replierId;
    private Date dateReply;
    private String replyContext;
}
