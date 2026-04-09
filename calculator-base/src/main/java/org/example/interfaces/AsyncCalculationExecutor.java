package org.example.interfaces;

import org.example.enums.*;
import org.example.models.BinaryCalculationRecord;
import org.example.models.UnaryCalculationRecord;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncCalculationExecutor {

    CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
            UnaryIntType type,
            Integer input
    );

    CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
            UnaryDoubleType type,
            Integer input
    );

    CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
            UnaryLongType type,
            Integer input
    );

    CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
            UnaryBooleanType type,
            Integer input
    );

    CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
            UnaryBigIntegerType type,
            Integer input
    );

    CompletableFuture<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    );

    List<CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    );

    List<CompletableFuture<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    );

    List<CompletableFuture<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    );

    List<CompletableFuture<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    );

    List<CompletableFuture<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    );

    void shutdown();
}