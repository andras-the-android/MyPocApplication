package com.example.andras.myapplication.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.andras.myapplication.R;


public class ArchitectureActivity extends AppCompatActivity {

    private TextView text1;
    ArchitectureViewModel viewModel;
    ArchitectureLiveData liveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architecture);
        Injector.inject(this);

        text1 = findViewById(R.id.text1);

        viewModel = ViewModelProviders.of(this).get(ArchitectureViewModel.class);
        viewModel.setView(this);
        getLifecycle().addObserver(viewModel);

        TextView text2 = findViewById(R.id.text2);
        LiveData<String> transformedLiveData = Transformations.map(liveData, input -> input + " transformed");
        transformedLiveData.observe(this, text2::setText);
    }

    public void displayText(String s) {
        text1.setText(s);
    }
}
