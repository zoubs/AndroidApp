package com.example.myapplication.ui.logup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MyUsers;
import com.example.myapplication.R;
import com.example.myapplication.ui.user.UserInformation;
import com.example.myapplication.ui.user.UserManageFragment;


public class LogUpFragment extends Fragment {
    private boolean is_admin;
    private Button btnCommit;
    private Button btnCancel;
    private EditText userEmail;
    private EditText userName;
    private EditText password;
    private EditText confirmPswd;
    private Spinner isAdmin;
    private PopupWindow progress;


    //构造函数，传递是否为管理员界面，用于复用
    public LogUpFragment(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_up,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCommit = view.findViewById(R.id.btn_log_up_commit);
        btnCancel = view.findViewById(R.id.btn_log_up_cancel);
        //判断是否为admin界面（复用）
        if(is_admin) {
            btnCommit.setText("添加用户");
        }
        else {
            btnCommit.setText("注册");
        }
        //commit button响应
        userEmail = view.findViewById(R.id.log_up_email);
        userName = view.findViewById(R.id.log_up_user_name);
        password = view.findViewById(R.id.log_up_password);
        confirmPswd = view.findViewById(R.id.log_up_confirm_password);
        isAdmin = view.findViewById(R.id.log_up_is_admin_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.is_admin_spinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isAdmin.setAdapter(adapter);

        final View tmpView = view;
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_email = userEmail.getText().toString();
                final String user_name = userName.getText().toString();
                String user_pswd = password.getText().toString();
                String confirm_pswd = confirmPswd.getText().toString();
                String user_identify = isAdmin.getSelectedItem().toString();
                final View popipWindow_view = getLayoutInflater().inflate(R.layout.log_up_progressbar_layout,null,false);
                progress = new PopupWindow(popipWindow_view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,false);

                if(user_email == null || user_email.equals("")) {
                    Toast.makeText(tmpView.getContext(), "缺少用户邮箱", Toast.LENGTH_SHORT).show();
                    userEmail.setBackgroundResource(R.drawable.textview_red_border);
                }

                else if(user_name == null || user_name.equals("")) {
                    Toast.makeText(tmpView.getContext(), "缺少用户名", Toast.LENGTH_SHORT).show();
                    userName.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                }

                else if(user_pswd == null || user_pswd.equals("")) {
                    Toast.makeText(tmpView.getContext(), "缺少密码", Toast.LENGTH_SHORT).show();
                    password.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                }

                else if(confirm_pswd == null || confirm_pswd.equals("")) {
                    Toast.makeText(tmpView.getContext(), "缺少密码确认", Toast.LENGTH_SHORT).show();
                    confirmPswd.setBackgroundResource(R.drawable.textview_red_border);
                    userEmail.setBackgroundResource(0);
                    userName.setBackgroundResource(0);
                    password.setBackgroundResource(0);
                }

                else if(!confirm_pswd.equals(user_pswd)) {
                    Toast.makeText(tmpView.getContext(), "确认密码不匹配", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(tmpView.getContext(), "该邮箱已被注册！", Toast.LENGTH_LONG).show();
                    }
                    //邮箱未被注册
                    else {
                        myUsers.userLogUp(user_email,user_name,user_pswd,is_admin);
                        progress.showAsDropDown(tmpView);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                                //插入成功
                                if(myUsers.isExist(user_email)) {
                                    Toast.makeText(tmpView.getContext(), "成功", Toast.LENGTH_LONG).show();
                                    UserManageFragment userManageFragment = (UserManageFragment)getActivity().getSupportFragmentManager().findFragmentByTag("userManageFragment");
                                    if(userManageFragment == null) {
                                        Toast.makeText(getContext(), "fuck wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        userManageFragment.addUser(new UserInformation(user_name,user_email,is_admin));
                                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                                    }

                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                                //插入失败
                                else {
                                    Toast.makeText(tmpView.getContext(), "插入失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);//1秒后执行Runnable中的run方法


                    }
                }
            }
        });

        //cancel button响应
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
