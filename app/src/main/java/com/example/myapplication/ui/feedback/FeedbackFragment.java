package com.example.myapplication.ui.feedback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.ui.feedback.FeedBack;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class FeedbackFragment extends Fragment {
    private FeedbackViewModel feedbackViewModel;
    private EditText feedbackTittle, feedbackContext;
    private Button mBtnSubmit, mBtnRecord;
    private FeedBack feedBackInformation;
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

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataSubmitConfirm();
            }
        });

    }

    private void submitFeedbackData() {
        String quesTittle = feedbackTittle.getText().toString();
        String questionContext = feedbackContext.getText().toString();
        if (!quesTittle.isEmpty() && !questionContext.isEmpty()) {
            //todo 两个数据已获得，进行数据库操作,还需要获取用户名存数据库表

            feedBackInformation.setQuestionContext(questionContext);
            feedBackInformation.setQuesTittle(quesTittle);

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