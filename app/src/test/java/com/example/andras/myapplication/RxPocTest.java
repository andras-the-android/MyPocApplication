package com.example.andras.myapplication;


import org.junit.Before;
import org.junit.Test;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by Andras_Nemeth on 2016. 12. 07..
 */
public class RxPocTest {

    @Before
    /**
     * On unit test environment we can't reach the RxAndroid MainThred scheduler, so this way we can mock it
     */
    public void setUp() throws Exception {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void test() throws Exception {
        new RxPoc().testBackpressure();
    }

}