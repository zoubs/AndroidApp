package com.example.myapplication.Dao;

import com.example.myapplication.PO.Feedback;

import java.util.List;

public interface FeedbackDao {
    Boolean insert(Feedback feedback);
    Boolean delete(Integer QuestionID);
    Boolean update(Feedback feedback);
    List<Feedback> findAll();
    List<Feedback> findBySubmitterID(Integer UserID);
    List<Feedback> findByReplierID(Integer UserID);
    List<Feedback> findResolved();
    List<Feedback> findUnresolved();
}
