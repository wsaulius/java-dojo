package org.example;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.example.implementations.CustomPrintingPredicate;
import org.example.implementations.FibonacciNumberCalculator;
import org.example.implementations.FibonacciSequenceGenerator;

import org.example.collectors.FilteringCollector;
import org.example.interfaces.SequenceConsumer;
import org.example.model.FibonacciSequence;

public class SupplierConsumerPredicateDemo {

    public static void main(String[] args) {
        int n = 12;

        FibonacciNumberCalculator numberCalculator = new FibonacciNumberCalculator();
        FibonacciSequenceGenerator sequenceGenerator = new FibonacciSequenceGenerator();

        final Supplier<Long> nthSupplier = () -> numberCalculator.apply(n);

        final Predicate<Long> isOdd = value -> value % 2 != 0;

        final Predicate<Long> isEven =  new CustomPrintingPredicate("Check if number is even",
        isOdd.negate() );

        final Consumer<Long> printNth =
            value -> System.out.println("Supplied nth Fibonacci number: " + value);

        Consumer<List<Long>> printSequence =
            values -> System.out.println("Fibonacci numbers: " + values);

        final SequenceConsumer customizedConsumer = (values, predicate) -> {

          if (values == null || values.isEmpty()) {
              System.out.println("[Empty] " + values);
              return;
          }

          Long first = values.get(0);
          System.out.println("[" + predicate.toString() + "] Fibonacci sequence: " + values);
        };

        Long nthValue = nthSupplier.get();
        printNth.accept(nthValue);

        FibonacciSequence sequence = sequenceGenerator.apply(n);
        List<Long> allValues = sequence.getValues()
            .stream()
            .toList();

        printSequence.accept(allValues);

        List<Long> evenValues = sequence.getValues()
            .stream()
            .filter( isEven )
            .toList();

        customizedConsumer.accept(evenValues, isEven);

        List<Long> oddValues = sequence.getValues()
            .stream()
            .collect(new FilteringCollector<>(isOdd));

        customizedConsumer.accept(oddValues, isOdd);

        final Map<Integer, List<Long>> grouped = allValues
           .stream()
           .collect(Collectors.groupingBy(
               value -> (int)((value - 1) / 10)
           ));

        System.out.println("Grouped: " + grouped);
    }
}
