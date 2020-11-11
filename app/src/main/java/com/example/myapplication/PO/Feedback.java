package com.example.myapplication.PO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Feedback {
    private Integer QuestionID;
    private Integer UserID;
    private Timestamp InquiryTime;
    private String QuestionTitle;
    private String QuestionContent;
    private Integer IsResolved;
    private Integer ReplierID;
    private Timestamp FeedbackTime;
    private String FeedbackContent;
    public Feedback() { }
    public Feedback( Integer QuestionID,
             Integer UserID,
             Timestamp InquiryTime,
             String QuestionTitle,
             String QuestionContent,
             Integer IsResolved,
             Integer ReplierID,
             Timestamp FeedbackTime,
             String FeedbackContent) {
        this.QuestionID = QuestionID;
        this.UserID = UserID;
        this.InquiryTime = InquiryTime;
        this.QuestionTitle = QuestionTitle;
        this.QuestionContent = QuestionContent;
        this.IsResolved = IsResolved;
        this.ReplierID = ReplierID;
        this.FeedbackTime = FeedbackTime;
        this.FeedbackContent = FeedbackContent;
    }
}
