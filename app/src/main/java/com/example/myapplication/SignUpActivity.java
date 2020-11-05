package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private Button mBtnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mBtnSignUp = findViewById(R.id.SignUp);
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 拉数据库进行比对，比对方法好像看到你在其他地方写的有，我没搬过来
                /*检查账号是否存在
                 两次密码是否相同
                 */
                /*根据情况进行显示提示信息
                1.账号已存在
                2.账号不合法
                3.两次密码不同
                 */

                /*
                需要向邮箱发验证码？
                若需要：判断验证码
                 */
                Toast.makeText(SignUpActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                //注册成功直接跳到home页面
                Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}