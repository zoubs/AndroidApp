package com.example.myapplication.ui.recordfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class RecordFoodFragment extends Fragment {

    private RecordFoodViewModel recordFoodViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordFoodViewModel =
                ViewModelProviders.of(this).get(RecordFoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_record_food, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        recordFoodViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}