package com.example.andras.myapplication.dagger2.di;

import com.example.andras.myapplication.dagger2.interactor.Feature1Interactor;
import com.example.andras.myapplication.dagger2.network.NetworkApi1;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@Module(includes = NetworkModule.class)
public class InteractorModule {

    @Singleton
    @Provides
    Feature1Interactor provideFeature1Interactor(NetworkApi1 networkApi1) {
        return new Feature1Interactor(networkApi1);
    }
}
