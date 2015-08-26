package com.example.andras.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.example.andras.myapplication.googlesamples.GestureListener;
import com.example.andras.myapplication.googlesamples.logger.Log;
import com.example.andras.myapplication.googlesamples.logger.LogFragment;
import com.example.andras.myapplication.googlesamples.logger.LogWrapper;
import com.example.andras.myapplication.googlesamples.logger.MessageOnlyLogFilter;

public class GesturesActivity extends Activity {

    private View gestureView;
    private VelocityTracker mVelocityTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);
        gestureView = findViewById(R.id.sample_output);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);

        //trackCommonGestures();
        trackVelocity();
        initializeLogging();

    }

    private void trackCommonGestures() {
        // First create the GestureListener that will include all our callbacks.
        // Then create the GestureDetector, which takes that listener as an argument.
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
        final GestureDetector gd = new GestureDetector(this, gestureListener);

        /* For the view where gestures will occur, create an onTouchListener that sends
         * all motion events to the gesture detector.  When the gesture detector
         * actually detects an event, it will use the callbacks you created in the
         * SimpleOnGestureListener to alert your application.
        */
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    private void trackVelocity() {
        gestureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int index = event.getActionIndex();
                int action = event.getActionMasked();
                int pointerId = event.getPointerId(index);

                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        if(mVelocityTracker == null) {
                            // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                            mVelocityTracker = VelocityTracker.obtain();
                        }
                        else {
                            // Reset the velocity tracker back to its initial state.
                            mVelocityTracker.clear();
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker.addMovement(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker.addMovement(event);
                        // When you want to determine the velocity, call
                        // computeCurrentVelocity(). Then call getXVelocity()
                        // and getYVelocity() to retrieve the velocity for each pointer ID.
                        mVelocityTracker.computeCurrentVelocity(1000);
                        // Log velocity of pixels per second
                        // Best practice to use VelocityTrackerCompat where possible.
                        Log.d("", "X velocity: " +
                                VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                        pointerId));
                        Log.d("", "Y velocity: " +
                                VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                        pointerId));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Return a VelocityTracker object back to be re-used by others.
                        mVelocityTracker.recycle();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_gestures, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.common : clearLog(); trackCommonGestures(); break;
            case R.id.track_velocity : clearLog(); trackVelocity(); break;
            case R.id.clear : clearLog(); break;
        }
        return true;
    }

    public void clearLog() {
        LogFragment logFragment =  ((LogFragment)getFragmentManager()
                .findFragmentById(R.id.log_fragment));
        logFragment.getLogView().setText("");
    }

    /** Create a chain of targets that will receive log data */
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());
//        logFragment.getLogView().setTextAppearance(this, R.style.Log);
        logFragment.getLogView().setBackgroundColor(Color.WHITE);


        Log.i(getClass().getSimpleName(), "Ready");
    }
}
