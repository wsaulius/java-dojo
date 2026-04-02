package org.example;
import java.util.List;
import org.example.collectors.FilteringCollector;
import org.example.implementations.FibonacciSequenceGenerator;
import org.example.model.FibonacciSequence;
public class CustomCollectorDemo{
 public static void main(String[] args){
  FibonacciSequence seq=new FibonacciSequenceGenerator().apply(12);
  List<Long> even=seq.getValues().stream().collect(new FilteringCollector<>(n->n%2==0));
  System.out.println("All: "+seq.getValues());
  System.out.println("Even: "+even);
 }
}