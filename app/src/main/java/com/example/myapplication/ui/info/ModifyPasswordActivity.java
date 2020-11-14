package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.User;
import com.example.myapplication.R;

import java.util.regex.Pattern;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button mBtnSubmit;
    private User user;
    private GlobalInfo globalInfo;
    private String oldPswd;
    private String newPswd;
    private String confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        mBtnSubmit = findViewById(R.id.btn_modify_password);
        globalInfo = (GlobalInfo)getApplication();

        user = new User();
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPswd = etOldPassword.getText().toString();
                newPswd = etNewPassword.getText().toString();
                confirm = etConfirmPassword.getText().toString();
                boolean flag = true;

                if(!oldPswd.isEmpty() && !newPswd.isEmpty() && !confirm.isEmpty()) {

                    if(!lengthValid(oldPswd)) {
                        flag = false;
                        Toast.makeText(ModifyPasswordActivity.this, "密码长度应为6-16位", Toast.LENGTH_SHORT).show();

                    } else {
                        if(!isValid(oldPswd)) {
                            flag = false;
                            Toast.makeText(ModifyPasswordActivity.this, "密码格式不规范", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(flag) {
                        if(!lengthValid(newPswd)) {
                            flag = false;
                            Toast.makeText(ModifyPasswordActivity.this, "密码长度应为6-16位", Toast.LENGTH_SHORT).show();

                        } else {
                            if(!isValid(newPswd)) {
                                flag = false;
                                Toast.makeText(ModifyPasswordActivity.this, "密码格式不规范", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    if(flag) {
                        if(!lengthValid(confirm)) {
                            flag = false;
                            Toast.makeText(ModifyPasswordActivity.this, "密码长度应为6-16位", Toast.LENGTH_SHORT).show();

                        } else {
                            if(!isValid(confirm)) {
                                flag = false;
                                Toast.makeText(ModifyPasswordActivity.this, "密码格式不规范", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    if(flag && newPswd.equals(confirm)) {
                        if (oldPswd.equals(globalInfo.getUserPassword())){
                            user.setUserName(globalInfo.getUserName());
                            user.setUserPassword(newPswd);
                            user.setUserEmail(globalInfo.getUserEmail());
                            user.setUserID(globalInfo.getNowUserId());
                            if(globalInfo.getIsAdmin()) {
                                user.setUserType("Admin");
                            }else {
                                user.setUserType("User");
                            }
                            //符合格式的话
                            showConfirmDialog();
                        }
                    }


                    //Toast.makeText(ModifyPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyPasswordActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPasswordActivity.this);
        builder.setTitle("确认修改吗？");
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
                        isSuccess[0] = userDao.updateUserBaseInfo(user);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (isSuccess[0]){
                    globalInfo.setUserPassword(newPswd);
                    Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyPasswordActivity.this, "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
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
    public boolean lengthValid(String string) {
        return string.length() >= 6 && string.length() <= 16;
    }
    public boolean isValid(String string) {
        return Pattern.compile("[0-9a-zA-Z]*").matcher(string).matches();
    }
}