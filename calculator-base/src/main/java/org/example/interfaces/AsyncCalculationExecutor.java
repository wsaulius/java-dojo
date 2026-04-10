package org.example.interfaces;

import org.example.enums.*;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Defines asynchronous calculation submission methods based on {@link CompletableFuture}.
 * Supports unary calculations for multiple result types, binary calculations, batch submission,
 * and executor shutdown.
 */
public interface AsyncCalculationExecutor {

    /**
     * Submits an asynchronous unary integer calculation.
     *
     * @param type unary integer operation type
     * @param input input value
     * @return future containing the completed unary calculation record
     */
    CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
            UnaryIntType type,
            Integer input
    );

    /**
     * Submits an asynchronous unary double calculation.
     *
     * @param type unary double operation type
     * @param input input value
     * @return future containing the completed unary calculation record
     */
    CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
            UnaryDoubleType type,
            Integer input
    );

    /**
     * Submits an asynchronous unary long calculation.
     *
     * @param type unary long operation type
     * @param input input value
     * @return future containing the completed unary calculation record
     */
    CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
            UnaryLongType type,
            Integer input
    );

    /**
     * Submits an asynchronous unary boolean calculation.
     *
     * @param type unary boolean operation type
     * @param input input value
     * @return future containing the completed unary calculation record
     */
    CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
            UnaryBooleanType type,
            Integer input
    );

    /**
     * Submits an asynchronous unary BigInteger calculation.
     *
     * @param type unary BigInteger operation type
     * @param input input value
     * @return future containing the completed unary calculation record
     */
    CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
            UnaryBigIntegerType type,
            Integer input
    );

    /**
     * Submits an asynchronous binary calculation.
     *
     * @param type binary operation type
     * @param left left input value
     * @param right right input value
     * @return future containing the completed binary calculation record
     */
    CompletableFuture<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    );

    /**
     * Submits a batch of asynchronous unary integer calculations.
     *
     * @param type unary integer operation type
     * @param inputs input values
     * @return list of futures containing completed unary calculation records
     */
    List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    );

    /**
     * Submits a batch of asynchronous unary double calculations.
     *
     * @param type unary double operation type
     * @param inputs input values
     * @return list of futures containing completed unary calculation records
     */
    List<CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    );

    /**
     * Submits a batch of asynchronous unary long calculations.
     *
     * @param type unary long operation type
     * @param inputs input values
     * @return list of futures containing completed unary calculation records
     */
    List<CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    );

    /**
     * Submits a batch of asynchronous unary boolean calculations.
     *
     * @param type unary boolean operation type
     * @param inputs input values
     * @return list of futures containing completed unary calculation records
     */
    List<CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    );

    /**
     * Submits a batch of asynchronous unary BigInteger calculations.
     *
     * @param type unary BigInteger operation type
     * @param inputs input values
     * @return list of futures containing completed unary calculation records
     */
    List<CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    );

    /**
     * Shuts down the underlying asynchronous execution resources.
     */
    void shutdown();
}