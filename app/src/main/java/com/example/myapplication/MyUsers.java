package com.example.myapplication;


import android.app.Activity;
import android.util.Log;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.PO.User;
import com.example.myapplication.VO.OrdinaryUser;

import java.sql.Timestamp;
import java.util.Date;


public class MyUsers {
    //private String url;             //MySql的URL
    //private String user;            //MySql登录名
    //private String pswd;            //MySql登录密码

    private Integer userId;
    private Boolean sqlInformation = true;      //sql执行情况
    private String gUserEmail;       //用户邮箱
    private String gUserName;
    private String userPassword;    //获取的用户密码
    private Activity activity;
    private String failInformation;
    private boolean is_admin;
    static boolean flag = false;    //代表是否出现错误



    //构造函数初始化
    public MyUsers() {

    }
    public MyUsers(Activity activity) {
        this.activity = activity;
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

    //添加用户
    public void userSignUp(final String userEmail, final String userName, final String userPassword, final boolean isAdmin) {
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = new User();
                Date date = new Date();
                user.setRegisterTime(new Timestamp(date.getTime()));
                user.setUserEmail(userEmail);
                user.setUserName(userName);
                user.setUserPassword(userPassword);

                if (isAdmin) {
                    sqlInformation = userDao.insertAdmin(user);
                } else {
                    sqlInformation = userDao.insertOrdinaryUser(user);
                }
            }
        });
        //开始线程
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //密码是否匹配
    public boolean isMatchPassword(String userEmail, String userPassword) {
        if(this.userPassword!=null && !this.userPassword.isEmpty())
            Log.d("ic_user",this.userPassword);
        if(!userPassword.isEmpty())
            Log.d("ic_user",userPassword);
        if (userPassword.equals(getPassword(userEmail))) {
            return true;
        }else {
            failInformation = "用户名或密码不正确";
            return false;
        }
    }

    //是否为管理员
    public boolean isAdmin() {
        return is_admin;
    }

    //返回一些需要的信息
    public Integer getUserId() {
        return userId;
    }

    public String getFailInformation() {
        return failInformation;
    }

    public String getGUserEmail() {
        return gUserEmail;
    }
    public String getGUserName() {
        return gUserName;
    }

    public boolean getSqlInformation() {
        return sqlInformation;
    }

    public void userDelete(final String email) {
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.findUserByEmail(email);
                if(user != null) {
                    userDao.deleteOrdinary(user.getUserID());
                }
            }
        });

        //开始线程
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //fixme 管理员更新用户数据 此功能不确定是否添加
    public void userAlter(final String email, final String name, final int is_admin) {

        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();

            }
        });

        //开始线程
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //邮箱是否存在
    public boolean isUserEmailExist(final String userEmail) {
        //flag = false;
        final boolean[] exist = new boolean[1];
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.findUserByEmail(userEmail);
                if(user == null) {
                    exist[0] = false;
                } else {
                    exist[0] = true;
                }
            }
        });

        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exist[0];
    }
    public boolean isUserNameExist(final String userName) {
        //flag = false;
        final boolean[] exist = new boolean[1];
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                User user = userDao.findUserByName(userName);
                if(user == null) {
                    exist[0] = false;
                } else {
                    exist[0] = true;
                }
            }
        });

        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exist[0];
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

            UserDaoImpl userDao = new UserDaoImpl();
            User userByEmail = userDao.findUserByEmail(tmpEmail);
            User userByName = userDao.findUserByName(tmpEmail);
            //fixme email,name都可以登录

            if (userByEmail == null && userByName == null){
                failInformation = "用户名不存在";
            } else {
                if (userByEmail!=null) {
                    returnPswd = userByEmail.getUserPassword();
                    is_admin = userByEmail.getUserType().equals("Admin");
                    userId = userByEmail.getUserID();
                    gUserEmail = userByEmail.getUserEmail();
                    gUserName = userByEmail.getUserName();
                } else {
                    returnPswd = userByName.getUserPassword();
                    is_admin = userByName.getUserType().equals("Admin");
                    userId = userByName.getUserID();
                    gUserEmail = userByName.getUserEmail();
                    gUserName = userByName.getUserName();
                }

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
}
