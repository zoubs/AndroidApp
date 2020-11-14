package com.example.myapplication.ui.adminmodule.foodmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.FoodDataDaoImpl;
import com.example.myapplication.PO.FoodData;
import com.example.myapplication.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

public class DeleteFoodActivity extends AppCompatActivity {

    private Button mBtnFind, mBtnDel, mBtnModify;
    private EditText etFoodNameFind, etFoodName, etFoodType, etCalorie;
    private String findName, foodName, foodType, foodCalorie;
    private FoodData foodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);


        mBtnDel = findViewById(R.id.btn_del_food_submit);
        mBtnFind = findViewById(R.id.btn_del_food_find);
        etFoodNameFind = findViewById(R.id.et_food_name_pre_del);
        etFoodName = findViewById(R.id.et_del_food_name);
        etFoodType = findViewById(R.id.et_del_food_type);
        etCalorie = findViewById(R.id.et_del_food_calorie);
        mBtnModify = findViewById(R.id.btn_del_food_modify);

        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findName = etFoodNameFind.getText().toString();
                if (!findName.isEmpty()) {
                    Thread thread;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                            foodData = foodDataDao.findByFoodName(findName);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (foodData == null) {
                        Toast.makeText(DeleteFoodActivity.this, "该食物不存在", Toast.LENGTH_SHORT).show();
                    } else {
                        etCalorie.setText(String.valueOf(foodData.getCalorie()));
                        etFoodName.setText(foodData.getFoodName());
                        etFoodType.setText(foodData.getFoodSpecies());
                        delConfirm();
                    }
                } else {
                    Toast.makeText(DeleteFoodActivity.this, "请填写食物名", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findName = etFoodNameFind.getText().toString();
                if (!findName.isEmpty()) {
                    Thread thread;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                            foodData = foodDataDao.findByFoodName(findName);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (foodData == null) {
                        Toast.makeText(DeleteFoodActivity.this, "该食物还不存在，请先添加食物", Toast.LENGTH_SHORT).show();
                    } else {
                        etCalorie.setText(String.valueOf(foodData.getCalorie()));
                        etFoodName.setText(foodData.getFoodName());
                        etFoodType.setText(foodData.getFoodSpecies());
                    }

                } else {
                    Toast.makeText(DeleteFoodActivity.this, "请填写食物名", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findName = etFoodNameFind.getText().toString();
                if (!findName.isEmpty()) {
                    Thread thread;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                            foodData = foodDataDao.findByFoodName(findName);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (foodData == null) {
                        Toast.makeText(DeleteFoodActivity.this, "该食物还不存在，请先添加食物", Toast.LENGTH_SHORT).show();
                    } else {
                        modifyConfirm();
                    }

                } else {
                    Toast.makeText(DeleteFoodActivity.this, "请填写食物名", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void modifyConfirm() {  //弹出对话框提示
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFoodActivity.this);
        builder.setTitle("确认修改吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                foodName = etFoodName.getText().toString();


                foodType = etFoodType.getText().toString();
                foodCalorie = etCalorie.getText().toString();


                if (!foodCalorie.isEmpty() && !foodType.isEmpty() && !foodName.isEmpty()) {
                    if (isDecimal(foodCalorie)) {
                        Thread threadModify;
                        final boolean[] isSuccess = new boolean[1];
                        final FoodData[] foodDataFind = new FoodData[1];
                        threadModify = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                                foodDataFind[0] = foodDataDao.findByFoodName(foodName);
                                isSuccess[0] =false;
                                if (foodDataFind[0] == null || foodName.equals(foodDataFind[0].getFoodName())) {
                                    Date date = new Date();
                                    foodData.setUpdateTime(new Timestamp(date.getTime()));
                                    foodData.setFoodName(foodName);
                                    foodData.setFoodSpecies(foodType);
                                    foodData.setCalorie(Double.valueOf(foodCalorie));
                                    isSuccess[0] = foodDataDao.update(foodData);
                                }

                            }
                        });
                        threadModify.start();
                        try {
                            threadModify.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!isSuccess[0]) {
                            Toast.makeText(DeleteFoodActivity.this, "修改失败，该食物已存在", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteFoodActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(DeleteFoodActivity.this, "食物卡路里填写不规范", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(DeleteFoodActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
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

    protected void delConfirm() {  //弹出对话框提示
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFoodActivity.this);
        builder.setTitle("确认删除吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Thread threadModify;
                final boolean[] isSuccess = new boolean[1];
                threadModify = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                        isSuccess[0] = foodDataDao.deleteByName(findName);
                    }
                });
                threadModify.start();
                try {
                    threadModify.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess[0]) {
                    Toast.makeText(DeleteFoodActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    etCalorie.setText("");
                    etFoodName.setText("");
                    etFoodType.setText("");
                } else {
                    Toast.makeText(DeleteFoodActivity.this, "删除失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().

                show();

    }

    public boolean isDecimal(String string) {
        return Pattern.compile("([1-9][0-9]*|0)(\\.[\\d]+)?").matcher(string).matches();
    }
}