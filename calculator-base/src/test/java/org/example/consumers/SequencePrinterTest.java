package org.example.consumers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequencePrinterTest {

    private final PrintStream originalOut = System.out;

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    void acceptShouldPrintOnlyMatchingElements() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        SequencePrinter<Integer> printer = new SequencePrinter<>();

        printer.accept(List.of(1, 2, 3, 4), value -> value % 2 == 0);

        String expected =
                "2" + System.lineSeparator() +
                        "4" + System.lineSeparator();

        assertEquals(expected, out.toString());
    }

    @Test
    void acceptShouldPrintNothingWhenNoElementsMatch() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        SequencePrinter<Integer> printer = new SequencePrinter<>();

        printer.accept(List.of(1, 3, 5), value -> value % 2 == 0);

        assertEquals("", out.toString());
    }

    @Test
    void acceptShouldPrintAllElementsWhenAllMatch() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        SequencePrinter<String> printer = new SequencePrinter<>();

        printer.accept(List.of("A", "B", "C"), value -> true);

        String expected =
                "A" + System.lineSeparator() +
                        "B" + System.lineSeparator() +
                        "C" + System.lineSeparator();

        assertEquals(expected, out.toString());
    }
}