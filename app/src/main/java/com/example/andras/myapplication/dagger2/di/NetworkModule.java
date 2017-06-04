package com.example.andras.myapplication.dagger2.di;

import com.example.andras.myapplication.dagger2.network.NetworkApi1;
import com.example.andras.myapplication.dagger2.network.NetworkApi2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras Nemeth on 2017. 06. 04..
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    NetworkApi1 provideNetworkApi1() {
        return new NetworkApi1();
    }

    @Provides
    @Singleton
    NetworkApi2 provideNetworkApi2() {
        return new NetworkApi2();
    }
}
