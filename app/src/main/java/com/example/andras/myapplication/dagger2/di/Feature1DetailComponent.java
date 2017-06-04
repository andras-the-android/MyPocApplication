package com.example.andras.myapplication.dagger2.di;

import android.app.Activity;

import com.example.andras.myapplication.dagger2.ui.feature1.detail.Feature1DetailActivity;
import com.example.andras.myapplication.dagger2.ui.feature1.list.Feature1ListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@Component(modules = InteractorModule.class)
@Singleton
public interface Feature1DetailComponent {

    void inject(Feature1DetailActivity feature1ListActivity);

    final class Get {
        private Get(){}

        static Feature1DetailComponent component;

        public static Feature1DetailComponent component(Activity activity) {
            if (component == null) {
                component = DaggerFeature1DetailComponent.builder()
                        .networkModule(new NetworkModule())
                        .interactorModule(new InteractorModule())
                        .build();
            }
            return component;
        }

    }
}
