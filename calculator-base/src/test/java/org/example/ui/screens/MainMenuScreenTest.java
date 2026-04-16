package org.example.ui.screens;

import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MainMenuScreenTest {

    private LineReader reader;
    private Terminal terminal;
    private StringWriter output;
    private MainMenuScreen screen;

    @BeforeEach
    void setUp() {
        reader = mock(LineReader.class);
        terminal = mock(Terminal.class);
        output = new StringWriter();
        PrintWriter writer = new PrintWriter(output, true);

        when(terminal.writer()).thenReturn(writer);

        screen = new MainMenuScreen(reader, terminal);
    }

    @Test
    void show_shouldSetUnaryScreen_whenChoiceIs1() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("1");

        screen.show(state);

        assertEquals(UiState.Screen.UNARY, state.getScreen());
    }

    @Test
    void show_shouldSetBinaryScreen_whenChoiceIs2() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("2");

        screen.show(state);

        assertEquals(UiState.Screen.BINARY, state.getScreen());
    }

    @Test
    void show_shouldSetMatrixScreen_whenChoiceIs3() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("3");

        screen.show(state);

        assertEquals(UiState.Screen.MATRIX, state.getScreen());
    }

    @Test
    void show_shouldToggleMode_whenChoiceIs4() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("4");

        screen.show(state);

        assertTrue(state.isAsyncMode());
    }

    @Test
    void show_shouldSetThreadPoolScreen_whenChoiceIs5() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("5");

        screen.show(state);

        assertEquals(UiState.Screen.THREADPOOL, state.getScreen());
    }

    @Test
    void show_shouldPrintInvalidChoice_whenChoiceIsUnknown() throws Exception {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("99");

        screen.show(state);

        assertEquals(UiState.Screen.MAIN, state.getScreen());
        verify(terminal, atLeastOnce()).flush();
        String printed = output.toString();
        org.junit.jupiter.api.Assertions.assertTrue(printed.contains("Invalid choice"));
    }

    @Test
    void show_shouldPrintCurrentMode_syncByDefault() throws Exception {
        UiState state = new UiState();
        when(reader.readLine(anyString())).thenReturn("1");

        screen.show(state);

        String printed = output.toString();
        org.junit.jupiter.api.Assertions.assertTrue(printed.contains("Mode: SYNC"));
    }

    @Test
    void show_shouldPrintCurrentMode_asyncWhenEnabled() throws Exception {
        UiState state = new UiState();
        state.toggleMode();
        when(reader.readLine(anyString())).thenReturn("1");

        screen.show(state);

        String printed = output.toString();
        org.junit.jupiter.api.Assertions.assertTrue(printed.contains("Mode: ASYNC"));
    }
}