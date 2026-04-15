package org.example.ui;

import org.example.ui.screens.*;
import org.example.ui.state.UiState;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConsoleAppTest {

    @Test
    void shouldDispatchMainScreen() throws IOException {
        UiState uiState = mock(UiState.class);
        when(uiState.getScreen()).thenReturn(UiState.Screen.MAIN);

        MainMenuScreen mainMenu = mock(MainMenuScreen.class);
        UnaryScreen unaryScreen = mock(UnaryScreen.class);
        BinaryScreen binaryScreen = mock(BinaryScreen.class);
        MatrixScreen matrixScreen = mock(MatrixScreen.class);
        ThreadPoolScreen threadPoolScreen = mock(ThreadPoolScreen.class);

        ConsoleApp app = new ConsoleApp(uiState, mainMenu, unaryScreen, binaryScreen, matrixScreen,threadPoolScreen);

        app.dispatchCurrentScreen();

        verify(mainMenu).show(uiState);
        verifyNoInteractions(unaryScreen, binaryScreen, matrixScreen);
    }

    @Test
    void shouldDispatchUnaryScreen() throws IOException {
        UiState uiState = mock(UiState.class);
        when(uiState.getScreen()).thenReturn(UiState.Screen.UNARY);

        MainMenuScreen mainMenu = mock(MainMenuScreen.class);
        UnaryScreen unaryScreen = mock(UnaryScreen.class);
        BinaryScreen binaryScreen = mock(BinaryScreen.class);
        MatrixScreen matrixScreen = mock(MatrixScreen.class);
        ThreadPoolScreen threadPoolScreen = mock(ThreadPoolScreen.class);


        ConsoleApp app = new ConsoleApp(uiState, mainMenu, unaryScreen, binaryScreen, matrixScreen,threadPoolScreen);

        app.dispatchCurrentScreen();

        verify(unaryScreen).show(uiState);
        verifyNoInteractions(mainMenu, binaryScreen, matrixScreen);
    }

    @Test
    void shouldDispatchBinaryScreen() throws IOException {
        UiState uiState = mock(UiState.class);
        when(uiState.getScreen()).thenReturn(UiState.Screen.BINARY);

        MainMenuScreen mainMenu = mock(MainMenuScreen.class);
        UnaryScreen unaryScreen = mock(UnaryScreen.class);
        BinaryScreen binaryScreen = mock(BinaryScreen.class);
        MatrixScreen matrixScreen = mock(MatrixScreen.class);
        ThreadPoolScreen threadPoolScreen = mock(ThreadPoolScreen.class);


        ConsoleApp app = new ConsoleApp(uiState, mainMenu, unaryScreen, binaryScreen, matrixScreen,threadPoolScreen);

        app.dispatchCurrentScreen();

        verify(binaryScreen).show(uiState);
        verifyNoInteractions(mainMenu, unaryScreen, matrixScreen);
    }

    @Test
    void shouldDispatchMatrixScreen() throws IOException {
        UiState uiState = mock(UiState.class);
        when(uiState.getScreen()).thenReturn(UiState.Screen.MATRIX);

        MainMenuScreen mainMenu = mock(MainMenuScreen.class);
        UnaryScreen unaryScreen = mock(UnaryScreen.class);
        BinaryScreen binaryScreen = mock(BinaryScreen.class);
        MatrixScreen matrixScreen = mock(MatrixScreen.class);
        ThreadPoolScreen threadPoolScreen = mock(ThreadPoolScreen.class);


        ConsoleApp app = new ConsoleApp(uiState, mainMenu, unaryScreen, binaryScreen, matrixScreen,threadPoolScreen);

        app.dispatchCurrentScreen();

        verify(matrixScreen).show(uiState);
        verifyNoInteractions(mainMenu, unaryScreen, binaryScreen);
    }

    @Test
    void shouldBuildTerminalAndLoopUntilScreenThrows() throws Exception {
        UiState uiState = mock(UiState.class);
        when(uiState.getScreen()).thenReturn(UiState.Screen.MAIN);

        MainMenuScreen mainMenu = mock(MainMenuScreen.class);
        UnaryScreen unaryScreen = mock(UnaryScreen.class);
        BinaryScreen binaryScreen = mock(BinaryScreen.class);
        MatrixScreen matrixScreen = mock(MatrixScreen.class);
        ThreadPoolScreen threadPoolScreen = mock(ThreadPoolScreen.class);


        doNothing()
                .doThrow(new IOException("stop loop"))
                .when(mainMenu).show(uiState);

        TerminalBuilder builder = mock(TerminalBuilder.class);
        Terminal terminal = mock(Terminal.class);
        when(builder.build()).thenReturn(terminal);

        try (MockedStatic<TerminalBuilder> terminalBuilderMock = mockStatic(TerminalBuilder.class)) {
            terminalBuilderMock.when(TerminalBuilder::builder).thenReturn(builder);

            ConsoleApp app = new ConsoleApp(uiState, mainMenu, unaryScreen, binaryScreen, matrixScreen,threadPoolScreen);

            assertThrows(IOException.class, app::start);

            terminalBuilderMock.verify(TerminalBuilder::builder);
            verify(builder).build();
            verify(uiState, times(2)).getScreen();
            verify(mainMenu, times(2)).show(uiState);
            verifyNoInteractions(unaryScreen, binaryScreen, matrixScreen);
        }
    }


}