package org.example.model;
import java.util.List;

public class FibonacciSequence {

    private final List<Long> values;

    public FibonacciSequence(List<Long> values){
      this.values = values;
    }

    public List<Long> getValues(){ return values; }

    public String toString(){ return values.toString(); }
    
}
