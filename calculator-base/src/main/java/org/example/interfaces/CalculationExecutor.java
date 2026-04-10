package org.example.interfaces;

import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Future;
import org.example.enums.BinaryType;
import org.example.enums.UnaryBigIntegerType;
import org.example.enums.UnaryBooleanType;
import org.example.enums.UnaryDoubleType;
import org.example.enums.UnaryIntType;
import org.example.enums.UnaryLongType;

/**
 * Defines asynchronous calculation submission methods based on {@link Future}.
 * Supports unary calculations for multiple result types, binary calculations, batch submission,
 * and executor shutdown.
 */
public interface CalculationExecutor {

    /**
     * Submits a unary integer calculation for execution.
     *
     * @param type unary integer operation type
     * @param input input value
     * @return future producing the completed unary integer calculation record
     */
    Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
            UnaryIntType type,
            Integer input
    );

    /**
     * Submits a unary double calculation for execution.
     *
     * @param type unary double operation type
     * @param input input value
     * @return future producing the completed unary double calculation record
     */
    Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
            UnaryDoubleType type,
            Integer input
    );

    /**
     * Submits a unary long calculation for execution.
     *
     * @param type unary long operation type
     * @param input input value
     * @return future producing the completed unary long calculation record
     */
    Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
            UnaryLongType type,
            Integer input
    );

    /**
     * Submits a unary boolean calculation for execution.
     *
     * @param type unary boolean operation type
     * @param input input value
     * @return future producing the completed unary boolean calculation record
     */
    Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
            UnaryBooleanType type,
            Integer input
    );

    /**
     * Submits a unary BigInteger calculation for execution.
     *
     * @param type unary BigInteger operation type
     * @param input input value
     * @return future producing the completed unary BigInteger calculation record
     */
    Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
            UnaryBigIntegerType type,
            Integer input
    );

    /**
     * Submits a binary calculation for execution.
     *
     * @param type binary operation type
     * @param left left input value
     * @param right right input value
     * @return future producing the completed binary calculation record
     */
    Future<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    );

    /**
     * Submits multiple unary integer calculations for execution.
     *
     * @param type unary integer operation type
     * @param inputs input values
     * @return futures producing completed unary integer calculation records
     */
    List<Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    );

    /**
     * Submits multiple unary double calculations for execution.
     *
     * @param type unary double operation type
     * @param inputs input values
     * @return futures producing completed unary double calculation records
     */
    List<Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    );

    /**
     * Submits multiple unary long calculations for execution.
     *
     * @param type unary long operation type
     * @param inputs input values
     * @return futures producing completed unary long calculation records
     */
    List<Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    );

    /**
     * Submits multiple unary boolean calculations for execution.
     *
     * @param type unary boolean operation type
     * @param inputs input values
     * @return futures producing completed unary boolean calculation records
     */
    List<Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    );

    /**
     * Submits multiple unary BigInteger calculations for execution.
     *
     * @param type unary BigInteger operation type
     * @param inputs input values
     * @return futures producing completed unary BigInteger calculation records
     */
    List<Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    );

    /**
     * Shuts down the underlying execution resources.
     */
    void shutdown();
}