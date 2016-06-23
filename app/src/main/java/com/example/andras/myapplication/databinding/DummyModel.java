package com.example.andras.myapplication.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.andras.myapplication.BR;
import com.example.andras.myapplication.R;

/**
 * Created by Andras_Nemeth on 2016. 06. 20..
 */
public class DummyModel extends BaseObservable{

    private String textToBind = "This is a textToBind from the model";
    private int resource = R.string.data_binding_test;
    private String anotherText;

    @Bindable
    public String getText() {
        return textToBind;
    }

    public int getResource() {
        return resource;
    }

    public void setText(String text) {
        this.textToBind = text;
        //Bindig repository listens to the getter/setter name, not the field
        notifyPropertyChanged(BR.text);
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    @Bindable
    public String getAnotherText() {
        return anotherText;
    }

    public void setAnotherText(String anotherText) {
        this.anotherText = anotherText;
        notifyPropertyChanged(BR.anotherText);
    }
}
