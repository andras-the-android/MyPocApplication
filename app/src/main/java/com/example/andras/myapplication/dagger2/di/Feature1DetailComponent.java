package com.example.andras.myapplication.dagger2.di;

import android.app.Activity;

import com.example.andras.myapplication.dagger2.ui.feature1.detail.Feature1DetailActivity;

import dagger.Component;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@ActivityScope
@Component(dependencies = InteractorComponent.class)
public interface Feature1DetailComponent {

    void inject(Feature1DetailActivity feature1ListActivity);

    final class Get {
        private Get(){}

        static Feature1DetailComponent component;

        public static Feature1DetailComponent component(Activity activity) {
            if (component == null) {
                component = DaggerFeature1DetailComponent.builder()
                        .interactorComponent(InteractorComponent.Get.component())
                        .build();
            }
            return component;
        }

    }
}
