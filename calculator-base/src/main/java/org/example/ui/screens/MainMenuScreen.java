package org.example.ui.screens;

import com.google.inject.Inject;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class MainMenuScreen {

    private final LineReader reader;
    private final Terminal terminal;

    @Inject
    public MainMenuScreen(LineReader reader, Terminal terminal) {
        this.reader = reader;
        this.terminal = terminal;
    }

    public void show(UiState state) throws IOException {
        terminal.writer().println("\n=== Calculator CLI ===");
        terminal.writer().println("Mode: " + (state.isAsyncMode() ? "ASYNC" : "SYNC"));
        terminal.writer().println("1. Unary");
        terminal.writer().println("2. Binary");
        terminal.writer().println("3. Matrix");
        terminal.writer().println("4. Toggle Mode");
        terminal.writer().println("5. Exit");
        terminal.flush();

        String choice = reader.readLine("Select: ");

        switch (choice) {

            case "1" -> state.setScreen(UiState.Screen.UNARY);
            case "2" -> state.setScreen(UiState.Screen.BINARY);
            case "3" -> state.setScreen(UiState.Screen.MATRIX);

            case "4" -> state.toggleMode();

            case "5" -> System.exit(0);

            default -> terminal.writer().println("Invalid choice");
        }
    }
}