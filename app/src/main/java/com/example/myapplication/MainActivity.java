package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button testButton;
    private Button testButton_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = findViewById(R.id.testButton_1);
        testButton_2 = findViewById(R.id.testButton_2);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到textView
                Intent intent;
                intent = new Intent(MainActivity.this,TextViewActivity.class);
                startActivity(intent);
            }
        });

        testButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditTextActivity.class);
                startActivity((intent));
            }
        });
    }

    public void showToast(View view) {
        Toast.makeText(this,"被点击了",Toast.LENGTH_SHORT).show();
    }
}
