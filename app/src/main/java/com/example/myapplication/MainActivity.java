package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.util.ToastUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private EditText emailText,pwdText;

    //database connect
    private String url = "jdbc:mysql://192.168.3.6:3306/android_db";
    private String user = "android";
    private String pswd = "android";
    private boolean flag = false;
    PreparedStatement statement = null;
    Statement stat = null;
    Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //登录跳转按钮
        emailText = findViewById(R.id.emailText);
        pwdText = findViewById(R.id.pwdText);
        mBtnLogin = findViewById(R.id.mBtnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email = emailText.getText().toString();
                 if(email.isEmpty()) {
                     email = "example@goole.com";
                 }
                String password = pwdText.getText().toString();
                 if(password.isEmpty()) {
                     password = "123456";
                 }

                final String finalEmail = email;
                new Thread(new Runnable() {
                     @Override
                     public void run() {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            java.sql.Connection cn = DriverManager.getConnection(url,user,pswd);
                            String sql = "select userPassword from users where userEmail =";
                            sql += ("\"" + finalEmail +"\"");
                            Statement st = (Statement)cn.createStatement();
                            ResultSet rs = st.executeQuery(sql);

                            while(rs.next())
                            {
                                flag = true;
                                Log.d("password",rs.getString("userPassword"));
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

                Log.d("flag",(flag?"true":"false"));

                //复杂
                if(flag) {
                    //Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("email",email);
                    bundle.putString("pwd",password);
                    intent.putExtras(bundle);
                    ToastUtil.showMsg(getApplicationContext(),"登录成功！");
                    startActivity(intent);
                }
                else {
                    //Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                    ToastUtil.showMsg(getApplicationContext(),"登录失败！");
                }
            }
        });//登录按钮申明结束
    }
}
