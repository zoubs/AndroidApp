package com.example.myapplication.ui.adminmodule.foodmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class AddFoodActivity extends AppCompatActivity {
    private Button mBtnAdd;
    private EditText etFoodName;
    private EditText etFoodType;
    private EditText etCalorie;
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

            }
        });
    }
}