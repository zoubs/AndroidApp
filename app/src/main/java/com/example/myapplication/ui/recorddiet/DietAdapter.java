package com.example.myapplication.ui.recorddiet;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.LinkedList;

public class DietAdapter extends BaseAdapter {

    private LinkedList<DietRecord> mData;
    private Context mContext;

    public DietAdapter(LinkedList<DietRecord> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_diet_record,parent,false);
        EditText etName = convertView.findViewById(R.id.et_item_diet_record_name);
        EditText etNumber = convertView.findViewById(R.id.et_item_diet_record_number);
        etName.setInputType(InputType.TYPE_NULL);
        etNumber.setInputType(InputType.TYPE_NULL);
        etName.setText(mData.get(position).getFoodName());
        etNumber.setText(mData.get(position).getFoodNumber());
        return  convertView;
    }
}
