package org.example.ui;

import com.google.inject.Inject;
import org.example.execution.*;
import org.example.services.CalculatorService;
import org.example.ui.screens.*;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ConsoleApp {

    private final UiState uiState;

    private final MainMenuScreen mainMenu;
    private final UnaryScreen unaryScreen;
    private final BinaryScreen binaryScreen;
    private final MatrixScreen matrixScreen;

    @Inject
    public ConsoleApp(
            UiState uiState,
            MainMenuScreen mainMenu,
            UnaryScreen unaryScreen,
            BinaryScreen binaryScreen,
            MatrixScreen matrixScreen
    ) {
        this.uiState = uiState;
        this.mainMenu = mainMenu;
        this.unaryScreen = unaryScreen;
        this.binaryScreen = binaryScreen;
        this.matrixScreen = matrixScreen;
    }

    public void start() throws IOException {
        TerminalBuilder.builder().build();
        while (true) {
            dispatchCurrentScreen();
        }
    }

    void dispatchCurrentScreen() throws IOException {
        switch (uiState.getScreen()) {
            case MAIN -> mainMenu.show(uiState);
            case UNARY -> unaryScreen.show(uiState);
            case BINARY -> binaryScreen.show(uiState);
            case MATRIX -> matrixScreen.show(uiState);
        }
    }

}