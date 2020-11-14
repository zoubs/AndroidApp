package com.example.myapplication.ui.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.DaoImpl.DietDaoImpl;
import com.example.myapplication.DaoImpl.FeedbackDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.Feedback;
import com.example.myapplication.R;
import com.example.myapplication.VO.DietVO;
import com.example.myapplication.ui.recorddiet.DietAdapter;
import com.example.myapplication.ui.recorddiet.DietRecord;
import com.example.myapplication.ui.recorddiet.FindDietActivity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class FeedbackRecordActivity extends AppCompatActivity {
    private ListView myListView;
    private List<Feedback> feedbackRecords;
    private GlobalInfo globalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_record);
        myListView = findViewById(R.id.lv_feedback_record);
        globalInfo = (GlobalInfo)getApplication();

        FeedbackAdapter clear = new FeedbackAdapter(new LinkedList<FeedbackRecord>(), FeedbackRecordActivity.this);
        myListView.setAdapter(clear);

        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FeedbackDaoImpl feedbackDao = new FeedbackDaoImpl();
                feedbackRecords = feedbackDao.findBySubmitterID(globalInfo.getNowUserId());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinkedList<FeedbackRecord> mData = new LinkedList<FeedbackRecord>();
        if (!feedbackRecords.isEmpty()) {
            for (Feedback feedback : feedbackRecords) {
                String status = feedback.getIsResolved() == 0 ? "未解决" : "已解决";
                if(status.equals("未解决")) {
                    mData.add(new FeedbackRecord(feedback.getQuestionTitle(), feedback.getQuestionContent(), "再等等吧，反馈尚未得到回复", status));
                } else {
                    mData.add(new FeedbackRecord(feedback.getQuestionTitle(), feedback.getQuestionContent(), feedback.getFeedbackContent(), status));
                }
            }
            FeedbackAdapter adapter = new FeedbackAdapter(mData, FeedbackRecordActivity.this);
            myListView.setAdapter(adapter);
        } else {  //没有记录，提醒添加
            mData.add(new FeedbackRecord("", "", "", ""));
            FeedbackAdapter remind = new FeedbackAdapter(mData, FeedbackRecordActivity.this);

            myListView.setAdapter(remind);
            Toast.makeText(FeedbackRecordActivity.this, "啊哦，您还没有提交过反馈", Toast.LENGTH_SHORT).show();
        }

    }
}