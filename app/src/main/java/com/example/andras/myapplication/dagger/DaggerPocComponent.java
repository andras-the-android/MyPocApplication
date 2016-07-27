package com.example.andras.myapplication.dagger;

import com.example.andras.myapplication.MainActivity;

import dagger.Component;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
@Component(modules = DaggerPocModule.class)
public interface DaggerPocComponent {

    Dependency1 getDeopendecy1();

    void injectMainActivity(DaggerPoc daggerPoc);
}
