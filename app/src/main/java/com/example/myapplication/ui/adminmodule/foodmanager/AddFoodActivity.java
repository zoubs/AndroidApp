package com.example.myapplication.ui.adminmodule.foodmanager;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddFoodActivity extends AppCompatActivity {
    private Button mBtnAdd;
    private EditText etFoodName;
    private EditText etFoodType;
    private EditText etCalorie;
    private String foodName, foodType, calorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mBtnAdd = findViewById(R.id.btn_add_food_submit);
        etFoodName = findViewById(R.id.et_add_food_name);
        etFoodType = findViewById(R.id.et_add_food_type);
        etCalorie = findViewById(R.id.et_add_food_calorie);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodName = etFoodName.getText().toString();
                foodType = etFoodType.getText().toString();
                calorie = etCalorie.getText().toString();
                if(!foodType.isEmpty() && !foodName.isEmpty() && !calorie.isEmpty()) {
                    if(isDecimal(calorie)) {
                        Thread thread;
                        final boolean[] isSuccess = new boolean[1];
                        final boolean[] isExist = new boolean[1];

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();

                                FoodData findFood = foodDataDao.findByFoodName(foodName);
                                if(findFood != null) {
                                    isExist[0] = true;
                                    isSuccess[0] = false;
                                } else {
                                    isSuccess[0] = true;
                                    FoodData foodData = new FoodData();
                                    foodData.setCalorie(Double.valueOf(calorie));
                                    foodData.setFoodSpecies(foodType);
                                    foodData.setFoodName(foodName);
                                    Date date = new Date();
                                    foodData.setUpdateTime(new Timestamp(date.getTime()));
                                    isSuccess[0] = foodDataDao.insert(foodData);
                                }
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        if(isExist[0]) {
                            Toast.makeText(AddFoodActivity.this, "无法添加，该食物已存在", Toast.LENGTH_SHORT).show();
                        } else {
                            if(isSuccess[0]){
                                Toast.makeText(AddFoodActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                etCalorie.setText("");
                                etFoodType.setText("");
                            } else {
                                Toast.makeText(AddFoodActivity.this, "添加失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(AddFoodActivity.this, "食物卡路里输入不规范", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddFoodActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean isDecimal(String string) {
        return Pattern.compile("([1-9][0-9]*|0)(\\.[\\d]+)?").matcher(string).matches();
    }
}