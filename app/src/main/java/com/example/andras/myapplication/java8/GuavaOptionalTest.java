package com.example.andras.myapplication.java8;

import android.util.Log;

import com.annimon.stream.Optional;

import java.util.NoSuchElementException;

/**
 * Bunch of examples of usages of the Optional class in Java 8
 *
 * Guava team member here.

 Probably the single biggest disadvantage of null is that it's not obvious what it should mean in any given context: it doesn't have an illustrative name.
 It's not always obvious that null means "no value for this parameter" -- heck, as a return value, sometimes it means "error", or even "success" (!!), or simply "the correct answer is nothing".
 Optional is frequently the concept you actually mean when you make a variable nullable, but not always.
 When it isn't, we recommend that you write your own class, similar to Optional but with a different naming scheme, to make clear what you actually mean.
 But I would say the biggest advantage of Optional isn't in readability: the advantage is its idiot-proof-ness.
 It forces you to actively think about the absent case if you want your program to compile at all, since you have to actively unwrap the Optional and address that case.
 Null makes it disturbingly easy to simply forget things, and though FindBugs helps, I don't think it addresses the issue nearly as well.
 This is especially relevant when you're returning values that may or may not be "present."
 You (and others) are far more likely to forget that other.method(a, b) could return a null value than you're likely to forget that a could be null when you're implementing other.method.
 Returning Optional makes it impossible for callers to forget that case, since they have to unwrap the object themselves.
 For these reasons, we recommend that you use Optional as a return type for your methods, but not necessarily in your method arguments.

 (http://stackoverflow.com/questions/9561295/whats-the-point-of-guavas-optional-class)
 * 
 * @author dgutierrez-diez
 */
public class GuavaOptionalTest {

    private static final String TAG = "GuavaOptionalTest";

    public GuavaOptionalTest() {
        typicalNullPointer();

        emptyOptionalCreation();

        nonEmptyOptional();

        nullableOptional();

        getExample();

        orElseExample();

        orElseThrowExample();

        isPresentExample();

        ifPresentExample();

        filterExample();

        mapExample();

    }

    private static void filterExample()
    {
        /* filter */

        // if the value is not present
        Optional<Car> carOptionalEmpty = Optional.empty();
        carOptionalEmpty.filter( x -> "250".equals( x.getPrice() ) ).ifPresent( x -> Log.d(TAG, x.getPrice()
                + " is ok!") );

        // if the value does not pass the filter
        Optional<Car> carOptionalExpensive = Optional.of( new Car( "3333" ) );
        carOptionalExpensive.filter( x -> "250".equals( x.getPrice() ) ).ifPresent( x -> Log.d(TAG, x
                .getPrice() + " is ok!") );

        // if the value is present and does pass the filter
        Optional<Car> carOptionalOk = Optional.of( new Car( "250" ) );
        carOptionalOk.filter( x -> "250".equals( x.getPrice() ) ).ifPresent( x -> Log.d(TAG, x.getPrice()
                + " is ok!") );

    }

    private static void mapExample()
    {
        /* map */
        // non empty string map to its length
        Optional<String> stringOptional = Optional.of( "loooooooong string" );
        Optional<Integer> sizeOptional = stringOptional.map( String::length ); //map from Optional<String> to Optional<Integer>
        
        Log.d(TAG, "size of string " + sizeOptional.orElse(0));

        // empty string map to its length -> we get 0 as lenght
        Optional<String> stringOptionalNull = Optional.ofNullable( null );
        Optional<Integer> sizeOptionalNull = stringOptionalNull.map( x -> x.length()  ); // we can use Lambdas as we want

        Log.d(TAG, "size of string " + sizeOptionalNull.orElse(0));

    }

    private static void ifPresentExample()
    {
        /* ifPresent */
        Optional<String> stringToUse = Optional.of( "danibuiza2" );
        stringToUse.ifPresent( System.out::println );

        /* if not present */
        Optional<String> stringToUseNull = Optional.ofNullable( null );
        stringToUseNull.ifPresent( System.out::println );
    }

    private static void isPresentExample()
    {
        /* isPresent */
        Optional<String> stringToUse = Optional.of( "danibuiza1" );
        if( stringToUse.isPresent() )
        {
            Log.d(TAG, stringToUse.get());
        }

    }

    private static void orElseThrowExample()
    {
        try
        {
            /* orElseThrow */
            Car carNull = null;
            Optional<Car> optionalCarNull = Optional.ofNullable( carNull );
            optionalCarNull.orElseThrow( IllegalStateException::new );
        }
        catch( IllegalStateException ex )
        {
            Log.d(TAG, "expected IllegalStateException");
        }
    }

    private static void orElseExample()
    {
        /* orElse */
        Car carCreated = new Car( "500" );
        Car defaultCar = new Car( "250" );

        // value is there
        Optional<Car> optionalCar = Optional.of( carCreated );
        String price = optionalCar.orElse( defaultCar ).getPrice();
        Log.d(TAG, "Car price: " + price);

        // else
        Optional<Car> optionalCar2 = Optional.empty();
        price = optionalCar2.orElse( defaultCar ).getPrice();
        Log.d(TAG, "Car price: " + price);
    }

    private static void getExample()
    {
        /* get */
        try
        {
            String strNull2 = null;
            // cannot be passed, we should use nullable
            Optional<String> optionalString = Optional.of( strNull2 );
            Log.d(TAG, "contains something" + optionalString.get().contains("something"));
        }
        catch( NullPointerException ex )
        {
            Log.d(TAG, "expected nullpointer");
        }
    }

    private static void nullableOptional()
    {
        try
        {/* ofNullable */
            String strNull = null;
            Optional<String> nullableOptional = Optional.ofNullable( strNull );
            Log.d(TAG, nullableOptional.get());
        }
        catch( NoSuchElementException ex )
        {
            Log.d(TAG, "expected NoSuchElementException");
        }
    }

    private static void nonEmptyOptional()
    {
        /* non empty */
        String str = "string";
        Optional<String> nonEmptyOptional = Optional.of( str );
        Log.d(TAG, nonEmptyOptional.get());
    }

    private static void emptyOptionalCreation()
    {
        try
        {
            /* empty */
            Optional<String> emptyOptional = Optional.empty();
            Log.d(TAG, emptyOptional.get());
        }
        catch( NoSuchElementException ex )
        {
            Log.d(TAG, "expected NoSuchElementException");
        }
    }

    @SuppressWarnings( "null" )
    private static void typicalNullPointer()
    {
        try
        {
            /*
             * typical NullPointerException, even the compiler is saying something: Null pointer
             * access: The variable strNull can only be null at this location
             */
            String strNull0 = null;
            Log.d(TAG, "contains something" + strNull0.contains("something"));
        }
        catch( NullPointerException ex )
        {
            Log.d(TAG, "expected nullpointer");
        }
    }
}
