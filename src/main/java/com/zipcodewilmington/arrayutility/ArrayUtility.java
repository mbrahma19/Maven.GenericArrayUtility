package com.zipcodewilmington.arrayutility;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Created by leon on 3/6/18.
 */
public class ArrayUtility<T> {

    private T[] array;

    public ArrayUtility(T[] inputArray) {
        array = inputArray;
    }

    public Integer countDuplicatesInMerge(T[] arrayToMerge, T valueToEvaluate) {
        List<T> result = Arrays.stream(mergeArrays(arrayToMerge)).filter(s -> valueToEvaluate.equals(s)).collect(Collectors.toList());
        return result.size();
    }

    public T getMostCommonFromMerge(T[] arrayToMerge) {

//        ********************************************************************
//        The below method utilizes the Collectors functions and automatically
//        creates distinct keys then stores number of occurences
//        *********************************************************************
//          Map<T,Long> result = Arrays.stream(mergeArrays(arrayToMerge)).
//                collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
//        *********************************************************************

//        This method uses the existing method in the class. IMPORTANT to make sure
//        the stream is made of distinct values else will cause an error for maps.
        Map<T,Integer> result = Arrays.stream(mergeArrays(arrayToMerge)).distinct().
                collect(toMap(Function.identity(), this::getNumberOfOccurrences));

        return result.entrySet().stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey).orElse(null);
    }

    public Integer getNumberOfOccurrences(T valueToEvaluate) {
        List<T> result = Arrays.stream(array).filter(s -> valueToEvaluate.equals(s)).collect(Collectors.toList());
        return result.size();
    }

    public T[] removeValue(T valueToRemove) {
        List<T> list = Arrays.stream(array).filter(s -> !valueToRemove.equals(s)).collect(Collectors.toList());
        return makeArray(list);
    }

    public T[] makeArray(List<T> list){
        return list.toArray((T[])Array.newInstance(array.getClass().getComponentType(), list.size()));
    }

    public T[] mergeArrays(T[] arrayToMerge){
        List<T> result = Stream.concat(Arrays.stream(arrayToMerge),Arrays.stream(array)).collect(Collectors.toList());
        return makeArray(result);
    }

}
