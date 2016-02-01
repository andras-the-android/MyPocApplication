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
        List<String> sourceList = Arrays.asList("first", "second", "third", "last");

        //[5, 6, 5, 4]
        List<Integer> result1 = Stream.of(sourceList)
                .map(item -> item.length())
                .collect(Collectors.toList());

        //first_second_third_last
        String result2 = Stream.of(sourceList)
                .reduce((acc, item) -> acc + "_" + item).get();

        //[1, 2, 3, 4, 5]
        List<Integer> result3 = Stream.of(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5)
        )
                .flatMap(sublist -> Stream.of(sublist))
                .collect(Collectors.toList());

        System.out.println(result3);

    }
}
