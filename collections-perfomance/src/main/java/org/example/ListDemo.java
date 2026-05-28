package org.example;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {

    public static void main(String[] args) {
        int size = 100_000;
        int operations = 10_000;

        List<Integer> array = new ArrayList<>();
        List<Integer> linked = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            array.add(i);
            linked.add(i);
        }

        //ArrayList
        long startArray = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            array.add(size / 2, -1);
        }

        long endArray = System.nanoTime();

        long arrayTime = (endArray - startArray) / 1_000_000;

        //LinkedList
        long startLinked = System.nanoTime();

        for (int i = 0; i < operations; i++) {
            linked.add(size / 2, -1);
        }

        long endLinked = System.nanoTime();

        long linkedTime = (endLinked - startLinked) / 1_000_000;

        System.out.println("Array multiple elements insert time: " + arrayTime + " ms");
        System.out.println("Linked multiple elements insert time: " + linkedTime + " ms");

    }
}