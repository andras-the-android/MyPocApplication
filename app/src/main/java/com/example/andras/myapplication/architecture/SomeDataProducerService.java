package com.example.andras.myapplication.architecture;

import android.os.Handler;
import android.util.Log;

import java.util.function.Consumer;

/**
 * Created by Andras_Nemeth on 2017. 07. 31..
 */

public class SomeDataProducerService {

    private static final String TAG = "SomeDataProducerService";

    private int count;
    private boolean running;
    private Consumer<String> callback;
    private String name;

    public  SomeDataProducerService(String name) {
        this.name = name;
    }

    public void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    public void start() {
        if (!running) {
            running = true;
            run();
        }
    }

    public void stop() {
        running = false;
    }

    private void run() {
        if (running) {
            String data = name + count++;
            callback.accept(data);
            Log.d(TAG, data);
            new Handler().postDelayed(this::run, 1000);
        }

    }

}
