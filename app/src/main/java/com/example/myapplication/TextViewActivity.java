package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TextViewActivity extends AppCompatActivity {

    private TextView colorChange;
    private TextView runText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        colorChange = findViewById(R.id.deleteLine);
        colorChange.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        colorChange.getPaint().setAntiAlias(true);

        runText = findViewById(R.id.runText);
        runText.setSelected(true);
    }
}
