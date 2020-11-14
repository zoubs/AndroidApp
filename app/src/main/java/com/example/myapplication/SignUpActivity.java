package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.PO.User;
import com.example.myapplication.ui.adminmodule.usermanager.AddUserActivity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL;

public class SignUpActivity extends AppCompatActivity {
    private Button mBtnSignUp, mBtnCancel;
    private EditText etUserEmail, etUserName, etPassword, etConfirmPswd;
    private User userByEmail, userByName, userSignInfo;
    private GlobalInfo globalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mBtnSignUp = findViewById(R.id.btn_user_sign_up_commit_add);
        //mBtnCancel = findViewById(R.id.btn_user_sign_up_cancel_add);

        etUserEmail = findViewById(R.id.user_sign_up_email_add);
        etUserName = findViewById(R.id.user_sign_up_user_name_add);
        etPassword = findViewById(R.id.user_sign_up_password_add);
        etConfirmPswd = findViewById(R.id.user_sign_up_confirm_password_add);
        globalInfo = (GlobalInfo) getApplication();

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final User user = new User();
                final String userEmail = etUserEmail.getText().toString();
                final String userName = etUserName.getText().toString();
                String userPassword = etPassword.getText().toString();
                String passwordConfirm = etConfirmPswd.getText().toString();

                if(userEmail.equals("")) {
                    Toast.makeText(SignUpActivity.this, "缺少用户邮箱", Toast.LENGTH_SHORT).show();
                    etUserEmail.setBackgroundResource(R.drawable.textview_red_border);
                }

                else if(userName.equals("")) {
                    Toast.makeText(SignUpActivity.this, "缺少用户名", Toast.LENGTH_SHORT).show();
                    etUserName.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                }

                else if(userPassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "缺少密码", Toast.LENGTH_SHORT).show();
                    etPassword.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                    etUserName.setBackgroundResource(0);
                }

                else if(passwordConfirm.equals("")) {
                    Toast.makeText(SignUpActivity.this, "缺少密码确认", Toast.LENGTH_SHORT).show();
                    etConfirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                    etUserName.setBackgroundResource(0);
                    etPassword.setBackgroundResource(0);
                }
                else if(!isValidName(userName)) {
                    Toast.makeText(SignUpActivity.this, "用户名输入不规范", Toast.LENGTH_SHORT).show();
                    etConfirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                    etUserName.setBackgroundResource(0);
                    etPassword.setBackgroundResource(0);
                }

                else if(!isValid(userPassword)){
                    Toast.makeText(SignUpActivity.this, "密码输入不规范", Toast.LENGTH_SHORT).show();
                    etConfirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                    etUserName.setBackgroundResource(0);
                    etPassword.setBackgroundResource(0);
                }
                else if(!passwordConfirm.equals(userPassword)) {
                    Toast.makeText(SignUpActivity.this, "两次输入的密码不匹配", Toast.LENGTH_SHORT).show();
                    etConfirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    etUserEmail.setBackgroundResource(0);
                    etUserName.setBackgroundResource(0);
                    etPassword.setBackgroundResource(0);
                } else {   //合法性检查初步通过
                    //检查用户名、邮箱是否存在
                    Thread thread;
                    final boolean[] isSuccess = new boolean[1];
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserDaoImpl userDao = new UserDaoImpl();
                            userByEmail = userDao.findUserByEmail(userEmail);
                            userByName = userDao.findUserByName(userName);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(userByEmail != null) {
                        Toast.makeText(SignUpActivity.this, "注册失败，该邮箱已被注册",Toast.LENGTH_SHORT).show();
                    } else  if(userByName != null) {
                        Toast.makeText(SignUpActivity.this, "注册失败，该用户名已被占用",Toast.LENGTH_SHORT).show();
                    } else {  //可以注册
                        Thread threadSignUp;
                        final User[] userTmp = {new User()};
                        userSignInfo = new User();
                        userSignInfo.setUserEmail(userEmail);
                        userSignInfo.setUserName(userName);
                        userSignInfo.setUserPassword(userPassword);
                        threadSignUp = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                UserDaoImpl userDao = new UserDaoImpl();
                                userSignInfo.setRegisterTime(new Timestamp((new Date()).getTime()));
                                isSuccess[0] = userDao.insertOrdinaryUser(userSignInfo);
                                userTmp[0] = userDao.findUserByName(userName);

                            }
                        });
                        threadSignUp.start();
                        try {
                            threadSignUp.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(isSuccess[0]) {
                            Toast.makeText(SignUpActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                            globalInfo.setUserName(userName);
                            globalInfo.setIsAdmin(false);
                            globalInfo.setUserPassword(userPassword);
                            globalInfo.setUserEmail(userEmail);
                            globalInfo.setNowUserId(userTmp[0].getUserID());
                            //注册成功直接跳到home页面
                            Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "注册失败，请稍后再试",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    public boolean isValidName(String name) {
        boolean res = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*").matcher(name).matches();
        if (res) {
            if (name.length() > 6) {
                res = false;
            }
        }
        return res;
    }
    public boolean lengthValid(String string) {
        return string.length() >= 6 && string.length() <= 16;
    }
    public boolean isValid(String string) {
        boolean res = Pattern.compile("[0-9a-zA-Z]*").matcher(string).matches();
        if (res) {
            if (string.length() <6 || string.length() >16) {
                res = false;
            }
        }
        return res;
    }
}