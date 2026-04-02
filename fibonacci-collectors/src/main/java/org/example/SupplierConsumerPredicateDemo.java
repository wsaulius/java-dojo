package org.example;
import java.util.*;
import java.util.function.*;
import org.example.implementations.*;
import org.example.interfaces.SequenceConsumer;
import org.example.model.FibonacciSequence;
public class SupplierConsumerPredicateDemo{
 public static void main(String[] args){
  int n=12;
  FibonacciNumberCalculator nc=new FibonacciNumberCalculator();
  FibonacciSequenceGenerator sg=new FibonacciSequenceGenerator();
  Supplier<Long> sup=()->nc.apply(n);
  Predicate<Long> isOdd=v->v%2!=0;
  SequenceConsumer cons=(vals,p)->{
   if(vals.isEmpty()){ System.out.println("Empty"); return; }
   String tag=p.test(vals.get(0))?"Odd":"Even";
   System.out.println("["+tag+"] "+vals);
  };
  System.out.println("Nth: "+sup.get());
  FibonacciSequence seq=sg.apply(n);
  cons.accept(seq.getValues(),isOdd);
 }
}