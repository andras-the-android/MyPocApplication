package com.example.andras.myapplication.dagger2.di;

import android.app.Activity;

import com.example.andras.myapplication.dagger2.ui.common.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@Module
public class CommonModule {

    private Activity activity;

    public CommonModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator(activity);
    }

}
