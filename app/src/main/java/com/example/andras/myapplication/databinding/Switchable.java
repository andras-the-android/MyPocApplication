package com.example.andras.myapplication.databinding;

import android.view.View;

/**
 * Created by Andras_Nemeth on 2016. 06. 22..
 */
public class Switchable {

    private boolean turnedOn = false;

    /**
     * @param model We dont need this model here but we can get it, so why not? :)
     */
    public void turnOn(DummyModel model) {
        turnedOn = true;
    }

    public void turnOff(DummyModel model) {
        turnedOn = false;
    }

    public boolean isOn() {
        return turnedOn;
    }
    
    public String getLabel() {
        return turnedOn ? "Turn Off" : "Turn On";
    }
}
