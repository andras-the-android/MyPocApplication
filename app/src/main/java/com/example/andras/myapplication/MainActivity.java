package com.example.andras.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDragNDropButtonClick(View view) {
        startActivity(new Intent(this, DragDropActivity.class));
    }

    public void onNotificationButtonClick(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void onActionBarButtonClick(View view) {
        startActivity(new Intent(this, ActionBarActivity.class));
    }

    public void onPropertyAnimButtonClick(View view) {
        startActivity(new Intent(this, PropertyAnimationActivity.class));
    }
}
