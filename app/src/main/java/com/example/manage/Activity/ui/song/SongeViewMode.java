package com.example.manage.Activity.ui.song;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SongeViewMode extends ViewModel {

    private MutableLiveData<String> mText;

    public SongeViewMode() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}