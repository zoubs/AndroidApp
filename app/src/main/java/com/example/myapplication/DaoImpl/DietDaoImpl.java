package com.example.myapplication.DaoImpl;

import com.example.myapplication.CommonUtils.MyConnections;
import com.example.myapplication.Dao.DietDao;
import com.example.myapplication.PO.Diet;
import com.example.myapplication.VO.DietVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DietDaoImpl implements DietDao {
    @Override
    public Boolean insert(Diet diet) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[Diet] (UserID,MealTime,FoodID,FoodNumber)" +
                    "values ( ? , ? , ? , ?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, diet.getUserID());
            psmt.setTimestamp(2, diet.getMealTime());
            psmt.setInt(3, diet.getFoodID());
            psmt.setDouble(4, diet.getFoodNumber());
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
    public Boolean delete(Integer DietID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[Diet] " +
                    "where DietID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, DietID);
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
    public Boolean update(Diet diet) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[Diet] set " +
                    "MealTime = ?," +
                    "FoodID = ?," +
                    "FoodNumber = ? " +
                    "where DietID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setTimestamp(1, diet.getMealTime());
            psmt.setInt(2, diet.getFoodID());
            psmt.setDouble(3, diet.getFoodNumber());
            psmt.setInt(4, diet.getDietID());
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
    public List<DietVO> findAll() {
        try {
            List<DietVO> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Diet] inner join master.dbo.[FoodData] " +
                    "on master.dbo.[Diet].FoodID = master.dbo.[FoodData].FoodID";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new DietVO(
                        rs.getInt("DietID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("MealTIme"),
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getDouble("FoodNumber")
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
    public List<DietVO> findByUserID(Integer UserID) {
        try {
            List<DietVO> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Diet] inner join master.dbo.[FoodData] " +
                    "on master.dbo.[Diet].FoodID = master.dbo.[FoodData].FoodID " +
                    "where UserID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new DietVO(
                        rs.getInt("DietID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("MealTIme"),
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getDouble("FoodNumber")
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
    public DietVO findByDietID(Integer dietID) {
        try {
            DietVO result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Diet] inner join master.dbo.[FoodData] " +
                    "on master.dbo.[Diet].FoodID = master.dbo.[FoodData].FoodID " +
                    "where DietID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, dietID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new DietVO(
                        rs.getInt("DietID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("MealTIme"),
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getDouble("FoodNumber")
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
    public List<DietVO> findTimeBetween(Integer UserID, Timestamp start, Timestamp end) {
        try {
            List<DietVO> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[Diet] inner join master.dbo.[FoodData] " +
                    "on master.dbo.[Diet].FoodID = master.dbo.[FoodData].FoodID " +
                    "where UserID= ? " +
                    "and MealTime >= ? and MealTime <= ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            psmt.setTimestamp(2, start);
            psmt.setTimestamp(3, end);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new DietVO(
                        rs.getInt("DietID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("MealTIme"),
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getDouble("FoodNumber")
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
