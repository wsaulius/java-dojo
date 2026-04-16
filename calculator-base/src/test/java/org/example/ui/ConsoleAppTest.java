package org.example.ui;

import org.example.ui.screens.BinaryScreen;
import org.example.ui.screens.MainMenuScreen;
import org.example.ui.screens.MatrixScreen;
import org.example.ui.screens.ThreadPoolScreen;
import org.example.ui.screens.UnaryScreen;
import org.example.ui.state.UiState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConsoleAppTest {

    private UiState state;
    private MainMenuScreen mainMenu;
    private UnaryScreen unaryScreen;
    private BinaryScreen binaryScreen;
    private MatrixScreen matrixScreen;
    private ThreadPoolScreen threadPoolScreen;
    private ConsoleApp app;

    @BeforeEach
    void setUp() {
        state = mock(UiState.class);
        mainMenu = mock(MainMenuScreen.class);
        unaryScreen = mock(UnaryScreen.class);
        binaryScreen = mock(BinaryScreen.class);
        matrixScreen = mock(MatrixScreen.class);
        threadPoolScreen = mock(ThreadPoolScreen.class);

        app = new ConsoleApp(
                state,
                mainMenu,
                unaryScreen,
                binaryScreen,
                matrixScreen,
                threadPoolScreen
        );
    }

    @Test
    void dispatchCurrentScreen_shouldShowMainMenu_whenScreenIsMain() throws IOException {
        when(state.getScreen()).thenReturn(UiState.Screen.MAIN);

        app.dispatchCurrentScreen();

        verify(mainMenu).show(state);
        verifyNoInteractions(unaryScreen, binaryScreen, matrixScreen, threadPoolScreen);
    }

    @Test
    void dispatchCurrentScreen_shouldShowUnary_whenScreenIsUnary() throws IOException {
        when(state.getScreen()).thenReturn(UiState.Screen.UNARY);

        app.dispatchCurrentScreen();

        verify(unaryScreen).show(state);
        verifyNoInteractions(mainMenu, binaryScreen, matrixScreen, threadPoolScreen);
    }

    @Test
    void dispatchCurrentScreen_shouldShowBinary_whenScreenIsBinary() throws IOException {
        when(state.getScreen()).thenReturn(UiState.Screen.BINARY);

        app.dispatchCurrentScreen();

        verify(binaryScreen).show(state);
        verifyNoInteractions(mainMenu, unaryScreen, matrixScreen, threadPoolScreen);
    }

    @Test
    void dispatchCurrentScreen_shouldShowMatrix_whenScreenIsMatrix() throws IOException {
        when(state.getScreen()).thenReturn(UiState.Screen.MATRIX);

        app.dispatchCurrentScreen();

        verify(matrixScreen).show(state);
        verifyNoInteractions(mainMenu, unaryScreen, binaryScreen, threadPoolScreen);
    }

    @Test
    void dispatchCurrentScreen_shouldShowThreadPool_whenScreenIsThreadPool() throws IOException {
        when(state.getScreen()).thenReturn(UiState.Screen.THREADPOOL);

        app.dispatchCurrentScreen();

        verify(threadPoolScreen).show(state);
        verifyNoInteractions(mainMenu, unaryScreen, binaryScreen, matrixScreen);
    }

    @Test
    void dispatchCurrentScreen_shouldThrow_whenScreenIsNull() {
        when(state.getScreen()).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> app.dispatchCurrentScreen());

        verifyNoInteractions(mainMenu, unaryScreen, binaryScreen, matrixScreen, threadPoolScreen);
    }

    @Test
    void start_shouldDispatchOnceAndExit_whenScreenBecomesNullAfterFirstIteration() throws IOException {
        when(state.getScreen()).thenReturn(
                UiState.Screen.MAIN,
                UiState.Screen.MAIN,
                null
        );

        app.start();

        verify(mainMenu).show(state);
        verifyNoInteractions(unaryScreen, binaryScreen, matrixScreen, threadPoolScreen);
    }

    @Test
    void start_shouldDoNothing_whenInitialScreenIsNull() throws IOException {
        when(state.getScreen()).thenReturn(null);

        app.start();

        verifyNoInteractions(mainMenu, unaryScreen, binaryScreen, matrixScreen, threadPoolScreen);
    }
}