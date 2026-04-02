package org.example;
import org.example.implementations.*;
public class FunctionalInterfaceDemo{
 public static void main(String[] args){ new FibonacciDemoRunner(new FibonacciSequenceGenerator(), new FibonacciNumberCalculator()).run(10); }
}