package org.example.consumers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultPrinterTest {

    private final PrintStream originalOut = System.out;

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    void acceptShouldPrintValue() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ResultPrinter<String> printer = new ResultPrinter<>();
        printer.accept("test");

        assertEquals("test" + System.lineSeparator(), out.toString());
    }

    @Test
    void acceptShouldPrintNullLiteralWhenResultIsNull() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ResultPrinter<String> printer = new ResultPrinter<>();
        printer.accept(null);

        assertEquals("null" + System.lineSeparator(), out.toString());
    }
}