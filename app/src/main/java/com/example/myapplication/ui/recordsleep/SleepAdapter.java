package com.example.myapplication.ui.recordsleep;

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

public class SleepAdapter extends BaseAdapter {

    private LinkedList<SleepRecord> mData;
    private Context mContext;

    public SleepAdapter(LinkedList<SleepRecord> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sleep_record,parent,false);
        EditText etIndex = convertView.findViewById(R.id.et_item_sleep_record_index);
        EditText etLength = convertView.findViewById(R.id.et_item_sleep_record_length);
        etIndex.setInputType(InputType.TYPE_NULL);
        etLength.setInputType(InputType.TYPE_NULL);

        etIndex.setText(mData.get(position).getIndex());
        etLength.setText(mData.get(position).getSleepLength());
        return  convertView;
    }
}
