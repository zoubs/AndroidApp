package com.example.myapplication.ui.recordfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class RecordFoodFragment extends Fragment {

    private RecordFoodViewModel recordFoodViewModel;
    private EditText editTextFoodName, editTextCarbohydrate, editTextFat, editTextPr;
    private Button mBtnFoodRecord, mBtnFoodSubmit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordFoodViewModel =
                ViewModelProviders.of(this).get(RecordFoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_record_food, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        recordFoodViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextFoodName = view.findViewById(R.id.et_food_input);
        editTextCarbohydrate = view.findViewById(R.id.et_cabohydrate);
        editTextFat = view.findViewById(R.id.et_fat);
        editTextPr = view.findViewById(R.id.et_pr);
        mBtnFoodRecord = view.findViewById(R.id.btn_food_record);
        mBtnFoodSubmit = view.findViewById(R.id.btn_food_submit);

        editTextFoodName.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (editTextFoodName.getWidth() - editTextFoodName
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editTextFoodName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.down_arrow), null);
                        showListWindow();
                        return true;
                    }
                }
                return false;

            }
        });
    }

    private void showListWindow() {
        //todo 目前没有显示图片，后续可以根据用户的选择，增加对应的食物图片
        final String[] list = {"米饭", "猪肉", "牛肉", "牛奶"};//要填充的数据
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(editTextFoodName);//以哪个控件为基准，在该处以editTextFoodName为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextFoodName.setText(list[i]);//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
    }
}