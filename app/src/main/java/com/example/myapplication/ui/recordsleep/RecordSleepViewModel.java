package com.example.myapplication.ui.recordsleep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecordSleepViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecordSleepViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recordsleep fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}