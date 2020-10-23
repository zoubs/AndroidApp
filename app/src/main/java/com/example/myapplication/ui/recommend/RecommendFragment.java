package com.example.myapplication.ui.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

import java.util.Map;

public class RecommendFragment extends Fragment {

    private RecommendViewModel recommendViewModel;
    private ImageView imageViewLeftTop, imageViewRightTop, imageViewLeftDown, imageViewRightDown;
    private Button mBtnAccept, mBtnNextGroup;

    private int[][] foodGroup = {
            {R.drawable.meat_recommend, R.drawable.rice_recommend, R.drawable.milk_recommend, R.drawable.orange_recommend},
            {R.drawable.egg_recommend, R.drawable.bread_recommend, R.drawable.vegetable_recommend, R.drawable.milk_recommend},
            {R.drawable.crisps, R.drawable.vegetable_dog, R.drawable.lemon_panda, R.drawable.vita_panda}
    };
    private int recommendIndex, groupCount = 3;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recommendViewModel =
                ViewModelProviders.of(this).get(RecommendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        final TextView textView = root.findViewById(R.id.text_recommend);
        recommendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        imageViewLeftTop = view.findViewById(R.id.recommend_image1);
        imageViewRightTop = view.findViewById(R.id.recommend_image2);
        imageViewLeftDown = view.findViewById(R.id.recommend_image3);
        imageViewRightDown = view.findViewById(R.id.recommend_image4);
        mBtnAccept = view.findViewById(R.id.btn_accept_recommend);
        mBtnNextGroup = view.findViewById(R.id.btn_next_group);



        setFoodRecommend();

        mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 看点接受要显示什么
            }
        });

        mBtnNextGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fixme 此时只预留了3组12张图片，后续可以加
                recommendIndex = (recommendIndex + 1) % groupCount;
                setFoodRecommend();

            }
        });
    }
    private void setFoodRecommend(){
        imageViewLeftTop.setImageResource(foodGroup[recommendIndex][0]);
        imageViewRightTop.setImageResource(foodGroup[recommendIndex][1]);
        imageViewLeftDown.setImageResource(foodGroup[recommendIndex][2]);
        imageViewRightDown.setImageResource(foodGroup[recommendIndex][3]);
    }
}