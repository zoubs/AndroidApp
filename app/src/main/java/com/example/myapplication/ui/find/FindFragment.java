package com.example.myapplication.ui.find;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PO.FoodData;
import com.example.myapplication.R;

public class FindFragment extends Fragment {

    private FindViewModel findViewModel;
    private EditText etFoodName, etFoodType, etFoodCalorie;
    private Button mBtnSubmit;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        findViewModel =
                ViewModelProviders.of(this).get(FindViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find, container, false);


        //todo 查询食物这块，不知道食物数据存在哪里，你看看这块应该怎么写比较好
        FoodData foodData = new FoodData();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnSubmit = view.findViewById(R.id.btn_find_food_submit);
        etFoodName = view.findViewById(R.id.et_find_food_name);
        etFoodType = view.findViewById(R.id.et_find_food_type);
        etFoodCalorie = view.findViewById(R.id.et_find_food_calorie);

        etFoodType.setInputType(InputType.TYPE_NULL);
        etFoodCalorie.setInputType(InputType.TYPE_NULL);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFoodName.getText().toString();
                if(!name.isEmpty()) {
                    //todo 查询
                }
            }
        });
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap = getResources().getDimensionPixelSize(R.dimen.dividerHeight);
            outRect.set(gap,gap,gap,gap);
        }
    }
}