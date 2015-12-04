package com.example.andras.myapplication.java8;

import android.util.Log;

/**
 * Created by Andras on 2015.09.27..
 */
public class RetrolambdaTest {
    
    public static final String TAG = RetrolambdaTest.class.getSimpleName();

    public RetrolambdaTest() {
        doJava8Stuff();
    }

    private void doJava8Stuff() {
        //with type declaration
        MathOperation addition = (int a, int b) -> a + b;

        //with out type declaration
        MathOperation subtraction = (a, b) -> a - b;

        //with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        //without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;

        Log.d(TAG, "10 + 5 = " + operate(10, 5, addition));
        Log.d(TAG, "10 - 5 = " + operate(10, 5, subtraction));
        Log.d(TAG, "10 x 5 = " + operate(10, 5, multiplication));
        Log.d(TAG, "10 / 5 = " + operate(10, 5, division));

        //with parenthesis
        GreetingService greetService1 = message ->
                Log.d(TAG, "Hello " + message);

        //without parenthesis
        GreetingService greetService2 = this::sayMessageByReference;


        greetService1.sayMessage("Mahesh");
        greetService2.sayMessage("Suresh");
    }

    private void sayMessageByReference(String message) {
        Log.d(TAG, "Hello " + message);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}
