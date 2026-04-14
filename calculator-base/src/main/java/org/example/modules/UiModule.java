package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class UiModule extends AbstractModule {

    @Provides
    Terminal provideTerminal() throws IOException {
        return TerminalBuilder.builder()
                .system(true)
                .build();
    }

    @Provides
    LineReader provideLineReader(Terminal terminal) {
        return LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
    }
}