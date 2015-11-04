package com.example.andras.myapplication;

import android.app.Application;

/**
 * Created by Andras_Nemeth on 2015.11.03..
 */
public class MyPocApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.init(this);
    }

}
