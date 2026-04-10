package org.example.suppliers;

import org.example.models.Matrix;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Supplies square matrices filled with random integer values.
 */
public class MatrixSupplier implements Supplier<Matrix> {

    private final int size;

    /**
     * Creates a matrix supplier for square matrices of the given size.
     *
     * @param size number of rows and columns in each generated matrix
     */
    public MatrixSupplier(int size) {
        this.size = size;
    }

    /**
     * Returns a newly generated square matrix filled with random values.
     *
     * @return generated matrix
     */
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