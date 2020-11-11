package com.example.myapplication.ui.adminmodule.usermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.MyUsers;
import com.example.myapplication.R;
import com.example.myapplication.ui.user.UserInformation;
import com.example.myapplication.ui.user.UserManageFragment;

public class AddUserActivity extends AppCompatActivity {

    private Button mBtnSubmit, mBtnCancel;
    private EditText userEmail;
    private EditText userName;
    private EditText password;
    private EditText confirmPswd;
    private Spinner isAdmin;
    private PopupWindow progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        userEmail = findViewById(R.id.log_up_email_add);
        userName = findViewById(R.id.log_up_user_name_add);
        password = findViewById(R.id.log_up_password_add);
        confirmPswd = findViewById(R.id.log_up_confirm_password_add);
        isAdmin = findViewById(R.id.log_up_is_admin_spinner_add);
        mBtnSubmit = findViewById(R.id.btn_log_up_commit_add);
        mBtnCancel = findViewById(R.id.btn_log_up_cancel_add);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.is_admin_spinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isAdmin.setAdapter(adapter);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_email = userEmail.getText().toString();
                final String user_name = userName.getText().toString();
                String user_pswd = password.getText().toString();
                String confirm_pswd = confirmPswd.getText().toString();
                String user_identify = isAdmin.getSelectedItem().toString();
                final View popipWindow_view = getLayoutInflater().inflate(R.layout.log_up_progressbar_layout,null,false);
                progress = new PopupWindow(popipWindow_view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,false);

                if(user_email.equals("")) {
                    Toast.makeText(AddUserActivity.this, "缺少用户邮箱", Toast.LENGTH_SHORT).show();
                    userEmail.setBackgroundResource(R.drawable.textview_red_border);
                }

                else if(user_name.equals("")) {
                    Toast.makeText(AddUserActivity.this, "缺少用户名", Toast.LENGTH_SHORT).show();
                    userName.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                }

                else if(user_pswd.equals("")) {
                    Toast.makeText(AddUserActivity.this, "缺少密码", Toast.LENGTH_SHORT).show();
                    password.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                }

                else if(confirm_pswd.equals("")) {
                    Toast.makeText(AddUserActivity.this, "缺少密码确认", Toast.LENGTH_SHORT).show();
                    confirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                    password.setBackgroundResource(0);
                }

                else if(!confirm_pswd.equals(user_pswd)) {
                    Toast.makeText(AddUserActivity.this, "确认密码不匹配", Toast.LENGTH_SHORT).show();
                    confirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                    password.setBackgroundResource(0);
                }

                else {
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                    password.setBackgroundResource(0);
                    confirmPswd.setBackgroundResource(0);
                    final boolean is_admin = (user_identify.equals("管理员"))? true : false;

                    //database connect
                    final MyUsers myUsers = new MyUsers("jdbc:mysql://39.101.211.144:3306/android_db?useSSL=false&allowPublicKeyRetrieval=true",
                            "android",
                            "android123456");

                    //邮箱已被注册
                    if(myUsers.isExist(user_email)) {
                        Toast.makeText(AddUserActivity.this, "该邮箱已被注册！", Toast.LENGTH_LONG).show();
                    }
                    //邮箱未被注册
                    else {
                        myUsers.userLogUp(user_email,user_name,user_pswd,is_admin);
                        //progress.showAsDropDown(AddUserActivity.class);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                                //插入成功
                                if(myUsers.isExist(user_email)) {
                                    Toast.makeText(AddUserActivity.this, "成功", Toast.LENGTH_LONG).show();
                                    UserManageFragment userManageFragment = (UserManageFragment)getSupportFragmentManager().findFragmentByTag("userManageFragment");
                                    if(userManageFragment == null) {
                                        Toast.makeText(AddUserActivity.this, "fuck wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        userManageFragment.addUser(new UserInformation(user_name,user_email,is_admin));
                                        Toast.makeText(AddUserActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    }

                                    getSupportFragmentManager().popBackStack();
                                }
                                //插入失败
                                else {
                                    Toast.makeText(AddUserActivity.this, "插入失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);//1秒后执行Runnable中的run方法


                    }
                }
            }
        });

        //cancel button响应
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });
    }
}