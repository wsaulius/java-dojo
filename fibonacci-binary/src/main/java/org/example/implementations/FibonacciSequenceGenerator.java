package org.example.implementations;
import java.util.stream.Stream;
import org.example.interfaces.FibonacciOperation;
import org.example.model.FibonacciSequence;
public class FibonacciSequenceGenerator implements FibonacciOperation<FibonacciSequence>{
    public FibonacciSequence apply(int n){
        return new FibonacciSequence(
            Stream.iterate(new long[]{0,1}, a->new long[]{a[1],a[0]+a[1]})
            .limit(n)
            .mapToLong(a->a[0])
            .boxed()
            .toList()
        );
    }
}