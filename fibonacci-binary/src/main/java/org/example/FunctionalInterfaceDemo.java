package org.example;
import org.example.implementations.*;
public class FunctionalInterfaceDemo{
    public static void main(String[] args){
        FibonacciDemoRunner runner=new FibonacciDemoRunner(
            new FibonacciSequenceGenerator(),
            new FibonacciNumberCalculator()
        );
        runner.run(10);
    }
}