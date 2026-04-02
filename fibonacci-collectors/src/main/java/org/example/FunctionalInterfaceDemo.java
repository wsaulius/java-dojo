package org.example;

import java.util.concurrent.Callable;
import org.example.implementations.*;

public class FunctionalInterfaceDemo{
 public static void main(String[] args) {

   int n = 100;

   new FibonacciDemoRunner(new FibonacciSequenceGenerator(),
   new FibonacciNumberCalculator()).run( n );

   final FibonacciNumberCalculator calc = new FibonacciNumberCalculator();

   Callable<Long> task = () -> calc.apply( n );

      try {
          Long result = task.call();
          System.out.println("Callable Fibonacci(" + n + ") = " + result);

      } catch (Exception e) {
          e.printStackTrace();
      }

 }

}
