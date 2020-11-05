package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class LogOutActivity extends AppCompatActivity {
    private EditText etPswd;
    private Button mBtnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        etPswd = findViewById(R.id.log_out_password);
        mBtnConfirm = findViewById(R.id.btn_log_out);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 判断密码是否正确

                //不正确的话提示一下，正确则进行事务处理
                showConfirmDialog();
            }
        });
    }
    protected void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogOutActivity.this);
        builder.setTitle("确认注销账号吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //todo 数据库提交数据, 提交成功弹出提示 写前面不知道会不会卡顿
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}