package com.example.myapplication.ui.recorddiet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecordFoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecordFoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recordfood fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}