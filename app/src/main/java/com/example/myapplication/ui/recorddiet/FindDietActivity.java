package com.example.myapplication.ui.recorddiet;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.DietDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.Diet;
import com.example.myapplication.R;
import com.example.myapplication.VO.DietVO;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class FindDietActivity extends AppCompatActivity {

    private GlobalInfo globalInfo;
    private Button mBtnFind;
    private ListView myListView;
    private EditText dietDate;
    private List<DietVO> dietVO;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_diet);
        dietDate = findViewById(R.id.et_record_diet_date);
        mBtnFind = findViewById(R.id.btn_record_diet);
        myListView = findViewById(R.id.lv_diet_record);
        globalInfo = (GlobalInfo) getApplication();

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


        mBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fixme 可以设置监听日期文本变化即刷新
                DietAdapter clear = new DietAdapter(new LinkedList<DietRecord>(), FindDietActivity.this);
                myListView.setAdapter(clear);
                final String date = dietDate.getText().toString();
                if(!date.isEmpty()){
                    Thread thread;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DietDaoImpl dietDao = new DietDaoImpl();
                            dietVO = dietDao.findTimeBetween(globalInfo.getNowUserId(), Timestamp.valueOf(date + " 00:00:00"), Timestamp.valueOf(date + " 23:59:59"));
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LinkedList<DietRecord> mData = new LinkedList<DietRecord>();
                    if(!dietVO.isEmpty()) {
                        for (DietVO vo : dietVO) {
                            mData.add(new DietRecord(vo.getFoodName(), vo.getFoodNumber().toString()));
                        }
                        DietAdapter adapter = new DietAdapter(mData, FindDietActivity.this);
                        myListView.setAdapter(adapter);
                    } else {  //没有记录，提醒添加
                        Toast.makeText(FindDietActivity.this, "这天还没有记录哦，请先添加饮食记录", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(FindDietActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
                }
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