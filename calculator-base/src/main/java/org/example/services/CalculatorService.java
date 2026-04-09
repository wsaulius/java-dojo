package org.example.services;

import com.google.inject.Singleton;
import jakarta.inject.Inject;
import org.example.enums.*;

import java.math.BigInteger;

@Singleton
public class CalculatorService {

    private final UnaryIntSelector unaryIntSelector;
    private final UnaryDoubleSelector unaryDoubleSelector;
    private final UnaryLongSelector unaryLongSelector;
    private final UnaryBooleanSelector unaryBooleanSelector;
    private final BinarySelector binarySelector;
    private final UnaryBigIntegerSelector unaryBigIntegerSelector;

    @Inject
    public CalculatorService(
            UnaryIntSelector unaryIntSelector,
            UnaryDoubleSelector unaryDoubleSelector,
            UnaryLongSelector unaryLongSelector,
            UnaryBooleanSelector unaryBooleanSelector,
            BinarySelector binarySelector,
            UnaryBigIntegerSelector unaryBigIntegerSelector
    ) {
        this.unaryIntSelector = unaryIntSelector;
        this.unaryDoubleSelector = unaryDoubleSelector;
        this.unaryLongSelector = unaryLongSelector;
        this.unaryBooleanSelector = unaryBooleanSelector;
        this.binarySelector = binarySelector;
        this.unaryBigIntegerSelector = unaryBigIntegerSelector;
    }

    public Integer runUnaryInt(UnaryIntType type, Integer input) {
        return unaryIntSelector.get(type).applyAsInt(input);
    }

    public Double runUnaryDouble(UnaryDoubleType type, Integer input) {
        return unaryDoubleSelector.get(type).applyAsDouble(input);
    }

    public Long runUnaryLong(UnaryLongType type, Integer input) {
        return unaryLongSelector.get(type).applyAsLong(input);
    }

    public Boolean runUnaryBoolean(UnaryBooleanType type, Integer input) {
        return unaryBooleanSelector.get(type).test(input);
    }

    public Double runBinary(BinaryType type, Double left, Double right) {
        return binarySelector.get(type).applyAsDouble(left, right);
    }

    public BigInteger runUnaryBigInteger(UnaryBigIntegerType type, Integer input) {
        return unaryBigIntegerSelector.get(type).apply(input);
    }
}