package com.example.myapplication.ui.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class RecommendFragment extends Fragment {

    private RecommendViewModel recommendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recommendViewModel =
                ViewModelProviders.of(this).get(RecommendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        final TextView textView = root.findViewById(R.id.text_recommend);
        recommendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}