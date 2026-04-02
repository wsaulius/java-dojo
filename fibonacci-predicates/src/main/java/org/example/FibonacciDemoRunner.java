package org.example;
import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;
public class FibonacciDemoRunner {
    private final FibonacciOperation<FibonacciSequence> seqOp;
    private final FibonacciOperation<Long> numOp;
    public FibonacciDemoRunner(FibonacciOperation<FibonacciSequence> s, FibonacciOperation<Long> n){
        this.seqOp=s; this.numOp=n;
    }
    public void run(int n){
        System.out.println("Sequence: "+seqOp.apply(n));
        System.out.println("Nth: "+numOp.apply(n));
    }
}