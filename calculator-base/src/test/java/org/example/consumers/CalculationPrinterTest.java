package org.example.consumers;

import org.example.interfaces.PrintableCalculation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationPrinterTest {

    private static class TestCalculation implements PrintableCalculation {
        private final String value;

        TestCalculation(String value) {
            this.value = value;
        }

        @Override
        public String format() {
            return value;
        }
    }

    @Test
    void acceptShouldPrintFormattedCalculation() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        CalculationPrinter<PrintableCalculation> printer = new CalculationPrinter<>();

        printer.accept(new TestCalculation("result"));

        assertEquals("result" + System.lineSeparator(), out.toString());
    }

    @Test
    void acceptShouldUseFormatMethodExactly() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        CalculationPrinter<PrintableCalculation> printer = new CalculationPrinter<>();

        printer.accept(new TestCalculation("formatted-output"));

        assertEquals("formatted-output" + System.lineSeparator(), out.toString());
    }
}