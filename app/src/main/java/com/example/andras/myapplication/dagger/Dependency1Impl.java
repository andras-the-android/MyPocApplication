package com.example.andras.myapplication.dagger;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
public class Dependency1Impl implements Dependency1 {
    @Override
    public String getStart() {
        return "Hello ";
    }
}
