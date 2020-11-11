package com.example.myapplication.DaoImpl;

import com.example.myapplication.CommonUtils.MyConnections;
import com.example.myapplication.Dao.FoodDataDao;
import com.example.myapplication.PO.FoodData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FoodDataDaoImpl implements FoodDataDao {
    @Override
    public Boolean insert(FoodData foodData) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[FoodData] (FoodName,FoodSpecies,Calorie,UpdateTime,Image)" +
                    "values ( ? , ? , ? , ? , ?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,foodData.getFoodName());
            psmt.setString(2, foodData.getFoodSpecies());
            psmt.setDouble(3, foodData.getCalorie());
            psmt.setTimestamp(4, foodData.getUpdateTime());
            psmt.setBytes(5,foodData.getImage());

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
    public Boolean delete(Integer FoodID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[FoodData] where FoodID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, FoodID);
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
    public Boolean update(FoodData foodData) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[FoodData] set " +
                    "FoodName = ? ," +
                    "FoodSpecies = ? ," +
                    "Calorie = ? ," +
                    "UpdateTime = ? ," +
                    "Image = ? " +
                    "where FoodID = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,foodData.getFoodName());
            psmt.setString(2, foodData.getFoodSpecies());
            psmt.setDouble(3, foodData.getCalorie());
            psmt.setTimestamp(4, foodData.getUpdateTime());
            psmt.setBytes(5,foodData.getImage());

            psmt.setInt(6, foodData.getFoodID());

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
    public List<FoodData> findAll() {
        try {
            List<FoodData> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[FoodData];";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new FoodData(
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("FoodSpecies"),
                        rs.getDouble("Calorie"),
                        rs.getTimestamp("UpdateTime"),
                        rs.getBytes("Image")
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
    public FoodData findByFoodID(Integer FoodID) {
        try {
            FoodData result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[FoodData] where FoodID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, FoodID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new FoodData(
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("FoodSpecies"),
                        rs.getDouble("Calorie"),
                        rs.getTimestamp("UpdateTime"),
                        rs.getBytes("Image")
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
    public List<FoodData> findBySpecies(String Species) {
        try {
            List<FoodData> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[FoodData] where FoodSpecies = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, Species);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new FoodData(
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("FoodSpecies"),
                        rs.getDouble("Calorie"),
                        rs.getTimestamp("UpdateTime"),
                        rs.getBytes("Image")
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
    public List<FoodData> findByNameLike(String keyword) {
        try {
            List<FoodData> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[FoodData] where FoodName like ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, "%" + keyword + "%");
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new FoodData(
                        rs.getInt("FoodID"),
                        rs.getString("FoodName"),
                        rs.getString("FoodSpecies"),
                        rs.getDouble("Calorie"),
                        rs.getTimestamp("UpdateTime"),
                        rs.getBytes("Image")
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
