package com.example.andras.myapplication.sniplets;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.andras.myapplication.Log;
import com.example.andras.myapplication.R;

/**
 * Created by Andras Nemeth on 2016. 10. 31..
 */

public class ConstraintLayoutPerformanceActivity extends AppCompatActivity {

    public static final String EXTRA_START_TIME = "startTime";

    private static final String TAG = "ConstraintLayoutPerform";
    private long start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start = getIntent().getLongExtra(EXTRA_START_TIME, System.currentTimeMillis());
        setContentView(R.layout.constraint_layout_performance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long duration = System.currentTimeMillis() - start;
        ((TextView)findViewById(R.id.textView)).setText(String.valueOf(duration));
        Log.d(TAG, "xxxx Layout inflation time: " + duration);

    }
}
