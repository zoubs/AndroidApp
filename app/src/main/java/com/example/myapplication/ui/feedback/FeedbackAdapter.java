package com.example.myapplication.ui.feedback;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.ui.recorddiet.DietRecord;

import java.util.LinkedList;

public class FeedbackAdapter extends BaseAdapter {

    private LinkedList<FeedbackRecord> mData;
    private Context mContext;

    public FeedbackAdapter(LinkedList<FeedbackRecord> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_feedback_record,parent,false);
        EditText tittle = convertView.findViewById(R.id.item_feedback_title);
        EditText context = convertView.findViewById(R.id.item_feedback_describe);
        EditText reply = convertView.findViewById(R.id.item_feedback_reply);
        EditText status = convertView.findViewById(R.id.item_feedback_status);

        tittle.setInputType(InputType.TYPE_NULL);
        context.setInputType(InputType.TYPE_NULL);
        reply.setInputType(InputType.TYPE_NULL);
        status.setInputType(InputType.TYPE_NULL);

        tittle.setText(mData.get(position).getTittle());
        context.setText(mData.get(position).getContext());
        reply.setText(mData.get(position).getReply());
        status.setText(mData.get(position).getStatus());
        return  convertView;
    }
}
