package org.example.implementations;
import java.util.stream.Stream;
import org.example.interfaces.FibonacciOperation;
public class FibonacciNumberCalculator implements FibonacciOperation<Long>{
    public Long apply(int n){
        return Stream.iterate(new long[]{0,1}, a->new long[]{a[1],a[0]+a[1]})
            .limit(n)
            .map(a->a[0])
            .reduce((a,b)->b)
            .orElse(0L);
    }
}