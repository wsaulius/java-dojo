package org.example.consumers;

import jakarta.inject.Singleton;
import org.example.interfaces.CalculationConsumer;
import org.example.interfaces.PrintableCalculation;

/**
 * Prints calculation results.
 */
@Singleton
public final class CalculationPrinter<T extends PrintableCalculation> implements CalculationConsumer<T> {

    /** {@inheritDoc} */
    @Override
    public void accept(T calculation) {
        System.out.println(calculation.format());
    }
}