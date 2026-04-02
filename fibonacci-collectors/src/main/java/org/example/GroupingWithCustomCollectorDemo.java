package org.example;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
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

           //Collection sum
          Map<Integer, Long> summed =
                  groupedEven.entrySet()
                          .stream()
                          .collect(Collectors.toMap(
                                  Map.Entry::getKey,
                                  e -> e.getValue().stream().mapToLong(Long::longValue).sum()
                          ));
          System.out.println("Summed: " + summed);

          Map<Integer, Long> summedOption2 = new HashMap<>();

          groupedEven.forEach((k, v) ->
                  summedOption2.put(k, v.stream().mapToLong(Long::longValue).sum())
          );

          System.out.println("Summed Option2: " + summedOption2);


          //Total sum
          Long sum = groupedEven.values()
                  .stream()
                  .flatMap(Set::stream)
                  .collect(Collectors.summingLong(Long::longValue));
          System.out.println("Summed: " + sum);

          long sum1 = groupedEven.values()
                  .stream()
                  .flatMapToLong(set -> set.stream().mapToLong(Long::longValue))
                  .sum();
          System.out.println("Summed Option2: " + sum1);
      }
  }
