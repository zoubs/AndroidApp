package com.example.myapplication.ui.adminmodule.usermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.MainActivity;
import com.example.myapplication.PO.User;
import com.example.myapplication.R;
import com.example.myapplication.ui.info.LogOutActivity;

public class DeleteUserActivity extends AppCompatActivity {

    private EditText userName;
    private Button mBtnSubmit, mBtnCancel;
    private Integer delId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        userName = findViewById(R.id.admin_user_name_del);
        mBtnCancel = findViewById(R.id.btn_admin_find_user);
        mBtnSubmit = findViewById(R.id.btn_admin_del_user);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                if(!name.isEmpty()) {
                    showConfirmDialog(name);
                } else {
                    Toast.makeText(DeleteUserActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void showConfirmDialog(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteUserActivity.this);
        builder.setTitle("确认删除该用户吗？此操作不可恢复");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Thread thread;
                final boolean[] isExist = new boolean[1];
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDaoImpl userDao = new UserDaoImpl();
                        User userByName = userDao.findUserByName(name);
                        User userByEmail = userDao.findUserByEmail(name);
                        if (userByEmail == null && userByName == null) {
                            isExist[0] = false;
                        }else {
                            isExist[0] = true;
                            if(userByEmail != null) {
                                delId = userByEmail.getUserID();
                            }else {
                                delId = userByName.getUserID();
                            }
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                final boolean[] isSuccess = new boolean[1];
                if(isExist[0]) {
                    Thread delThread;
                    delThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserDaoImpl userDao = new UserDaoImpl();
                            isSuccess[0] = userDao.deleteOrdinary(delId);
                        }
                    });
                    delThread.start();
                    try {
                        delThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isSuccess[0]) {
                        Toast.makeText(DeleteUserActivity.this, "删除用户成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DeleteUserActivity.this, "删除失败，请稍后再试",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeleteUserActivity.this, "删除失败，该用户不存在",Toast.LENGTH_SHORT).show();
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