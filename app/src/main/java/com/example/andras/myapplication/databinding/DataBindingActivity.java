package com.example.andras.myapplication.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andras.myapplication.R;

public class DataBindingActivity extends AppCompatActivity {

    private DummyModel model = new DummyModel();
    private Switchable switchable =  new Switchable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding is generated from the name of the layout
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setModel(model);
        binding.setSwitchable(switchable);
        binding.submitButton.setOnClickListener(view -> model.setText(binding.textView.getText().toString()));
    }


}
