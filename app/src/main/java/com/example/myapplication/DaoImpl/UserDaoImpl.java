package com.example.myapplication.DaoImpl;

import com.example.myapplication.CommonUtils.MyConnections;
import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.PO.User;
import com.example.myapplication.VO.OrdinaryUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImpl implements com.example.myapplication.Dao.UserDao {

    @Override
    public Boolean insertAdmin(User admin) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[User] (UserEmail,UserName,UserPassword,RegisterTime,UserType)" +
                    "values ( ? , ? , ? , ? , ? )";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, admin.getUserEmail());
            psmt.setString(2, admin.getUserName());
            psmt.setString(3, admin.getUserPassword());
            psmt.setTimestamp(4, admin.getRegisterTime());
            psmt.setString(5, admin.getUserType());

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
    public Boolean insertOrdinaryUser(OrdinaryUser user) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "insert into master.dbo.[User] (UserEmail,UserName,UserPassword,RegisterTime,UserType)" +
                    "values ( ? , ? , ? , ? , ? );";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, user.getBaseInfo().getUserEmail());
            psmt.setString(2, user.getBaseInfo().getUserName());
            psmt.setString(3, user.getBaseInfo().getUserPassword());
            psmt.setTimestamp(4, user.getBaseInfo().getRegisterTime());
            psmt.setString(5, user.getBaseInfo().getUserType());
            boolean rs = psmt.execute();

            sql = "select UserID from master.dbo.[User] where UserName = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, user.getBaseInfo().getUserName());
            ResultSet tmp = psmt.executeQuery();
            Integer UserID = null;
            while(tmp.next()) {
                UserID = tmp.getInt("UserID");
            }
            sql = "insert into master.dbo.[OrdinaryUserData] (UserID,UserSature,UserWeight,UserBP,UserAge)" +
                    "values ( ? , ? , ? , ? , ? );";

            if(UserID == null || UserID <= 0) {
                return false;
            }
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            psmt.setDouble(2, user.getUserData().getUserSature());
            psmt.setDouble(3, user.getUserData().getUserWeight());
            psmt.setDouble(4, user.getUserData().getUserBP());
            psmt.setInt(5, user.getUserData().getUserAge());
            rs = rs & psmt.execute();

            conn.close();
            psmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteAdmin(Integer userID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[User] where UserID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, userID);

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
    public Boolean deleteOrdinary(Integer userID) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "delete from master.dbo.[User] where UserID = ? ;";

            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, userID);
            psmt.execute();

            conn.close();
            psmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateAdmin(User admin) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[User] set " +
                    "UserEmail = ? ," +
                    "UserName = ? ," +
                    "UserPassword = ? ," +
                    "UserType = ? " +
                    "where UserID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, admin.getUserEmail());
            psmt.setString(2, admin.getUserName());
            psmt.setString(3, admin.getUserPassword());
            psmt.setString(4, admin.getUserType());
            psmt.setInt(5, admin.getUserID());

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
    public Boolean updateOrdinary(OrdinaryUser user) {
        try {
            Connection conn = MyConnections.getConnection();
            String sql = "update master.dbo.[User] set " +
                    "UserEmail = ? ," +
                    "UserName = ? ," +
                    "UserPassword = ? ," +
                    "UserType = ? " +
                    "where UserID = ? ;";
            sql += "update master.dbo.[OrdinaryUserData] set " +
                    "UserSature = ? ," +
                    "UserWeight = ? ," +
                    "UserBP = ? ," +
                    "UserAge = ? " +
                    "where UserID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, user.getBaseInfo().getUserEmail());
            psmt.setString(2, user.getBaseInfo().getUserName());
            psmt.setString(3, user.getBaseInfo().getUserPassword());
            psmt.setString(4, user.getBaseInfo().getUserType());
            psmt.setInt(5, user.getBaseInfo().getUserID());

            psmt.setDouble(6, user.getUserData().getUserSature());
            psmt.setDouble(7, user.getUserData().getUserWeight());
            psmt.setDouble(8, user.getUserData().getUserBP());
            psmt.setInt(9, user.getUserData().getUserAge());
            psmt.setInt(10, user.getBaseInfo().getUserID());
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
    public List<User> findAll() {
        try {
            List<User> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User];";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new User( rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType")));
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
    public List<User> findAdmin() {
        try {
            List<User> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User] where UserType = 'Admin';";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new User( rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType")));
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
    public List<OrdinaryUser> findOrdinary() {
        try {
            List<OrdinaryUser> list = new LinkedList<>();
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User] " +
                    "inner join master.dbo.[OrdinaryUserData] on master.dbo.[OrdinaryUserData].UserID = master.dbo.[User].UserID " +
                    "where UserType = 'User';";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                list.add(new OrdinaryUser(new User(
                                rs.getInt("UserID"),
                                rs.getString("UserEmail"),
                                rs.getString("UserName"),
                                rs.getString("UserPassword"),
                                rs.getTimestamp("RegisterTime"),
                                rs.getString("UserType")),
                        new OrdinaryUserData(
                                rs.getInt("UserID"),
                                rs.getDouble("UserSature"),
                                rs.getDouble("UserWeight"),
                                rs.getDouble("UserBP"),
                                rs.getInt("UserAge")
                        )));
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
    public User findAdminByID(Integer UserID) {
        try {
            User result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User] where UserType = 'Admin' and UserID = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1,UserID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new User( rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType"));
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
    public OrdinaryUser findOrdinaryByID(Integer UserID) {
        try {
            OrdinaryUser result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User] " +
                    "inner join master.dbo.[OrdinaryUserData]on master.dbo.[OrdinaryUserData].UserID = master.dbo.[User].UserID " +
                    "where master.dbo.[User].UserType = 'User' " +
                    "and master.dbo.[OrdinaryUserData].UserID = ? ;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, UserID);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new OrdinaryUser(new User(
                        rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType")),
                        new OrdinaryUserData(
                                rs.getInt("UserID"),
                                rs.getDouble("UserSature"),
                                rs.getDouble("UserWeight"),
                                rs.getDouble("UserBP"),
                                rs.getInt("UserAge")
                        ));
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
    public User findAdminByName(String UserName) {
        try {
            User result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User] where UserType = 'Admin' and UserName = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,UserName);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new User( rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType"));
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
    public OrdinaryUser findOrdinaryByName(String UserName) {
        try {
            OrdinaryUser result = null;
            Connection conn = MyConnections.getConnection();
            String sql = "select * from master.dbo.[User]" +
                    "inner join master.dbo.[OrdinaryUserData] " +
                    "on master.dbo.[OrdinaryUserData].UserID = master.dbo.[User].UserID " +
                    "where UserType = 'User'" +
                    "and UserName = ?;";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, UserName);
            ResultSet rs = psmt.executeQuery();

            while(rs.next()) {
                result = new OrdinaryUser(new User(
                        rs.getInt("UserID"),
                        rs.getString("UserEmail"),
                        rs.getString("UserName"),
                        rs.getString("UserPassword"),
                        rs.getTimestamp("RegisterTime"),
                        rs.getString("UserType")),
                        new OrdinaryUserData(
                                rs.getInt("UserID"),
                                rs.getDouble("UserSature"),
                                rs.getDouble("UserWeight"),
                                rs.getDouble("UserBP"),
                                rs.getInt("UserAge")
                        ));
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
}
