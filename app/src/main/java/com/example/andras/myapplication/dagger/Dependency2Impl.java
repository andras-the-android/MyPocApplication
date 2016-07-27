package com.example.andras.myapplication.dagger;

import javax.inject.Inject;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
public class Dependency2Impl implements Dependency2 {

    /**
     * Only for di framework
     */
    @Inject
    public Dependency2Impl() {
    }

    @Override
    public String getEnd() {
        return "dagger world!";
    }
}
