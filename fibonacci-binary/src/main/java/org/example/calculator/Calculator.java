package org.example.calculator;
import java.util.function.BinaryOperator;

public class Calculator<T>{

    private final BinaryOperator<T> op;

    public Calculator(BinaryOperator<T> op){ this.op=op; }

    public T calculate(T a,T b){
      return op.apply(a,b);
    }
}
