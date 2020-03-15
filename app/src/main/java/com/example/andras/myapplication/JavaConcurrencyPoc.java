package com.example.andras.myapplication;

import androidx.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * source: http://www.vogella.com/tutorials/JavaConcurrency/article.html
 */
public class JavaConcurrencyPoc {

    private static final int NUMBER_OF_THREADS = 10;

    public static void main(String[] args) {
        new JavaConcurrencyPoc().doStuff();
    }

    private void doStuff() {
        try {
//        doWithThread();
//            doWithExecutorService();
            doWithExecutorServiceCallable();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void doWithThread() {
        Runnable task = new MyRunnable("thread1");
        Thread worker = new Thread(task);
        worker.start();
        System.out.println("started");
    }

    private void doWithExecutorService() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        executor.execute(new MyRunnable("thread1"));
        executor.execute(new MyRunnable("thread2"));
        System.out.println("started");

        //...other operations

        executor.shutdown();
        boolean stoppedWithoutTimeot = executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(stoppedWithoutTimeot ? "Finished all threads normally" :  "Finished threads with timeout");
    }

    private void doWithExecutorServiceCallable() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        Future<Long> future = executor.submit(new MyCallable("thread1"));
        System.out.println("started");

        //this is not effective at all. Use CallableFuture if it is possible
        Long sum = future.get();
        while (sum == null) {
            sleep(1000);
            sum = future.get();
        }
        System.out.printf("Yaaay, sum is %d%n", sum);
        executor.shutdown();
    }

    private static class MyRunnable implements Runnable {

        private String id;

        MyRunnable(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            doBackGroundOperation(id);
        }
    }

    private static class MyCallable implements Callable<Long> {

        private String id;

        MyCallable(String id) {
            this.id = id;
        }

        @Override
        public Long call() throws Exception {
            return doBackGroundOperation(id);
        }

    }

    private static Long doBackGroundOperation(String id) {
        long sum = 0;
        for (long i = 1; i < 20; i++) {
            sleep(500);
            sum += i;
            System.out.printf("%s still here%n", id);
        }
        return sum;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
