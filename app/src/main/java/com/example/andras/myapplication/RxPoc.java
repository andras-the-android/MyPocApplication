package com.example.andras.myapplication;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

class RxPoc {

    void test() {

        /*
        juhhhu hello
        juhhhu bello
        completed
        juhhhu2 hello
        juhhhu2 bello
        completed2
         */
        Observable<String> observable = Observable.just("hello", "bello");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log("completed");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                log("juhhhu " + s);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log("completed2");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                log("juhhhu2 " + s);
            }
        });


    }

    void test2() {
        Observable<String> observable = Observable.fromCallable(() -> "hello bello");
        log("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log("completed");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                log("juhhhu " + s);
            }
        });
    }

    /**
     * Call this from activity! It wont work from junit
     * source: https://github.com/Froussios/Intro-To-RxJava/blob/master/Part%203%20-%20Taming%20the%20sequence/6.%20Hot%20and%20Cold%20observables.md
     */
    void testHot() {
        try {
            //tryTestHot();
            //tryReplay();
            tryCache();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Note instead of .publish().refCount() you can use share() this is the so called multicast
     */
    private void tryTestHot() throws InterruptedException {
        ConnectableObservable<Long> hotObservable = Observable.interval(1000, TimeUnit.MILLISECONDS).publish();
        //doesn't do anything until observable ins't connected
        Action1<Long> logAction1 = value -> log("first-" + value);
        Subscription subscription1 = hotObservable.subscribe(logAction1);
        log("Observable created observer subscribed but still doesn't happen anything");
        Thread.sleep(2000);
        Subscription subscriptionAll = hotObservable.connect();
        //this will get the same values
        Subscription subscription2 = hotObservable.subscribe(value -> log("second-" + value));

        //from now this won't get updates
        Thread.sleep(3000);
        subscription1.unsubscribe();

        //back again. Note that the counter doesn't restart
        Thread.sleep(3000);
        hotObservable.subscribe(logAction1);

        //kill everything
        Thread.sleep(5000);
        subscriptionAll.unsubscribe();

        //back again, but this time it will start from the beginning
        Thread.sleep(5000);
        subscriptionAll = hotObservable.connect();
        hotObservable.subscribe(logAction1);

        //refCount makes a half cold half hot observable. It starts on subscription like cold and finishes on last
        // unsubscription, but the different subscribers get the same data like hot observers.
        //In this case it is interesting that the hot ConnectableObserver is connected when the refCout starts and
        //it doesn't start from the beginning. Furthermore connectable will be disconnected too when the cold observable
        // stops. I think you'd better if you do not use it like this. :)
        Thread.sleep(2000);
        Observable<Long> cold = hotObservable.refCount();
        Subscription subscriptionCold = cold.subscribe(value -> log("cold-" + value));
        Thread.sleep(3000);
        subscriptionCold.unsubscribe();

        Thread.sleep(2000);
        subscriptionAll.unsubscribe();

        log("end of test");
    }

    /**
     * Same as hot plan observable but the second subscriber will also get a first items. You can parameterize buffer in
     * multiple ways. See the link for details.
     */
    private void tryReplay() throws InterruptedException {
        log("replay test");
        ConnectableObservable<Long> replayableObservable = Observable.interval(1000, TimeUnit.MILLISECONDS).replay();
        Subscription subscription = replayableObservable.connect();
        replayableObservable.subscribe(i -> log("First-" + i));
        Thread.sleep(3000);
        replayableObservable.subscribe(i -> log("Second-" + i));
        Thread.sleep(5000);
        subscription.unsubscribe();
        log("replay test end");
    }


    /*
    This is the same as replay but with a simplified syntax
     */
    private void tryCache() throws InterruptedException {
        log("cache test");
        Observable<Long> cachedObservable = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .cache();

        Subscription subscribe1 = cachedObservable.subscribe(i -> log("First-" + i));
        Thread.sleep(3000);
        Subscription subscribe2 = cachedObservable.subscribe(i -> log("Second-" + i));
        Thread.sleep(5000);
        subscribe1.unsubscribe();
        subscribe2.unsubscribe();
        log("cache test end");

    }

     public void testBackpressure() {
         Observable
             .interval(10, TimeUnit.MILLISECONDS)
             .limit(200)
             //this must be before observeOn
             .onBackpressureDrop(aLong -> log("we can't do anything with " + aLong))
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.computation())
             .subscribe(new Subscriber<Long>() {

                 @Override
                 public void onCompleted() {
                     log("completed");
                 }

                 @Override
                 public void onError(Throwable e) {
                     log("Error: " + e.getMessage());
                     e.printStackTrace();
                 }

                 @Override
                 public void onNext(Long integer) {
                     try {
                         Thread.sleep(20);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     log(String.valueOf(integer));
                 }
             });

    }

    public void testErrorHandling() {
        Observable.fromCallable(() ->  Integer.parseInt("not a number"))
                .onErrorResumeNext(Observable.just(5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(integer -> log("corrected value: " + integer), Throwable::printStackTrace);

    }

    private void log(String log) {
        System.out.println("rxpoc " + log);
    }
}
