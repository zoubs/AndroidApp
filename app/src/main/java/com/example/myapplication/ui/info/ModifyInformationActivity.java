package com.example.myapplication.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyInformationActivity extends AppCompatActivity {
    private EditText editTextBirthday, editTextUserName, editTextWeight, editTextHeight, editTextPressureLow,editTextPressureHigh;
    private Button mBtnSubmit;
    private RadioGroup radioGroupSex;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);

        editTextBirthday = findViewById(R.id.et_user_birthday_modify);
        editTextUserName = findViewById(R.id.et_user_nickname_modify);
        editTextHeight = findViewById(R.id.et_user_height_modify);
        editTextWeight = findViewById(R.id.et_user_weight_modify);
        editTextPressureLow = findViewById(R.id.et_user_blood_pressure_low_modify);
        editTextPressureHigh= findViewById(R.id.et_user_blood_pressure_high_modify);
        radioGroupSex = findViewById(R.id.user_sex_group_modify);
        mBtnSubmit = findViewById(R.id.user_info_submit_modify);


        editTextBirthday.setInputType(InputType.TYPE_NULL);

        //fixme 两种方法，我觉得OnTouch好点
        editTextBirthday.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }

        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetailInfo userDetailInfo = new UserDetailInfo();
                String birthday = editTextBirthday.getText().toString();
                String userName = editTextUserName.getText().toString();
                String height = editTextHeight.getText().toString();
                String weight = editTextWeight.getText().toString();
                String pressureLow = editTextPressureLow.getText().toString();
                String pressureHigh = editTextPressureHigh.getText().toString();


                RadioButton radioButton = (RadioButton) radioGroupSex.getChildAt(0);
                String sex = radioButton.isChecked() ? "男" : "女";
                boolean re = isValidName(userName);

                if (!birthday.isEmpty() && !userName.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !pressureLow.isEmpty() && !pressureHigh.isEmpty()) {
                    boolean valid = true;

                    if (!isValidName(userName)) {
                        Toast.makeText(ModifyInformationActivity.this, "用户名不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (!isDecimal(height)) {
                        Toast.makeText(ModifyInformationActivity.this, "身高数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (!isDecimal(weight)) {
                        Toast.makeText(ModifyInformationActivity.this, "体重数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (!isDecimal(pressureHigh) || !isDecimal(pressureLow)) {
                        Toast.makeText(ModifyInformationActivity.this, "血压数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (!isValidName(userName)) {
                        Toast.makeText(ModifyInformationActivity.this, "昵称填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (valid) {
                        userDetailInfo.setNickName(userName);
                        userDetailInfo.setBirthday(birthday);
                        userDetailInfo.setSex(sex);
                        userDetailInfo.setHeight(String.format("%.2f",  Float.parseFloat(height)));
                        userDetailInfo.setWeight(String.format("%.2f", Float.parseFloat(weight)));
                        userDetailInfo.setPressureLow(String.format("%.2f", Float.parseFloat(pressureLow)));
                        userDetailInfo.setPressureHigh(String.format("%.2f", Float.parseFloat(pressureHigh)));

                        showConfirmDialog();
                    }
                } else {
                    Toast.makeText(ModifyInformationActivity.this, "请将表单填写完整",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean isDecimal(String string) {
        return Pattern.compile("([1-9][0-9]*|0)(\\.[\\d]+)?").matcher(string).matches();
    }
    public boolean isValidName(String name) {
        boolean res = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*").matcher(name).matches();
        if (res) {
            if (name.length() > 6) {
                res = false;
            }
        }
        return res;
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ModifyInformationActivity.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                editTextBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyInformationActivity.this);
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