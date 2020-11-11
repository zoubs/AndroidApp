package com.example.myapplication.ui.recordfood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Calendar;

public class FindDietActivity extends AppCompatActivity {

    private Button mBtnNext;
    private EditText dietDate, dietType1,dietType2, dietType3,dietFood1,dietFood2,dietFood3,dietNumber1,dietNumber2,dietNumber3;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_diet);
        mBtnNext = findViewById(R.id.btn_record_diet);
        dietDate = findViewById(R.id.et_record_diet_date);
        dietType1 = findViewById(R.id.et_diet_record_type1);
        dietType2 = findViewById(R.id.et_diet_record_type2);
        dietType3 = findViewById(R.id.et_diet_record_type3);
        dietFood1 = findViewById(R.id.et_diet_record_food_name1);
        dietFood2 = findViewById(R.id.et_diet_record_food_name2);
        dietFood3 = findViewById(R.id.et_diet_record_food_name3);
        dietNumber1 = findViewById(R.id.et_diet_record_food_number1);
        dietNumber2 = findViewById(R.id.et_diet_record_food_number2);
        dietNumber3 = findViewById(R.id.et_diet_record_food_number3);

        dietType1.setInputType(InputType.TYPE_NULL);
        dietType2.setInputType(InputType.TYPE_NULL);
        dietType3.setInputType(InputType.TYPE_NULL);
        dietFood1.setInputType(InputType.TYPE_NULL);
        dietFood2.setInputType(InputType.TYPE_NULL);
        dietFood3.setInputType(InputType.TYPE_NULL);
        dietNumber1.setInputType(InputType.TYPE_NULL);
        dietNumber2.setInputType(InputType.TYPE_NULL);
        dietNumber3.setInputType(InputType.TYPE_NULL);


        dietDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 查询数据
            }
        });
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();

        //THEME_HOLO_LIGHT,
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                dietDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //fixme 弹出软键盘bug修复, 回车不接受
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        // 弹出Dialog的时候会弹出输入法，将弹出的输入法关闭。必须在show()方法之后调用
        datePickerDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}