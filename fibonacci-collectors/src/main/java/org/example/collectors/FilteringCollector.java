package org.example.collectors;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
public class FilteringCollector<T> implements Collector<T,List<T>,List<T>>{
 private final Predicate<T> predicate;
 public FilteringCollector(Predicate<T> predicate){ this.predicate=predicate; }
 public Supplier<List<T>> supplier(){ return ArrayList::new; }
 public BiConsumer<List<T>,T> accumulator(){ return (l,i)->{ if(predicate.test(i)) l.add(i); }; }
 public BinaryOperator<List<T>> combiner(){ return (l,r)->{ l.addAll(r); return l; }; }
 public Function<List<T>,List<T>> finisher(){ return Function.identity(); }
 public Set<Characteristics> characteristics(){ return Set.of(Characteristics.IDENTITY_FINISH); }
}