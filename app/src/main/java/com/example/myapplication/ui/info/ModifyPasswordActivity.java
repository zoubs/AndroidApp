package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button mBtnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        mBtnSubmit = findViewById(R.id.btn_modify_password);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPswd = etOldPassword.getText().toString();
                String newPswd = etNewPassword.getText().toString();
                String confirm = etConfirmPassword.getText().toString();

                if(!oldPswd.isEmpty() && !newPswd.isEmpty() && !confirm.isEmpty()) {
                    //todo 判断密码是否符合格式，你写过了，搬过来就行

                    //符合格式的话
                    showConfirmDialog();

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