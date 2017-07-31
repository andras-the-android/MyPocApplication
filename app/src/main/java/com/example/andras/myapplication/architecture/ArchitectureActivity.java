package com.example.andras.myapplication.architecture;

import android.arch.lifecycle.LifecycleActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.andras.myapplication.R;


public class ArchitectureActivity extends LifecycleActivity {

    private TextView text1;
    private ArchitectureViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);
        text1 = findViewById(R.id.text1);
        viewModel = new ArchitectureViewModel(SomeDataProducerService.INSTANCE);
        viewModel.setView(this);
        getLifecycle().addObserver(viewModel);
    }

    public void displayText(String s) {
        text1.setText(s);
    }
}
