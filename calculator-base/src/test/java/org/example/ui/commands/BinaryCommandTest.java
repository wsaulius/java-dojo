package org.example.ui.commands;

import org.example.enums.BinaryType;
import org.example.services.CalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BinaryCommandTest {

    @Test
    void shouldReturnUsageWhenArgumentsAreMissing() {
        CalculatorService calculator = mock(CalculatorService.class);
        BinaryCommand command = new BinaryCommand(calculator);

        String result = command.execute(new String[]{"binary", "add", "5"});

        verifyNoInteractions(calculator);
        assertEquals("Usage: binary <type> <a> <b>", result);
    }

    @Test
    void shouldExecuteBinaryCalculation() {
        CalculatorService calculator = mock(CalculatorService.class);
        when(calculator.runBinary(BinaryType.ADD, 5.0, 3.0)).thenReturn(8.0);

        BinaryCommand command = new BinaryCommand(calculator);

        String result = command.execute(new String[]{"binary", "add", "5", "3"});

        verify(calculator).runBinary(BinaryType.ADD, 5.0, 3.0);
        assertEquals("8.0", result);
    }

    @Test
    void shouldThrowForInvalidBinaryType() {
        CalculatorService calculator = mock(CalculatorService.class);
        BinaryCommand command = new BinaryCommand(calculator);

        assertThrows(IllegalArgumentException.class,
                () -> command.execute(new String[]{"binary", "bad", "5", "3"}));
    }

    @Test
    void shouldThrowForInvalidNumber() {
        CalculatorService calculator = mock(CalculatorService.class);
        BinaryCommand command = new BinaryCommand(calculator);

        assertThrows(NumberFormatException.class,
                () -> command.execute(new String[]{"binary", "add", "x", "3"}));
    }
}