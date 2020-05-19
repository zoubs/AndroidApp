package com.example.myapplication;

import android.util.Log;

import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyUsers {
    private String url;             //MySql的URL
    private String user;            //MySql登录名
    private String pswd;            //MySql登录密码
    private String userPassword;    //获取的用户密码

    //构造函数初始化
    public MyUsers(String url, String user, String pswd) {
        this.url = url;
        this.user = user;
        this.pswd = pswd;
    }

    //获取用户密码
    //email     需要获取密码的用户名
    //return    密码/null
    public String getPassword(String email) {
        MyThreadReturn myThreadReturn = new MyThreadReturn(email);
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userPassword = myThreadReturn.getReturnPassword();

        if(userPassword!=null && !userPassword.isEmpty())
            Log.d("return",userPassword);

        return userPassword;
    }

    //账号密码是否匹配
    //userEmail     用户名
    //userPassword  用户密码
    //return        是/否匹配
    public boolean isMatchPassword(String userEmail,String userPassword) {
        if(this.userPassword!=null && !this.userPassword.isEmpty())
            Log.d("user",this.userPassword);
        if(!userPassword.isEmpty())
            Log.d("user",userPassword);
        if(userPassword.equals(getPassword(userEmail)))
            return true;
        else
            return false;
    }

    //私有类，处理Thread线程
    private class MyThreadReturn implements Runnable {

        private String tmpEmail;
        private String returnPswd;
        //构造函数
        // 传参
        public MyThreadReturn(String email) {
            this.tmpEmail = email;
        }

        @Override
        public void run() {
            try {
                //连接到mysql
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection cn = DriverManager.getConnection(url,user,pswd);
                String sql = "select userPassword from users where userEmail =";
                sql += ("\"" + tmpEmail +"\"");
                Statement st = (Statement)cn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                //获取密码
                while(rs.next()) {
                    returnPswd = rs.getString("userPassword");
                    System.out.println(returnPswd);
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

        public String getReturnPassword() {
            if(returnPswd!=null && !returnPswd.isEmpty())
                Log.d("run_return",returnPswd);
            return returnPswd;
        }
    }
}
