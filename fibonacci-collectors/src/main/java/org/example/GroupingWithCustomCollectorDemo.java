package org.example;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.example.collectors.FilteringCollector;
import org.example.implementations.FibonacciSequenceGenerator;
import org.example.model.FibonacciSequence;

public class GroupingWithCustomCollectorDemo {

      private static String groupByTens(long value) {
          if (value == 0) {
              return "0";
          }

          long start = ((value - 1) / 10) * 10 + 1;
          long end = start + 9;
          return start + "-" + end;
      }

      public static void main(String[] args) {

          FibonacciSequence sequence = new FibonacciSequenceGenerator().apply(15);

          Map<String, List<Long>> groupedEvenNumbers = sequence.getValues()
              .stream()
              .collect(Collectors.groupingBy(
                  GroupingWithCustomCollectorDemo::groupByTens,
                  TreeMap::new,
                  new FilteringCollector<>(value -> value % 2 == 0)
              ));

          System.out.println("Original sequence: " + sequence.getValues());
          System.out.println("Grouped even numbers: " + groupedEvenNumbers);

          final Map<Integer, Set<Long>> groupedEven = sequence.getValues()
            .stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.groupingBy(
              value -> (int)((value - 1) / 10),
              TreeMap::new,
              Collectors.toCollection(TreeSet::new)
              ));

           System.out.println("Grouped even collector: " + groupedEven);

      }
  }
