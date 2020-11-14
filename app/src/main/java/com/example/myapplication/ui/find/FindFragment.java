package com.example.myapplication.ui.find;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DaoImpl.FoodDataDaoImpl;
import com.example.myapplication.PO.FoodData;
import com.example.myapplication.R;

public class FindFragment extends Fragment {

    private EditText etFoodName, etFoodType, etFoodCalorie;
    private Button mBtnSubmit;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find, container, false);
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
                final String name = etFoodName.getText().toString();
                final boolean[] isExist = new boolean[1];
                if(!name.isEmpty()) {
                    Thread myThread;
                    myThread = new Thread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            FoodDataDaoImpl foodDataDao = new FoodDataDaoImpl();
                            FoodData foodData = foodDataDao.findByFoodName(name);
                            if(foodData == null) {
                                //Toast.makeText(getActivity(), "该食物尚未添加",Toast.LENGTH_SHORT).show();
                                isExist[0] = false;
                                etFoodType.setText("");
                                etFoodCalorie.setText("");
                            } else {
                                isExist[0] = true;
                                etFoodType.setText(foodData.getFoodSpecies());
                                etFoodCalorie.setText(foodData.getCalorie().toString());
                            }
                        }
                    });
                    myThread.start();
                    try {
                        myThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!isExist[0]) {
                        Toast.makeText(getActivity(), "该食物尚未添加",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}