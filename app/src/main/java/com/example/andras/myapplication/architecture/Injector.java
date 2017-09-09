package com.example.andras.myapplication.architecture;

/**
 * Created by Andras_Nemeth on 2017. 08. 06..
 */

public class Injector {

    private static final SomeDataProducerService lifecycleDataProducer = new SomeDataProducerService("lifecycle");
    private static final SomeDataProducerService liveDataProducer = new SomeDataProducerService("livedata");
    private static final ArchitectureLiveData architectureLiveData = new ArchitectureLiveData(liveDataProducer);

    public static void inject(ArchitectureActivity architectureActivity) {
        architectureActivity.liveData = architectureLiveData;
    }

    public static void inject(ArchitectureViewModel architectureViewModel) {
        architectureViewModel.setService(lifecycleDataProducer);
    }
}
