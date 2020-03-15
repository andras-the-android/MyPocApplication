package com.example.andras.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import androidx.appcompat.app.AppCompatActivity;
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

public class GesturesActivity extends AppCompatActivity {

    private View gestureView;
    private VelocityTracker mVelocityTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);
        gestureView = findViewById(R.id.sample_output);
        gestureView.setClickable(true);
        gestureView.setFocusable(true);

        trackCommonGestures();
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
                        // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                        mVelocityTracker = VelocityTracker.obtain();

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
                        mVelocityTracker = null;
                        break;
                }
                return true;
            }
        });
    }

    private void trackMultitouchEvents() {
        gestureView.setOnTouchListener(new View.OnTouchListener() {


            public int mActivePointerId;
            public static final String DEBUG_TAG = "";
            private int counter;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Get the pointer ID
                mActivePointerId = event.getPointerId(0);


                int action = MotionEventCompat.getActionMasked(event);
                // Get the index of the pointer associated with the action.
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //!!!!ACTION_MOVE esetben mindig 0 lesz a visszatérési érték, szóval ilyenkor
                // végig kell iterálni az összes pointeren
                int index = MotionEventCompat.getActionIndex(event);
                int xPos = -1;
                int yPos = -1;


                String eventType = event.getPointerCount() > 1 ? "multitouch " : "single touch ";
                // The coordinates of the current screen contact, relative to
                // the responding View or Activity.
                xPos = (int)MotionEventCompat.getX(event, index);
                yPos = (int)MotionEventCompat.getY(event, index);
                Log.d(DEBUG_TAG, counter++ + ": The action is " + eventType + actionToString(action)+ " (x:" + xPos +", y:" + yPos + ") index:" + index);

                // ... Many touch events later...

                // Use the pointer ID to find the index of the active pointer
                // and fetch its position
                int pointerIndex = event.findPointerIndex(mActivePointerId);
                // Get the pointer's current position
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);

                return true;
            }

            // Given an action int, returns a string description
            private String actionToString(int action) {
                switch (action) {

                    case MotionEvent.ACTION_DOWN: return "Down";
                    case MotionEvent.ACTION_MOVE: return "Move";
                    case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
                    case MotionEvent.ACTION_UP: return "Up";
                    case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
                    case MotionEvent.ACTION_OUTSIDE: return "Outside";
                    case MotionEvent.ACTION_CANCEL: return "Cancel";
                }
                return "";
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
            case R.id.track_multitouch : clearLog(); trackMultitouchEvents(); break;
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
