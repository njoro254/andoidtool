package com.example.myapplication.ui.home;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
private  String Name;
    Intent useraccessintent;
    public HomeViewModel() {
        Name="";
       // useraccessintent=Intent
        mText = new MutableLiveData<>();
        mText.setValue("Metadata List "+getEditText());
    }
    public void setText(String name)
    {
        Name=name;
    }
    public String getEditText()
    {
        return  Name;
    }

    public LiveData<String> getText() {
        return mText;
    }
}