package com.example.myapplication.ui.adminmodule.feedbackmanager;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.FeedbackDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.Feedback;
import com.example.myapplication.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class FeedbackManagerFragment extends Fragment {

    private FeedbackManagerViewModel mViewModel;
    private EditText etTittle, etContext, etReply;
    private int position, count;
    private Button mBtnFinish;
    private GlobalInfo globalInfo;
    private List<Feedback> feedbacks;

    public static FeedbackManagerFragment newInstance() {
        return new FeedbackManagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feedback_manager_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FeedbackManagerViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        globalInfo = (GlobalInfo) getActivity().getApplication();

        mBtnFinish = view.findViewById(R.id.btn_feedback_reply);
        etTittle = view.findViewById(R.id.ed_feedback_title_reply);
        etContext = view.findViewById(R.id.et_feedback_reply);
        etReply = view.findViewById(R.id.et_feedback_reply_context);

        etTittle.setInputType(InputType.TYPE_NULL);
        etContext.setInputType(InputType.TYPE_NULL);

        Thread threadFind;
        threadFind = new Thread(new Runnable() {
            @Override
            public void run() {
                FeedbackDaoImpl feedbackDao = new FeedbackDaoImpl();
                feedbacks = feedbackDao.findUnresolved();
            }
        });
        threadFind.start();
        try {
            threadFind.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        position = 0;
        count = feedbacks.size();
        if(!feedbacks.isEmpty()) {
            Feedback feedback = feedbacks.get(position);
            etTittle.setText(feedback.getQuestionTitle());
            etContext.setText(feedback.getQuestionContent());
        } else {
            //系统中没有待解决的反馈信息
            Toast.makeText(getActivity(), "暂时没有未解决的反馈哦！", Toast.LENGTH_SHORT).show();
        }
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!feedbacks.isEmpty()) {
                    final Feedback feedback = feedbacks.get(position);
                    String reply = etReply.getText().toString();
                    Feedback feedbackReply = new Feedback();
                    feedback.setIsResolved(1);
                    feedback.setReplierID(globalInfo.getNowUserId());
                    feedback.setFeedbackContent(reply);
                    Date date = new Date();
                    feedback.setFeedbackTime(new Timestamp(date.getTime()));

                    Thread thread;
                    final boolean[] isSuccess = new boolean[1];
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FeedbackDaoImpl feedbackDao = new FeedbackDaoImpl();
                            isSuccess[0] = feedbackDao.update(feedback);
                            feedbacks = feedbackDao.findUnresolved();   //更新内容
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(isSuccess[0]) {
                        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
                        if(!feedbacks.isEmpty()) {
                            Feedback feedbackTmp = feedbacks.get(0);
                            etTittle.setText(feedbackTmp.getQuestionTitle());
                            etContext.setText(feedbackTmp.getQuestionContent());  //显示下一条
                            etReply.setText("");
                        } else {
                            Toast.makeText(getActivity(), "暂时没有未解决的反馈哦！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "操作失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        if(!feedbacks.isEmpty()) {
                            Feedback feedbackTmp = feedbacks.get(0);
                            etTittle.setText(feedbackTmp.getQuestionTitle());
                            etContext.setText(feedbackTmp.getQuestionContent());  //显示下一条
                            etReply.setText("");
                        } else {
                            Toast.makeText(getActivity(), "暂时没有未解决的反馈哦！", Toast.LENGTH_SHORT).show();
                            etTittle.setText("");
                            etReply.setText("");
                            etContext.setText("");
                        }
                    }
                }

            }
        });

    }
}