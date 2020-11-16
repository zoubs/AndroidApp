package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.R;
import com.example.myapplication.VO.OrdinaryUser;

public class InfoActivity extends AppCompatActivity {
    private EditText editTextAge, editTextUserName, editTextWeight, editTextHeight, editTextPressure, editTextBmi;
    private GlobalInfo globalInfo;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        OrdinaryUserData userData = new OrdinaryUserData();
        globalInfo = (GlobalInfo)getApplication();

        editTextAge = findViewById(R.id.et_user_age);
        editTextHeight = findViewById(R.id.et_user_height);
        editTextWeight = findViewById(R.id.et_user_weight);
        editTextPressure = findViewById(R.id.et_user_blood_pressure);
        editTextBmi = findViewById(R.id.et_user_bmi);

        editTextAge.setInputType(InputType.TYPE_NULL);
        editTextHeight.setInputType(InputType.TYPE_NULL);
        editTextWeight.setInputType(InputType.TYPE_NULL);
        editTextPressure.setInputType(InputType.TYPE_NULL);
        editTextBmi.setInputType(InputType.TYPE_NULL);

        final boolean[] isExist = new boolean[1];
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                OrdinaryUser ordinaryUser = userDao.findOrdinaryByID(globalInfo.getNowUserId());
                isExist[0] = ordinaryUser != null;
                if (isExist[0]) {
                    OrdinaryUserData userDetail = ordinaryUser.getUserData();

                    double weight = userDetail.getUserWeight();
                    double height = userDetail.getUserSature();
                    double bp = userDetail.getUserBP();
                    double bmi = weight *10000 /(height*height);
                    String bmiText = String.format("%.2f", bmi);    //保留两位小数
                    editTextBmi.setText(bmiText);
                    int age = userDetail.getUserAge();


                    editTextHeight.setText(String.format("%.2f", height));
                    editTextWeight.setText(String.format("%.2f",weight));
                    editTextPressure.setText(String.format("%.2f", bp));
                    editTextAge.setText(String.valueOf(age));
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!isExist[0]) {   //还未添加过信息
            editTextHeight.setText("");
            editTextWeight.setText("");
            editTextPressure.setText("");
            editTextAge.setText("");
            editTextBmi.setText("");
            Toast.makeText(InfoActivity.this, "请尽快完善信息", Toast.LENGTH_LONG).show();
        }

    }
}