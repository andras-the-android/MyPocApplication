package com.example.andras.myapplication.databinding;

import com.example.andras.myapplication.R;

/**
 * Created by Andras_Nemeth on 2016. 06. 20..
 */
public class DummyModel {

    private String text = "This is a text from the model";
    private int resource = R.string.data_binding_test;

    public String getText() {
        return text;
    }

    public int getResource() {
        return resource;
    }
}
