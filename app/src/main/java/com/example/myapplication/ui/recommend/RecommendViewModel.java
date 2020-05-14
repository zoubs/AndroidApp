package com.example.myapplication.ui.recommend;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecommendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecommendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recommend fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}