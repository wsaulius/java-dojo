package org.example;
import java.math.BigDecimal;
import org.example.calculator.Calculator;
public class BinaryOperationDemo{
    public static void main(String[] args){
        Calculator<Integer> add=new Calculator<>(Integer::sum);
        Calculator<Integer> mult=new Calculator<>((a,b)->a*b);
        System.out.println("2+3="+add.calculate(2,3));
        System.out.println("2*3="+mult.calculate(2,3));
        Calculator<BigDecimal> money=new Calculator<>(BigDecimal::add);
        System.out.println("1.2+3.4="+money.calculate(new BigDecimal("1.2"),new BigDecimal("3.4")));
    }
}