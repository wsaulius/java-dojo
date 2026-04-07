package org.example.consumers;

import org.example.interfaces.CalculationConsumer;
import org.example.models.CalculationRecord;

public class CalculationPrinter implements CalculationConsumer<CalculationRecord> {

    @Override
    public void accept(CalculationRecord calculation) {
        System.out.println(
                calculation.left() + " " +
                        calculation.operation() + " " +
                        calculation.right() + " = " +
                        calculation.result()
        );
    }
}