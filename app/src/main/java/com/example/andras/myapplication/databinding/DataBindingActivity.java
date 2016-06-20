package com.example.andras.myapplication.databinding;

import android.databinding.DataBindingUtil;
import com.example.andras.myapplication.databinding.ActivityDataBindingBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andras.myapplication.R;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //generated from the name of the layout
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setModel(new DummyModel());
    }
}
