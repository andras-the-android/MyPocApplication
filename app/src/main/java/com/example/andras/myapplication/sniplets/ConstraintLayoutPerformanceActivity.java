package com.example.andras.myapplication.sniplets;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.andras.myapplication.R;

/**
 * Created by Andras Nemeth on 2016. 10. 31..
 */

public class ConstraintLayoutPerformanceActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long start = System.currentTimeMillis();
        setContentView(R.layout.constraint_layout_performance);
        long duration = System.currentTimeMillis() - start;
        ((TextView)findViewById(R.id.textView)).setText(String.valueOf(duration));
    }
}
