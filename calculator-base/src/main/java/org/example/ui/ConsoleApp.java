package org.example.ui;

import com.google.inject.Inject;
import org.example.execution.*;
import org.example.ui.screens.*;
import org.example.ui.state.UiState;
import java.io.IOException;

public class ConsoleApp {

    private final UiState uiState;

    private final MainMenuScreen mainMenu;
    private final UnaryScreen unaryScreen;
    private final BinaryScreen binaryScreen;
    private final MatrixScreen matrixScreen;
    private final ThreadPoolScreen threadPoolScreen;

    @Inject
    public ConsoleApp(
            UiState uiState,
            MainMenuScreen mainMenu,
            UnaryScreen unaryScreen,
            BinaryScreen binaryScreen,
            MatrixScreen matrixScreen,
            ThreadPoolScreen threadPoolScreen
    ) {
        this.uiState = uiState;
        this.mainMenu = mainMenu;
        this.unaryScreen = unaryScreen;
        this.binaryScreen = binaryScreen;
        this.matrixScreen = matrixScreen;
        this.threadPoolScreen = threadPoolScreen;
    }

    public void start() throws IOException {
        while (uiState.getScreen() != null) {
            dispatchCurrentScreen();
        }
    }
    void dispatchCurrentScreen() throws IOException {
        UiState.Screen screen = uiState.getScreen();

        if (screen == null) {
            throw new IllegalStateException("Screen is null");
        }

        switch (screen) {
            case MAIN:
                mainMenu.show(uiState);
                return;
            case UNARY:
                unaryScreen.show(uiState);
                return;
            case BINARY:
                binaryScreen.show(uiState);
                return;
            case MATRIX:
                matrixScreen.show(uiState);
                return;
            case THREADPOOL:
                threadPoolScreen.show(uiState);
                return;
        }
    }

}