package com.example.myapplication.ui.recordsleep;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DaoImpl.SleepStateDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.SleepState;
import com.example.myapplication.R;

import java.util.Calendar;
import java.util.Date;

public class RecordSleepFragment extends Fragment {

    private RecordSleepViewModel recordSleepViewModel;
    private Button mBtnUserSleepSubmit, mBtnUserSleepOk, mBtnUserSleepRecord;
    private EditText editTextUserSleepDate, editTextUserSleepLength;
    private int sleepTimeLength;
    private GlobalInfo globalInfo;
    private Date date;
    /*private String[] numbers = {"0.5h", "1.0h", "1.5h", "2h", "2.5h", "3h", "3.5h", "4h", "4.5h", "5h",
                                "5.5h", "6h", "6.5h", "7h", "7.5h", "8h", "8.5h", "9h", "9.5h", "10h", "10h+"};*/

    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "10+"};
    //设置需要显示的内容数组
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordSleepViewModel =
                ViewModelProviders.of(this).get(RecordSleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_record_sleep, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        recordSleepViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        globalInfo = (GlobalInfo)getActivity().getApplication();
        mBtnUserSleepSubmit = view.findViewById(R.id.btn_sleep_submit);
        editTextUserSleepDate = view.findViewById(R.id.et_user_sleep_date);
        editTextUserSleepLength = view.findViewById(R.id.et_user_sleep_length);
        mBtnUserSleepRecord = view.findViewById(R.id.btn_user_sleep_data);

        editTextUserSleepDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        editTextUserSleepLength.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showNumberPickDlg();
                    return true;
                }
                return false;
            }
        });

        mBtnUserSleepSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDataSubmitConfirm();
            }
        });

        mBtnUserSleepRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo 睡眠记录查询
                Intent intent = new Intent(getActivity(), FindSleepActivity.class);
                startActivity(intent);
            }
        });
        /*mBtnUserSleepOk = view.findViewById(R.id.btn_sleep_ok);
        etUserSleepStart = view.findViewById(R.id.et_user_sleep_start);
*/
    }

    protected void showDataSubmitConfirm() {  //弹出对话框提示
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认提交吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                submitSleepData();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    public void submitSleepData() {
        String date = editTextUserSleepDate.getText().toString();
        String length = editTextUserSleepLength.getText().toString();
        final SleepStateDaoImpl sleepStateDao = new SleepStateDaoImpl();
        final SleepState sleepState = new SleepState();
        final boolean[] isSuccess = new boolean[1];
        if(!date.isEmpty() && !length.isEmpty()){
            double len = Double.parseDouble(length)*60;

            long dur = Double.valueOf(len).longValue();
            date += " 00:00:00";
            sleepState.setUserID(globalInfo.getNowUserId());
            sleepState.setSleepDate(Timestamp.valueOf(date));
            sleepState.setSleepDuration(dur);

            Thread myThread;
            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    isSuccess[0] = sleepStateDao.insert(sleepState);
                }
            });
            myThread.start();
            try {
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSuccess[0]) {
                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "请将表单填写完整", Toast.LENGTH_SHORT).show();
        }
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                editTextUserSleepDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void showNumberPickDlg() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view =  getLayoutInflater().inflate(R.layout.sleep_number_picker, null);
        final NumberPicker numberPicker = view.findViewById(R.id.number_picker);

        numberPicker.setValue(12);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(24);

        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.valueOf((float) value / 2);
            }
        });
        builder.setView(view);
        builder.setTitle("设置睡眠时间");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sleepTimeLength = numberPicker.getValue();
                dialogInterface.cancel();
                editTextUserSleepLength.setText(String.valueOf((float) sleepTimeLength / 2));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}