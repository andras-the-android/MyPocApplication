package com.example.andras.myapplication.dagger;

import javax.inject.Singleton;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
public class Dependency1Impl implements Dependency1 {
    @Override
    public String getStart() {
        return "Hello ";
    }
}
