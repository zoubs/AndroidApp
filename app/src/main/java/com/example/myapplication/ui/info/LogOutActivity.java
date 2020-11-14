package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class LogOutActivity extends AppCompatActivity {
    private EditText etPswd;
    private Button mBtnConfirm;
    private GlobalInfo globalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        globalInfo = (GlobalInfo) getApplication();
        etPswd = findViewById(R.id.log_out_password);
        mBtnConfirm = findViewById(R.id.btn_log_out);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPswd.getText().toString();
                if(!password.isEmpty()) {
                    if (password.equals(globalInfo.getUserPassword())) {
                        showConfirmDialog();
                    } else {
                        Toast.makeText(LogOutActivity.this, "注销失败，密码不正确",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogOutActivity.this, "请将表单填写完整",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogOutActivity.this);
        builder.setTitle("确认注销账号吗？此操作不可恢复");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Thread thread;
                final boolean[] isSuccess = new boolean[1];
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDaoImpl userDao = new UserDaoImpl();
                        isSuccess[0] = userDao.deleteOrdinary(globalInfo.getNowUserId());
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(isSuccess[0]) {
                    Toast.makeText(LogOutActivity.this, "注销成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogOutActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogOutActivity.this, "注销失败，请稍后尝试",Toast.LENGTH_SHORT).show();
                }
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