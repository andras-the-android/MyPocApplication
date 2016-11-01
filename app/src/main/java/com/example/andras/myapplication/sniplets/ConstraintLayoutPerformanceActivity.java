package com.example.andras.myapplication.sniplets;

import android.os.Bundle;
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
    private long activityStartTime;
    private long inflationDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStartTime = getIntent().getLongExtra(EXTRA_START_TIME, System.currentTimeMillis());
        long inflationStartTime = System.currentTimeMillis();
//        setContentView(R.layout.constraint_layout_performance);
        setContentView(R.layout.constraint_layout_performance_linear);
        inflationDuration = System.currentTimeMillis() - inflationStartTime;
    }

    @Override
    protected void onResume() {
        super.onResume();
        long activityStartDuration = System.currentTimeMillis() - activityStartTime;
        Log.d(TAG, "xxxx Layout inflation/activity start time: " + inflationDuration + " " + activityStartDuration);

    }
}
