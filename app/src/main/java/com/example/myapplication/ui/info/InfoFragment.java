package com.example.myapplication.ui.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;


public class InfoFragment extends Fragment {

    private Button mBtnLookOver, mBtnModifyInfo, mBtnModifyPswd, mBtnLogOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLookOver = view.findViewById(R.id.btn_look_over_info);
        mBtnModifyInfo=view.findViewById(R.id.btn_modify_info);
        mBtnModifyPswd = view.findViewById(R.id.btn_modify_password);
        mBtnLogOut = view.findViewById(R.id.btn_log_out);

        mBtnLookOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        mBtnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ModifyInformationActivity.class);
                startActivity(intent);
            }
        });

        mBtnModifyPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ModifyPasswordActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LogOutActivity.class);
                startActivity(intent);
            }
        });
    }
}