package org.example.consumers;

import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.PrintableCalculation;

public class CalculationPrinter<T extends PrintableCalculation> implements CalculationConsumer<T> {

    @Override
    public void accept(T calculation) {
        System.out.println(calculation.format());
    }
}