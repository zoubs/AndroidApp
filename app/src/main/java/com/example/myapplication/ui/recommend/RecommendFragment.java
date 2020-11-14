package com.example.myapplication.ui.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DaoImpl.UserDaoImpl;
import com.example.myapplication.GlobalInfo;
import com.example.myapplication.PO.OrdinaryUserData;
import com.example.myapplication.R;
import com.example.myapplication.VO.OrdinaryUser;

public class RecommendFragment extends Fragment {
    private int type, index;
    private GlobalInfo globalInfo;
    private double bmi;
    private ImageView imageViewRecommend;
    private Button mBtnNextGroup;

    private int[] foodHealthy =
            {R.drawable.recommend_hea1, R.drawable.recommend_hea2, R.drawable.recommend_hea3,
                    R.drawable.recommend_hea4, R.drawable.recommend_hea5};
    private int[] foodFat = {R.drawable.recommend_fat1, R.drawable.recommend_fat2, R.drawable.recommend_fat3, R.drawable.recommend_fat4, R.drawable.recommend_fat5};
    private int[] foodMuscle = {R.drawable.recommcond_nor1, R.drawable.recommend_nor2, R.drawable.recommend_nor3};
    private int[] foodThin = {R.drawable.recommend_thin, R.drawable.recommend_thin2, R.drawable.recommend_thin3, R.drawable.recommend_thin4, R.drawable.recommeng_thin5};
    private int fatCount = 5, muscle = 3, thinCount = 5, healthyCount = 5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewRecommend = view.findViewById(R.id.recommend_image1);
        mBtnNextGroup = view.findViewById(R.id.btn_next_group);
        mBtnNextGroup.setVisibility(View.INVISIBLE);
        globalInfo = (GlobalInfo) getActivity().getApplication();

        imageViewRecommend.setImageResource(R.drawable.pengyuyan);
        Thread thread;
        final boolean[] isExist = new boolean[1];
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserDaoImpl userDao = new UserDaoImpl();
                OrdinaryUser user = userDao.findOrdinaryByID(globalInfo.getNowUserId());
                if (user != null) {
                    isExist[0] = true;
                    OrdinaryUserData userDetail = user.getUserData();

                    double weight = userDetail.getUserWeight();
                    double height = userDetail.getUserSature();
                    bmi = weight * 10000 / (height * height);

                } else {
                    isExist[0] = false;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        if (isExist[0]) {  //进行推荐
            mBtnNextGroup.setVisibility(View.VISIBLE);
            if (bmi >= 28) {  //超重
                type = 4;
            } else if (bmi < 28 && bmi > 24) {
                //肥胖
                type = 3;
            } else if (bmi >= 18.5 && bmi <= 24) {
                //正常
                type = 2;
            } else if (bmi < 18.5) {
                //偏瘦
                type = 1;
            }

            index = 0;
            mBtnNextGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type == 1) {
                        imageViewRecommend.setImageResource(foodThin[index]);
                        index = (index+1) %thinCount;
                    } else if(type == 2) {
                        imageViewRecommend.setImageResource(foodHealthy[index]);
                        index = (index+1) % healthyCount;
                    } else if (type == 3) {
                        imageViewRecommend.setImageResource(foodMuscle[index]);
                        index = (index + 1) % muscle;
                    } else if (type == 4) {
                        imageViewRecommend.setImageResource(foodFat[index]);
                        index = (index+1) % fatCount;
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "请先完善信息，再来探索这里哦！", Toast.LENGTH_LONG).show();
        }
    }
}