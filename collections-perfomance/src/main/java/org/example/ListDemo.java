package org.example;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        int size = 100_000;

        List<Integer> array = new ArrayList<>();
        List<Integer> linked = new LinkedList<>();

        for(int i = 0; i < size; i++) {
            array.add(i);
            linked.add(i);
        }

        //ArrayList
        long startArray = System.nanoTime();

        array.add(size / 2, -1);

        long endArray = System.nanoTime();

        long arrayTime = endArray - startArray;

        //LinkedList
        long startLinked = System.nanoTime();

        linked.add(size / 2, -1);

        long endLinked = System.nanoTime();

        long linkedTime = endLinked - startLinked;

        System.out.println("Array single element insert time: " + arrayTime + " ns");
        System.out.println("Linked single element insert time: " + linkedTime + " ns");

    }
}