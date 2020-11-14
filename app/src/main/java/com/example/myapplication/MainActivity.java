package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DaoImpl.FoodDataDaoImpl;
import com.example.myapplication.util.ToastUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*Toast toast2 = new Toast(MainActivity.this);
    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.toast_custom, null);
    toast2.setView(view);
    toast2.setGravity(Gravity.CENTER, 0, 0);
    toast2.show();*/
    private Button mBtnLogin, mBtnSignUp;
    private EditText userNameText,pwdText;
    //public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //登录跳转按钮
        userNameText = findViewById(R.id.emailText);
        pwdText = findViewById(R.id.pwdText);
        mBtnLogin = findViewById(R.id.mBtnLogin);
        mBtnSignUp = findViewById(R.id.mBtnSignUp);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至注册页面
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        final String storeEmail = getEmail();
        String storePassword = getPassword();

        if(storeEmail != null && !storeEmail.isEmpty()) {
            userNameText.setText(storeEmail);
        }

        if(storePassword != null && !storePassword.isEmpty()) {
            pwdText.setText(storePassword);
        }

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameText.getText().toString();
                String password = pwdText.getText().toString();
                MyUsers myUsers = new MyUsers();

                //复杂
                if(myUsers.isMatchPassword(userName,password)) {
                    //Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();

                    save(userName,password);


                    Bundle bundle = new Bundle();
                    bundle.putString("email",userName);
                    bundle.putBoolean("is_admin",myUsers.isAdmin());

                    System.out.println(myUsers.isAdmin());
                    Intent intent = new Intent();
                    final GlobalInfo globalInfo = (GlobalInfo) getApplication();
                    if(!myUsers.isAdmin()) {
                        Thread threadGetFood;
                        threadGetFood = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                                List<String> allFood = foodDataDao.findAllFoodName();
                                Collections.sort(allFood, new MyChineseComparator());
                                globalInfo.setAllFood(allFood);
                            }
                        });
                        threadGetFood.start();
                        try {
                            threadGetFood.join();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        intent.setClass(MainActivity.this,HomePageActivity.class);
                    }
                    else {
                        intent.setClass(MainActivity.this,AdminHomePageActivity.class);
                    }
                    intent.putExtras(bundle);

                    globalInfo.setUserEmail(myUsers.getGUserEmail());
                    globalInfo.setUserName(myUsers.getGUserName());
                    globalInfo.setUserPassword(password);
                    globalInfo.setNowUserId(myUsers.getUserId());
                    globalInfo.setIsAdmin(myUsers.isAdmin());
                    ToastUtil.showMsg(getApplicationContext(),"登录成功！");
                    startActivity(intent);
                }
                else {
                    //Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                    ToastUtil.showMsg(getApplicationContext(),myUsers.getFailInformation());
                }
            }
        });//登录按钮申明结束
    }

    private void save(String email, String password) {
        FileOutputStream efileOutputStream = null;
        FileOutputStream pfileOutputStream = null;
        try {
            efileOutputStream = openFileOutput("email.txt",MODE_PRIVATE);
            efileOutputStream.write(email.getBytes());

            pfileOutputStream = openFileOutput("password.txt",MODE_PRIVATE);
            pfileOutputStream.write(password.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(efileOutputStream != null) {
                try {
                    efileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(pfileOutputStream != null) {
                try {
                    pfileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getEmail() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput("email.txt");
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = fileInputStream.read(buff)) > 0) {
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getPassword() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput("password.txt");
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = fileInputStream.read(buff)) > 0) {
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
