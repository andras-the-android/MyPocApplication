package com.example.andras.myapplication.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
@Component(modules = DaggerPocModule.class)
@Singleton
public interface DaggerFakeComponent {

    @Named("fake")
    Dependency1 getFakeDependecy1();
}
