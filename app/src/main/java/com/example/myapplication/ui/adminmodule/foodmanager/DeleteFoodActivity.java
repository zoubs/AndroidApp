package com.example.myapplication.ui.adminmodule.foodmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class DeleteFoodActivity extends AppCompatActivity {

    private Button mBtnFind, mBtnDel;
    private EditText etFoodNameFind, etFoodName, etFoodType, etCalorie;

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
    }
}