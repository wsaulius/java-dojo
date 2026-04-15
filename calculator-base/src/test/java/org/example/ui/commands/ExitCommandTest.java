package org.example.ui.commands;

import org.example.interfaces.ExitHandler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExitCommandTest {

    @Test
    void shouldCallExitHandler() {
        ExitHandler handler = mock(ExitHandler.class);
        ExitCommand command = new ExitCommand(handler);

        command.execute(new String[]{"exit"});

        verify(handler).exit(0);
    }
}