package org.example.consumers;

import org.example.models.Matrix;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixPrinterTest {

    private final PrintStream originalOut = System.out;

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    void acceptShouldPrintMatrixRowsAndTrailingBlankLine() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Matrix matrix = new Matrix(new int[][]{
                {1, 2},
                {3, 4}
        });

        MatrixPrinter printer = new MatrixPrinter();
        printer.accept(matrix);

        String expected =
                "1 2 " + System.lineSeparator() +
                        "3 4 " + System.lineSeparator() +
                        System.lineSeparator();

        assertEquals(expected, out.toString());
    }

    @Test
    void acceptShouldPrintSingleElementMatrix() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Matrix matrix = new Matrix(new int[][]{
                {7}
        });

        MatrixPrinter printer = new MatrixPrinter();
        printer.accept(matrix);

        String expected =
                "7 " + System.lineSeparator() +
                        System.lineSeparator();

        assertEquals(expected, out.toString());
    }

    @Test
    void acceptShouldPrintNegativeAndZeroValues() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Matrix matrix = new Matrix(new int[][]{
                {0, -1},
                {-2, 3}
        });

        MatrixPrinter printer = new MatrixPrinter();
        printer.accept(matrix);

        String expected =
                "0 -1 " + System.lineSeparator() +
                        "-2 3 " + System.lineSeparator() +
                        System.lineSeparator();

        assertEquals(expected, out.toString());
    }
}