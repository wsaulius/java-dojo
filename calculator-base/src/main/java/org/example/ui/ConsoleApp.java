package org.example.ui;

import com.google.inject.Inject;
import org.example.execution.*;
import org.example.services.CalculatorService;
import org.example.ui.screens.*;
import org.example.ui.state.UiState;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

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
        while (true) {

            switch (uiState.getScreen()) {

                case MAIN -> mainMenu.show(uiState);
                case UNARY -> unaryScreen.show(uiState);
                case BINARY -> binaryScreen.show(uiState);
                case MATRIX -> matrixScreen.show(uiState);
                case THREADPOOL -> threadPoolScreen.show(uiState);
            }
        }
    }
}