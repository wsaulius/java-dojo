package org.example;
import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;
public class FibonacciDemoRunner{
 private final FibonacciOperation<FibonacciSequence> s;
 private final FibonacciOperation<Long> n;
 public FibonacciDemoRunner(FibonacciOperation<FibonacciSequence> s,FibonacciOperation<Long> n){ this.s=s; this.n=n; }
 public void run(int x){ System.out.println("Sequence: "+s.apply(x)); System.out.println("Nth: "+n.apply(x)); }
}