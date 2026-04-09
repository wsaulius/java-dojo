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

public interface CalculationExecutor {

    Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> submitUnaryInt(
            UnaryIntType type,
            Integer input
    );

    Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>> submitUnaryDouble(
            UnaryDoubleType type,
            Integer input
    );

    Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>> submitUnaryLong(
            UnaryLongType type,
            Integer input
    );

    Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>> submitUnaryBoolean(
            UnaryBooleanType type,
            Integer input
    );

    Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>> submitUnaryBigInteger(
            UnaryBigIntegerType type,
            Integer input
    );

    Future<BinaryCalculationRecord> submitBinary(
            BinaryType type,
            Double left,
            Double right
    );

    List<Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>>> submitUnaryIntBatch(
            UnaryIntType type,
            List<Integer> inputs
    );

    List<Future<UnaryCalculationRecord<UnaryDoubleType, Integer, Double>>> submitUnaryDoubleBatch(
            UnaryDoubleType type,
            List<Integer> inputs
    );

    List<Future<UnaryCalculationRecord<UnaryLongType, Integer, Long>>> submitUnaryLongBatch(
            UnaryLongType type,
            List<Integer> inputs
    );

    List<Future<UnaryCalculationRecord<UnaryBooleanType, Integer, Boolean>>> submitUnaryBooleanBatch(
            UnaryBooleanType type,
            List<Integer> inputs
    );

    List<Future<UnaryCalculationRecord<UnaryBigIntegerType, Integer, BigInteger>>> submitUnaryBigIntegerBatch(
            UnaryBigIntegerType type,
            List<Integer> inputs
    );

    void shutdown();
}