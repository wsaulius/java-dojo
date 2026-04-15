package org.example.ui.commands;

import org.example.interfaces.Command;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class HelpCommandTest {

    @Test
    void shouldReturnAvailableCommands() {
        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", mock(Command.class));
        commands.put("exit", mock(Command.class));

        HelpCommand command = new HelpCommand(commands);

        String result = command.execute(new String[]{"help"});

        assertEquals("Commands: [help, exit]", result);
    }
}