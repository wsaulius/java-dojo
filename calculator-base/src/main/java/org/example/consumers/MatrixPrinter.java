package org.example.consumers;

import jakarta.inject.Singleton;
import org.example.models.Matrix;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Prints matrix content.
 */
@Singleton
public final class MatrixPrinter implements Consumer<Matrix> {

    /** {@inheritDoc} */
    @Override
    public void accept(Matrix matrix) {

        Arrays.stream(matrix.data())
                .forEach(row -> {
                    Arrays.stream(row)
                            .forEach(v -> System.out.print(v + " "));
                    System.out.println();
                });

        System.out.println();
    }
}