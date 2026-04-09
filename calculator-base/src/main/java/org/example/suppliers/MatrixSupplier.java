package org.example.suppliers;

import org.example.models.Matrix;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MatrixSupplier implements Supplier<Matrix> {

    private final int size;

    public MatrixSupplier(int size) {
        this.size = size;
    }

    @Override
    public Matrix get() {
        int[][] data = IntStream.range(0, size)
                .mapToObj(i ->
                        IntStream.range(0, size)
                                .map(j -> new RandomNumberSupplier(1, 10).get())
                                .toArray()
                )
                .toArray(int[][]::new);

        return new Matrix(data);
    }
}