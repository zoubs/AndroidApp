package com.example.myapplication.ui.feedback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DaoImpl.FeedbackDaoImpl;
import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.Feedback;
import com.example.myapplication.R;

import java.sql.Timestamp;
import java.util.Date;

public class FeedbackFragment extends Fragment {
    private FeedbackViewModel feedbackViewModel;
    private EditText feedbackTittle, feedbackContext;
    private Button mBtnSubmit, mBtnRecord;
    private Feedback feedBack;
    private GlobalInfo globalInfo;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedbackViewModel =
                ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        feedbackViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedbackTittle = view.findViewById(R.id.ed_feedback_title);
        feedbackContext= view.findViewById(R.id.et_feedback_describe);
        mBtnSubmit = view.findViewById(R.id.btn_feedback_submit);
        mBtnRecord = view.findViewById(R.id.btn_feedback_record);
        globalInfo = (GlobalInfo)getActivity().getApplication();
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataSubmitConfirm();
            }
        });

        mBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedbackRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void submitFeedbackData() {
        String quesTittle = feedbackTittle.getText().toString();
        String questionContext = feedbackContext.getText().toString();
        final Boolean[] isSuccess = new Boolean[1];
        if (!quesTittle.isEmpty() && !questionContext.isEmpty()) {

            feedBack = new Feedback();
            feedBack.setQuestionContent(questionContext);
            feedBack.setQuestionTitle(quesTittle);
            feedBack.setUserID(globalInfo.getNowUserId());
            feedBack.setIsResolved(0);
            feedBack.setInquiryTime(new Timestamp(new Date().getTime()));

            Thread myThread;
            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FeedbackDaoImpl feedbackDao = new FeedbackDaoImpl();
                    isSuccess[0] = feedbackDao.insert(feedBack);
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

            //Toast.makeText(getContext(), "保存成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "请将表单填写完整", Toast.LENGTH_LONG).show();
        }
    }


    protected void showDataSubmitConfirm() {  //弹出对话框提示
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认提交吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                submitFeedbackData();
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