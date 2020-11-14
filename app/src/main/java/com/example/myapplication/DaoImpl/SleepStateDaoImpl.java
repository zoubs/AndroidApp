package com.example.myapplication.DaoImpl;

import android.content.Intent;
import android.widget.ListView;

import com.example.myapplication.CommonUtils.MyConnections;
import com.example.myapplication.Dao.SleepStateDao;
import com.example.myapplication.PO.SleepState;
import com.example.myapplication.VO.DietVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SleepStateDaoImpl implements SleepStateDao {
    @Override
    public Boolean insert(SleepState sleepState) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[SleepState] (UserID,SleepDuration,SleepDate)" +
                    "values ( ? , ? , ? )";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, sleepState.getUserID());
            psmt.setLong(2, sleepState.getSleepDuration());
            psmt.setTimestamp(3, sleepState.getSleepDate());
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
    public Boolean delete(Integer SleepStateID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[SleepState] where SleepStateID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, SleepStateID);
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
    public Boolean update(SleepState sleepState) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[SleepState] set " +
                    "SleepDuration = ? ," +
                    "SleepDate = ?" +
                    "where SleepStateID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setLong(1, sleepState.getSleepDuration());
            psmt.setTimestamp(2, sleepState.getSleepDate());
            psmt.setInt(3, sleepState.getSleepStateID());
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
    public List<SleepState> findAll() {
        try {
            List<SleepState> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[SleepState] ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                list.add(new SleepState(
                        rs.getInt("SleepStateID"),
                        rs.getInt("UserID"),
                        rs.getLong("SleepDuration"),
                        rs.getTimestamp("SleepDate")
                ));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<SleepState> findByUserID(Integer UserID) {
        try {
            List<SleepState> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[SleepState] where UserID = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                list.add(new SleepState(
                        rs.getInt("SleepStateID"),
                        rs.getInt("UserID"),
                        rs.getLong("SleepDuration"),
                        rs.getTimestamp("SleepDate")
                ));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public SleepState findBySleepStateID(Integer SleepStateID) {
        try {
            SleepState result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[SleepState] where SleepStateID = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, SleepStateID);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                result = new SleepState(
                        rs.getInt("SleepStateID"),
                        rs.getInt("UserID"),
                        rs.getLong("SleepDuration"),
                        rs.getTimestamp("SleepDate")
                );
            }

            conn.close();
            psmt.close();
            rs.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SleepState> findByDate(Timestamp date) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(date.getTime());
        time.set(Calendar.MILLISECOND, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.HOUR_OF_DAY, 0);
        Timestamp startTime = new Timestamp(time.getTimeInMillis());
        time.add(Calendar.DATE, 1);
        Timestamp endTime = new Timestamp(time.getTimeInMillis());

        try {
            List<SleepState> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[SleepState] where SleepDate >= ? and SleepDate < ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setTimestamp(1, startTime);
            psmt.setTimestamp(2, endTime);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()) {
                list.add(new SleepState(
                        rs.getInt("SleepStateID"),
                        rs.getInt("UserID"),
                        rs.getLong("SleepDuration"),
                        rs.getTimestamp("SleepDate")
                ));
            }

            conn.close();
            psmt.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
