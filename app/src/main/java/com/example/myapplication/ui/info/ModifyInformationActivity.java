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
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.PO.User;
import com.example.myapplication.R;
import com.example.myapplication.VO.OrdinaryUser;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ModifyInformationActivity extends AppCompatActivity {
    private EditText editTextAge, editTextUserName, editTextWeight, editTextHeight, editTextPressure;
    private Button mBtnSubmit;
    private OrdinaryUser ordinaryUser;
    private GlobalInfo globalInfo;
    private boolean isExist;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);
        globalInfo = (GlobalInfo)getApplication();

        editTextUserName = findViewById(R.id.et_user_nickname_modify);
        editTextAge = findViewById(R.id.et_user_age_modify);
        //editTextUserName = findViewById(R.id.et_user_nickname_modify);
        editTextHeight = findViewById(R.id.et_user_height_modify);
        editTextWeight = findViewById(R.id.et_user_weight_modify);
        editTextPressure = findViewById(R.id.et_user_blood_pressure_modify);
        mBtnSubmit = findViewById(R.id.user_info_submit_modify);



        final Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                ordinaryUser = userDao.findOrdinaryByID(globalInfo.getNowUserId());
                isExist = ordinaryUser != null;
                if (isExist) {
                    OrdinaryUserData userDetail = ordinaryUser.getUserData();

                    double weight = userDetail.getUserWeight();
                    double height = userDetail.getUserSature();
                    double bp = userDetail.getUserBP();
                    double bmi = weight *10000 /(height*height);
                    String bmiText = String.format("%.2f", bmi);    //保留两位小数
                    int age = userDetail.getUserAge();

                    editTextUserName.setText(globalInfo.getUserName());
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

        if(!isExist) {   //还未添加过信息
            editTextUserName.setText(globalInfo.getUserName());
            editTextHeight.setText("");
            editTextWeight.setText("");
            editTextPressure.setText("");
            editTextAge.setText("");
        }



        editTextAge.setInputType(InputType.TYPE_NULL);

        editTextAge.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showNumberPickDlg();
                    return true;
                }
                return false;
            }

        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userBaseInfo = new User();
                final OrdinaryUserData userData = new OrdinaryUserData();
                final OrdinaryUser ordinaryUserModify = new OrdinaryUser();
                String age = editTextAge.getText().toString();
                final String userName = editTextUserName.getText().toString();
                String height = editTextHeight.getText().toString();
                String weight = editTextWeight.getText().toString();
                String pressure = editTextPressure.getText().toString();

                boolean re = isValidName(userName);

                if (!age.isEmpty() && !userName.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !pressure.isEmpty()) {
                    boolean valid = true;

                    if (!isValidName(userName)) {
                        Toast.makeText(ModifyInformationActivity.this, "用户名不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    else if (!isDecimal(height)) {
                        Toast.makeText(ModifyInformationActivity.this, "身高数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    else if (!isDecimal(weight)) {
                        Toast.makeText(ModifyInformationActivity.this, "体重数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    else if (!isDecimal(pressure)) {
                        Toast.makeText(ModifyInformationActivity.this, "血压数据填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    else if (!isValidName(userName)) {
                        Toast.makeText(ModifyInformationActivity.this, "用户名填写不规范", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }
                    if (valid) {

                        Thread thread1; //查看新用户名是否已经存在
                        final boolean[] isNameExist = new boolean[1];
                        thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserDaoImpl userDao = new UserDaoImpl();
                                User userNewName = userDao.findUserByName(userName);
                                isNameExist[0] = userNewName != null;
                            }
                        });
                        thread1.start();
                        try {
                            thread1.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        userData.setUserID(globalInfo.getNowUserId());
                        userData.setUserWeight(Double.parseDouble(weight));
                        userData.setUserSature(Double.parseDouble(height));
                        userData.setUserAge(Integer.parseInt(age));
                        userData.setUserBP(Double.parseDouble(pressure));
                        if(isNameExist[0] && !userName.equals(globalInfo.getUserName())) {  //修改了，且已存在
                            Toast.makeText(ModifyInformationActivity.this, "该用户名已被占用", Toast.LENGTH_SHORT).show();
                        }
                        else {  //没有修改用户名或者改了用户名，但是用户名不存在
                            if(isExist) {  //存在详细信息的记录则更新
                                ordinaryUserModify.setUserData(userData);

                                User baseInfo = new User();
                                baseInfo.setUserName(userName);  //更新用户名
                                baseInfo.setUserEmail(globalInfo.getUserEmail());
                                baseInfo.setUserPassword(globalInfo.getUserPassword());
                                baseInfo.setUserID(globalInfo.getNowUserId());
                                baseInfo.setUserType("User");
                                ordinaryUserModify.setBaseInfo(baseInfo);

                                Thread threadUpdate;
                                final boolean[] isSuccess = new boolean[1];
                                threadUpdate = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UserDaoImpl userDao = new UserDaoImpl();
                                        isSuccess[0] = userDao.updateOrdinary(ordinaryUserModify);
                                    }
                                });
                                threadUpdate.start();
                                try {
                                    threadUpdate.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (isSuccess[0]) {
                                    globalInfo.setUserName(userName);
                                    Toast.makeText(ModifyInformationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ModifyInformationActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                }
                            } else {  //不存在用户的详细信息记录， 进行插入

                                ordinaryUserModify.setUserData(userData);

                                User baseInfo = new User();
                                baseInfo.setUserName(userName);  //更新用户名
                                baseInfo.setUserEmail(globalInfo.getUserEmail());
                                baseInfo.setUserPassword(globalInfo.getUserPassword());
                                baseInfo.setUserID(globalInfo.getNowUserId());
                                baseInfo.setUserType("User");
                                ordinaryUserModify.setBaseInfo(baseInfo);


                                Thread threadInsert;
                                final boolean[] isSuccess = new boolean[2];
                                threadInsert = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UserDaoImpl userDao = new UserDaoImpl();
                                        isSuccess[0] = userDao.insertUserDetailInfo(userData);
                                        isSuccess[1] = userDao.updateOrdinary(ordinaryUserModify);
                                    }
                                });
                                threadInsert.start();
                                try {
                                    threadInsert.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (isSuccess[0]) {
                                    globalInfo.setUserName(userName);
                                    Toast.makeText(ModifyInformationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ModifyInformationActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        //showConfirmDialog();
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
    public boolean isInteger(String string) {
        return Pattern.compile("[1-9][0-9]*|0").matcher(string).matches();
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
                editTextAge.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void showNumberPickDlg() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View view =  getLayoutInflater().inflate(R.layout.sleep_number_picker, null);
        final NumberPicker numberPicker = view.findViewById(R.id.number_picker);

        numberPicker.setValue(20);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(70);

        builder.setView(view);
        builder.setTitle("选择您的年龄");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int age = numberPicker.getValue();
                dialogInterface.cancel();
                editTextAge.setText(String.valueOf(age));
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

    protected void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyInformationActivity.this);
        builder.setTitle("确认修改吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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