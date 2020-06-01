package com.example.myapplication;

import android.os.Message;
import android.util.Log;

import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyUsers {
    private String url;             //MySql的URL
    private String user;            //MySql登录名
    private String pswd;            //MySql登录密码
    private String userPassword;    //获取的用户密码
    private boolean is_admin;
    static boolean flag = false;
    static CountExisted countExisted = new CountExisted();

    //构造函数初始化
    public MyUsers(String url, String user, String pswd) {
        this.url = url;
        this.user = user;
        this.pswd = pswd;
        flag = false;
    }

    //默认初始化
    public MyUsers() {
        this.url = "jdbc:mysql://192.168.3.6:3306/android_db?useSSL=false&allowPublicKeyRetrieval=true";
        this.user = "android";
        this.pswd = "android";
    }

    //获取用户密码
    private String getPassword(String email) {
        MyThreadReturn myThreadReturn = new MyThreadReturn(email);
        Thread thread = new Thread(myThreadReturn);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userPassword = myThreadReturn.getReturnPassword();
        is_admin = myThreadReturn.getIsAdmin();
        System.out.println(is_admin);

        if(userPassword!=null && !userPassword.isEmpty())
            Log.d("return",userPassword);

        return userPassword;
    }

    //添加用户数据
    public void userLogUp(final String userEmail, final String userName, final String userPassword, final boolean isAdmin) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接到mysql
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection(url,user,pswd);
                    String sql = "insert into users (userEmail,userName,userPassword,is_admin) value (?,?,?,?);";

                    PreparedStatement psmt = conn.prepareStatement(sql);
                    psmt.setString(1,userEmail);
                    psmt.setString(2,userName);
                    psmt.setString(3,userPassword);
                    psmt.setBoolean(4,isAdmin);
                    psmt.executeUpdate();

                    conn.close();
                    psmt.close();
                    Log.d("success","链接成功");
                } catch (ClassNotFoundException e) {
                    Log.d("error","链接失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();//开始线程

    }

    //密码是否匹配
    public boolean isMatchPassword(String userEmail, String userPassword) {
        if(this.userPassword!=null && !this.userPassword.isEmpty())
            Log.d("ic_user",this.userPassword);
        if(!userPassword.isEmpty())
            Log.d("ic_user",userPassword);
        return userPassword.equals(getPassword(userEmail));
    }

    //是否为管理员
    public boolean isAdmin() {
        return is_admin;
    }

    //删除
    public void userDelete(final String email) {
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接到mysql
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection(url,user,pswd);
                    String sql = "delete from users where userEmail = ?;";

                    PreparedStatement psmt = conn.prepareStatement(sql);
                    psmt.setString(1,email);
                    psmt.execute();

                    conn.close();
                    psmt.close();
                    Log.d("success","链接成功");
                } catch (ClassNotFoundException e) {
                    Log.d("error","链接失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });//开始线程

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //管理员更新用户数据
    public void userAlter(final String email, final String name, final int is_admin) {

        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接到mysql
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection(url,user,pswd);
                    String sql = "update users set userName = ? where userEmail = ?;";

                    PreparedStatement psmt = conn.prepareStatement(sql);
                    psmt.setString(1,name);
                    psmt.setString(2,email);
                    psmt.executeUpdate();

                    if(is_admin != 0) {
                        sql = "update users set is_admin = ? where userEmail = ?;";
                        psmt =conn.prepareStatement(sql);
                        psmt.setBoolean(1,(is_admin==1 ? true : false));
                        psmt.setString(2,email);
                        psmt.executeUpdate();
                    }

                    conn.close();
                    psmt.close();
                    Log.d("success","链接成功");
                } catch (ClassNotFoundException e) {
                    Log.d("error","链接失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });//开始线程

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //是否存在
    public boolean isExist(String userEmail) {
        //flag = false;
        countExisted.setStatus(false);
        Thread myThread = new ExistedThread(userEmail,countExisted);
        myThread.start();

        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(userEmail);

        System.out.println(countExisted.getStatus());
        return countExisted.getStatus();
    }

    //私有类，处理Thread线程
    private class MyThreadReturn implements Runnable {

        private String tmpEmail;
        private String returnPswd;
        private boolean is_admin;

        //构造函数 传参
        MyThreadReturn(String email) {
            this.tmpEmail = email;
        }

        @Override
        public void run() {
            try {
                //连接到mysql
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection conn = DriverManager.getConnection(url,user,pswd);
                String sql = "select userPassword,is_admin from users where userEmail = ?";
                //sql += ("\"" + tmpEmail +"\"");
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1,tmpEmail);
                ResultSet rs = psmt.executeQuery();

                //获取密码
                while(rs.next()) {
                    returnPswd = rs.getString("userPassword");
                    is_admin = rs.getBoolean("is_admin");
                    System.out.println(returnPswd);
                    System.out.println(is_admin);
                }
                conn.close();
                psmt.close();
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

        public boolean getIsAdmin() {
            return is_admin;
        }
    }

    //处理该邮箱是否出现过
    static class CountExisted {
        private boolean is_exist = false;

        public boolean getStatus() {
            return this.is_exist;
        }

        public void setStatus(boolean i) {
            this.is_exist = i;
        }
    }

    class ExistedThread extends Thread {
        private CountExisted c;
        private String user_email;

        public ExistedThread(String email,CountExisted c) {
            this.user_email = email;
            this.c = c;
        }

        @Override
        public void run() {
            try {
                //连接到mysql
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection conn = DriverManager.getConnection(url,user,pswd);
                String sql = "select * from users where userEmail = ?";
                System.out.println(user_email);
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1,user_email);
                ResultSet rs = psmt.executeQuery();
                if(rs.next()) {
                    c.setStatus(true);
                    System.out.println("fuck true");
                }
                else {
                    c.setStatus(false);
                    System.out.println("fuck false");
                }
                conn.close();
                psmt.close();
                rs.close();
                Log.d("success","链接成功");
                //MyUsers.callback();
            } catch (ClassNotFoundException e) {
                Log.d("error","链接失败");
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
