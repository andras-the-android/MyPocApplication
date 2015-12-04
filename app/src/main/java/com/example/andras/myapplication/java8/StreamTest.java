package com.example.andras.myapplication.java8;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andras_Nemeth on 2015. 12. 04..
 */
public class StreamTest {

    private static final String TAG = "StreamTest";

    public static void main(String[] args) {
        //[5, 6, 5, 4]
        List<String> sourceList = Arrays.asList("first", "second", "third", "last");
        List<Integer> result1 = Stream.of(sourceList)
                .map(item -> item.length())
                .collect(Collectors.toList());

        //first_second_third_last
        String result2 = Stream.of(sourceList)
                .reduce((acc, item) -> acc + "_" + item).get();

        System.out.println(result2);

    }
}
