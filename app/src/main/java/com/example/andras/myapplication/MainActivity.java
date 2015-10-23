package com.example.andras.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.andras.myapplication.material.MaterialTestActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrolambdaTest();
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

    public void onMapsButtonClick(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void onLocationButtonClick(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    public void onDesignDemoButtonClick(View view) {
        startActivity(new Intent(this, MaterialTestActivity.class));
    }

    public void onGesturesButtonClick(View view) {
        startActivity(new Intent(this, GesturesActivity.class));
    }
}
