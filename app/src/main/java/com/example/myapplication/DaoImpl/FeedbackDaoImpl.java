package com.example.myapplication.DaoImpl;

import com.example.myapplication.CommonUtils.MyConnections;
import com.example.myapplication.Dao.FeedbackDao;
import com.example.myapplication.PO.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class FeedbackDaoImpl implements FeedbackDao {

    @Override
    public Boolean insert(Feedback feedback) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[Feedback] (UserID,InquiryTime,QuestionTitle,QuestionContent," +
                    "IsResolved)" +
                    "values ( ? , ? , ? , ? , ? )";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, feedback.getUserID());
            psmt.setTimestamp(2, feedback.getInquiryTime());
            psmt.setString(3, feedback.getQuestionTitle());
            psmt.setString(4, feedback.getQuestionContent());
            psmt.setInt(5, feedback.getIsResolved());

            boolean rs = psmt.execute();

            conn.close();
            psmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Integer QuestionID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[Feedback] where QuestionID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, QuestionID);

            boolean rs = psmt.execute();

            conn.close();
            psmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(Feedback feedback) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[Feedback] set " +
                    "QuestionTitle = ? ," +
                    "QuestionContent = ? ," +
                    "IsResolved = ? ," +
                    "ReplierID = ? ," +
                    "FeedbackTime = ? ," +
                    "FeedbackContent = ? " +
                    "where QuestionID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, feedback.getQuestionTitle());
            psmt.setString(2, feedback.getQuestionContent());
            psmt.setInt(3, feedback.getIsResolved());
            if(feedback.getIsResolved() != 0) {

                psmt.setInt(4, feedback.getReplierID());
                psmt.setTimestamp(5, feedback.getFeedbackTime());
                psmt.setString(6, feedback.getFeedbackContent());
            }
            else {
                psmt.setInt(4, 0);
                psmt.setTimestamp(5, null);
                psmt.setString(6, null);
            }
            psmt.setInt(7,feedback.getQuestionID());
            boolean rs = psmt.execute();

            conn.close();
            psmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Feedback> findAll() {
        try {
            List<Feedback> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Feedback] ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new Feedback(rs.getInt("QuestionID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("InquiryTime"),
                        rs.getString("QuestionTitle"),
                        rs.getString("QuestionContent"),
                        rs.getInt("IsResolved"),
                        rs.getInt("ReplierID"),
                        rs.getTimestamp("FeedbackTime"),
                        rs.getString("FeedbackContent")));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Feedback> findBySubmitterID(Integer UserID) {
        try {
            List<Feedback> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Feedback] where UserID = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new Feedback(rs.getInt("QuestionID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("InquiryTime"),
                        rs.getString("QuestionTitle"),
                        rs.getString("QuestionContent"),
                        rs.getInt("IsResolved"),
                        rs.getInt("ReplierID"),
                        rs.getTimestamp("FeedbackTime"),
                        rs.getString("FeedbackContent")));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Feedback> findByReplierID(Integer UserID) {
        try {
            List<Feedback> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Feedback] where ReplierID = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new Feedback(rs.getInt("QuestionID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("InquiryTime"),
                        rs.getString("QuestionTitle"),
                        rs.getString("QuestionContent"),
                        rs.getInt("IsResolved"),
                        rs.getInt("ReplierID"),
                        rs.getTimestamp("FeedbackTime"),
                        rs.getString("FeedbackContent")));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Feedback> findResolved() {
        try {
            List<Feedback> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Feedback] where IsResolved = 1;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new Feedback(rs.getInt("QuestionID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("InquiryTime"),
                        rs.getString("QuestionTitle"),
                        rs.getString("QuestionContent"),
                        rs.getInt("IsResolved"),
                        rs.getInt("ReplierID"),
                        rs.getTimestamp("FeedbackTime"),
                        rs.getString("FeedbackContent")));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Feedback> findUnresolved() {
        try {
            List<Feedback> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Feedback] where IsResolved = 0;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new Feedback(rs.getInt("QuestionID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("InquiryTime"),
                        rs.getString("QuestionTitle"),
                        rs.getString("QuestionContent"),
                        rs.getInt("IsResolved"),
                        rs.getInt("ReplierID"),
                        rs.getTimestamp("FeedbackTime"),
                        rs.getString("FeedbackContent")));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
