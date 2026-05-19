package org.example.phase1.collectors;

import org.example.phase1.records.Order;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class TotalSumCollector implements Collector<Order, double[], Double> {

    @Override
    public Supplier<double[]> supplier() {
        return () -> new double[1];
    }

    @Override
    public BiConsumer<double[], Order> accumulator() {
        return (arr, order) -> arr[0] += order.amount();
    }

    @Override
    public BinaryOperator<double[]> combiner() {
        return (a, b) -> {
            a[0] += b[0];
            return a;
        };
    }

    @Override
    public Function<double[], Double> finisher() {
        return arr -> arr[0];
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}