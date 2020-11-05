package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplication.R;

public class InfoActivity extends AppCompatActivity {
    private EditText editTextBirthday, editTextUserName, editTextWeight, editTextHeight, editTextPressureLow,editTextPressureHigh, editTextSex, editTextBmi;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        UserDetailInfo userInformation = new UserDetailInfo();
        //fixme 设了个默认值，以后删除
        userInformation.setBirthday("2000-10-11");
        userInformation.setHeight("174.3");
        userInformation.setWeight("70.2");
        userInformation.setNickName("鹿鸣");
        userInformation.setPressureLow("70.2");
        userInformation.setPressureHigh("140.3");
        userInformation.setSex("男");


        editTextBirthday = findViewById(R.id.et_user_birthday);
        editTextUserName = findViewById(R.id.et_user_nickname);
        editTextHeight = findViewById(R.id.et_user_height);
        editTextWeight = findViewById(R.id.et_user_weight);
        editTextPressureLow = findViewById(R.id.et_user_blood_pressure_low);
        editTextPressureHigh= findViewById(R.id.et_user_blood_pressure_high);
        editTextSex = findViewById(R.id.et_user_sex);
        editTextBmi = findViewById(R.id.et_user_bmi);

        //todo 看用户是否已经完善信息，也可以在注册成功后直接跳转到完善信息页面，完善之后才能使用，这样好像会简单很多
        //假定查询到的结果在UserInformation中, 身高体重之类统一保留的两位小数
        String weight = userInformation.getWeight();
        String height = userInformation.getHeight();
        editTextUserName.setText(userInformation.getNickName());
        editTextBirthday.setText(userInformation.getBirthday());
        editTextHeight.setText(height);
        editTextWeight.setText(weight);
        editTextPressureLow.setText(userInformation.getPressureLow());
        editTextPressureHigh.setText(userInformation.getPressureHigh());
        if (userInformation.getSex().compareTo("男") == 0) {
            editTextSex.setText("男");
        } else {
            editTextSex.setText("女");
        }
        float bmi = Float.parseFloat(weight) / (Float.parseFloat(height)/100 * Float.parseFloat(height)/100);
        String bmiText = String.format("%.2f", bmi);    //保留两位小数
        editTextBmi.setText(bmiText);
    }
}