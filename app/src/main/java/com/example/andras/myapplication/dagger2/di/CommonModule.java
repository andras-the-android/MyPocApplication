package com.example.andras.myapplication.dagger2.di;

import android.app.Activity;
import android.content.Context;

import com.example.andras.myapplication.MyPocApplication;
import com.example.andras.myapplication.dagger2.ui.common.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@Module
public class CommonModule {

    private Context context;

    public CommonModule() {
        this.context = MyPocApplication.getInstance();
    }

    @Provides
    @ActivityScope
    Navigator provideNavigator() {
        return new Navigator(context);
    }

}
