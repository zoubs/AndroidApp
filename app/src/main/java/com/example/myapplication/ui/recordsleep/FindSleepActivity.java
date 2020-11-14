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
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.DietDaoImpl;
import com.example.myapplication.DaoImpl.SleepStateDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.SleepState;
import com.example.myapplication.R;
import com.example.myapplication.VO.DietVO;
import com.example.myapplication.ui.recorddiet.DietAdapter;
import com.example.myapplication.ui.recorddiet.DietRecord;
import com.example.myapplication.ui.recorddiet.FindDietActivity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class FindSleepActivity extends AppCompatActivity {

    private Button mBtnSubmit;
    private ListView myListView;
    private GlobalInfo globalInfo;
    private List<SleepState> sleepStates;
    EditText sleepDate;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_sleep);
        sleepDate = findViewById(R.id.et_record_sleep_date);
        mBtnSubmit = findViewById(R.id.btn_find_record_sleep);
        myListView = findViewById(R.id.lv_sleep_record);
        globalInfo = (GlobalInfo)getApplication();
        sleepDate.setInputType(InputType.TYPE_NULL);

        sleepDate.setOnTouchListener(new View.OnTouchListener() {
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
                SleepAdapter clear = new SleepAdapter(new LinkedList<SleepRecord>(), FindSleepActivity.this);
                myListView.setAdapter(clear);
                final String date = sleepDate.getText().toString();
                if(!date.isEmpty()){
                    Thread thread;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SleepStateDaoImpl sleepStateDao = new SleepStateDaoImpl();
                            sleepStates = sleepStateDao.findByDate(Timestamp.valueOf(date + " 00:00:00"));
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LinkedList<SleepRecord> mData = new LinkedList<SleepRecord>();
                    int i = 1;
                    if(!sleepStates.isEmpty()) {
                        for (SleepState state : sleepStates) {
                            float len = state.getSleepDuration().floatValue() / 60;
                            mData.add(new SleepRecord("当日第"+i +"次睡眠", String.format ("%.2f",len) + " 小时"));
                        }
                        SleepAdapter adapter = new SleepAdapter(mData, FindSleepActivity.this);
                        myListView.setAdapter(adapter);
                    } else {  //没有记录，提醒添加
                        Toast.makeText(FindSleepActivity.this, "这天还没有记录哦，请先添加睡眠记录", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(FindSleepActivity.this, "请将表单填写完整", Toast.LENGTH_SHORT).show();
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
                sleepDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
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