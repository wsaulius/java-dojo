package org.example.ui.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnaryCommandTest {

    @Test
    void shouldReturnPlaceholderMessage() {
        UnaryCommand command = new UnaryCommand();

        String result = command.execute(new String[]{"unary"});

        assertEquals("Unary not implemented yet", result);
    }
}