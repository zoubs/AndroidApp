package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private EditText emailText,pwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //登录跳转按钮
        emailText = findViewById(R.id.emailText);
        mBtnLogin = findViewById(R.id.mBtnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = emailText.getText().toString();
                //String password = pwdText.getText().toString();

                //判断账号密码
                //简化
                Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                startActivity(intent);
                //复杂
                /*if(email.equals("admin") && password.equals("admin")) {
                    Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                }*/
            }
        });//登录按钮申明结束
    }
}
