package com.example.myapplication;

import android.util.Log;

import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyUsers {
    private String url;
    private String user;
    private String pswd;
    private String userPassword;

    public MyUsers(String url, String user, String pswd) {
        this.url = url;
        this.user = user;
        this.pswd = pswd;
    }

    public String getPassword(String email) {
        final String finalEmail = email;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接到mysql
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection(url,user,pswd);
                    String sql = "select userPassword from users where userEmail =";
                    sql += ("\"" + finalEmail +"\"");
                    Statement st = (Statement)cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    //获取密码
                    while(rs.next()) {
                        userPassword = rs.getString("userPassword");
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    Log.d("success","链接成功");
                } catch (ClassNotFoundException e) {
                    Log.d("error","链接失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return userPassword;
    }

    public boolean isMatchPassword(String userEmail,String userPassword) {
        if(userEmail.equals(getPassword(userEmail))) {
            return true;
        }
        else {
            return false;
        }
    }
}
