package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.R;

public class InfoActivity extends AppCompatActivity {
    private EditText editTextAge, editTextUserName, editTextWeight, editTextHeight, editTextPressure, editTextBmi;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        OrdinaryUserData userData = new OrdinaryUserData();
        //fixme 设了个默认值，以后删除

        userData.setUserAge(21);
        userData.setUserBP(120.1);
        userData.setUserSature(182.1);
        userData.setUserWeight(74.2);

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

        //todo 查询信息显示
        //假定查询到的结果在UserInformation中, 身高体重之类统一保留的两位小数
        double weight = userData.getUserWeight();
        double height = userData.getUserSature();
        double bp = userData.getUserBP();
        double bmi = weight *10000 /(height*height);
        String bmiText = String.format("%.2f", bmi);    //保留两位小数
        editTextBmi.setText(bmiText);
        int age = userData.getUserAge();
        editTextHeight.setText(String.format("%.2f", height));
        editTextWeight.setText(String.format("%.2f",weight));
        editTextPressure.setText(String.format("%.2f", bp));
        editTextAge.setText(String.valueOf(age));


    }
}