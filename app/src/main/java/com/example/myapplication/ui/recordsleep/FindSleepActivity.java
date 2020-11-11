package com.example.myapplication.ui.recordsleep;

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

public class FindSleepActivity extends AppCompatActivity {

    private Button mBtnSubmit;
    EditText editTextDate, editTextLength;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_sleep);
        editTextDate = findViewById(R.id.et_record_sleep_date);
        editTextLength = findViewById(R.id.et_record_sleep_length);
        mBtnSubmit = findViewById(R.id.btn_find_record_sleep);

        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextLength.setInputType(InputType.TYPE_NULL);

        editTextDate.setOnTouchListener(new View.OnTouchListener() {
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
                editTextDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
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