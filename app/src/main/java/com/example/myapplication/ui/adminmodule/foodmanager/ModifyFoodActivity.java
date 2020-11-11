package com.example.myapplication.ui.adminmodule.foodmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class ModifyFoodActivity extends AppCompatActivity {

    private Button mBtnFindFood, mBtnModifyFood;
    private EditText etFoodNamePre, etFoodName,etFoodType,etFoodCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_food);

        mBtnFindFood = findViewById(R.id.btn_modify_food_find);
        mBtnModifyFood = findViewById(R.id.btn_modify_food_submit);

        etFoodNamePre = findViewById(R.id.et_food_name_pre_modify);
        etFoodName = findViewById(R.id.et_modify_food_name);
        etFoodType = findViewById(R.id.et_modify_food_type);
        etFoodCalorie = findViewById(R.id.et_modify_food_calorie);


    }
}