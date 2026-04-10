package org.example.services;

import com.google.inject.Singleton;
import jakarta.inject.Inject;
import org.example.enums.*;

import java.math.BigInteger;

/**
 * Executes unary and binary calculator operations through typed selector services.
 */
@Singleton
public class CalculatorService {

    private final UnaryIntSelector unaryIntSelector;
    private final UnaryDoubleSelector unaryDoubleSelector;
    private final UnaryLongSelector unaryLongSelector;
    private final UnaryBooleanSelector unaryBooleanSelector;
    private final BinarySelector binarySelector;
    private final UnaryBigIntegerSelector unaryBigIntegerSelector;

    /**
     * Creates the calculator service with all required typed selectors.
     *
     * @param unaryIntSelector selector for unary integer operations
     * @param unaryDoubleSelector selector for unary double operations
     * @param unaryLongSelector selector for unary long operations
     * @param unaryBooleanSelector selector for unary boolean operations
     * @param binarySelector selector for binary operations
     * @param unaryBigIntegerSelector selector for unary BigInteger operations
     */
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

    /**
     * Executes a unary integer operation.
     *
     * @param type unary integer operation type
     * @param input input value
     * @return computed integer result
     */
    public Integer runUnaryInt(UnaryIntType type, Integer input) {
        return unaryIntSelector.get(type).applyAsInt(input);
    }

    /**
     * Executes a unary double operation.
     *
     * @param type unary double operation type
     * @param input input value
     * @return computed double result
     */
    public Double runUnaryDouble(UnaryDoubleType type, Integer input) {
        return unaryDoubleSelector.get(type).applyAsDouble(input);
    }

    /**
     * Executes a unary long operation.
     *
     * @param type unary long operation type
     * @param input input value
     * @return computed long result
     */
    public Long runUnaryLong(UnaryLongType type, Integer input) {
        return unaryLongSelector.get(type).applyAsLong(input);
    }

    /**
     * Executes a unary boolean operation.
     *
     * @param type unary boolean operation type
     * @param input input value
     * @return computed boolean result
     */
    public Boolean runUnaryBoolean(UnaryBooleanType type, Integer input) {
        return unaryBooleanSelector.get(type).test(input);
    }

    /**
     * Executes a binary operation.
     *
     * @param type binary operation type
     * @param left left operand
     * @param right right operand
     * @return computed double result
     */
    public Double runBinary(BinaryType type, Double left, Double right) {
        return binarySelector.get(type).applyAsDouble(left, right);
    }

    /**
     * Executes a unary BigInteger operation.
     *
     * @param type unary BigInteger operation type
     * @param input input value
     * @return computed BigInteger result
     */
    public BigInteger runUnaryBigInteger(UnaryBigIntegerType type, Integer input) {
        return unaryBigIntegerSelector.get(type).apply(input);
    }
}