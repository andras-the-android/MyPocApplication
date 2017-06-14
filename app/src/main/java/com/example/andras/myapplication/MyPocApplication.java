package com.example.andras.myapplication;

import android.app.Application;

/**
 * Created by Andras_Nemeth on 2015.11.03..
 */
public class MyPocApplication extends Application {

    private static MyPocApplication instance;

    public static MyPocApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.init(this);
    }

}
