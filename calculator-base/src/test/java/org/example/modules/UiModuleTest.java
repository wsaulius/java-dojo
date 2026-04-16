package org.example.modules;

import org.example.modules.UiModule;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UiModuleTest {

    private final UiModule module = new UiModule();

    @Test
    void provideTerminal_createsTerminal() throws Exception {
        Terminal terminal = module.provideTerminal();
        assertNotNull(terminal);
    }

    @Test
    void provideLineReader_createsLineReader() throws Exception {
        Terminal terminal = module.provideTerminal();
        LineReader reader = module.provideLineReader(terminal);

        assertNotNull(reader);
    }
}