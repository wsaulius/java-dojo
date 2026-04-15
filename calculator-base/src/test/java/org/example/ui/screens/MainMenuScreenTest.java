//package org.example.ui.screens;
//
//import org.example.interfaces.ExitHandler;
//import org.example.ui.state.UiState;
//import org.jline.reader.LineReader;
//import org.jline.terminal.Terminal;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import static org.mockito.Mockito.*;
//
//class MainMenuScreenTest {
//
//    private LineReader reader;
//    private Terminal terminal;
//    private UiState state;
//    private ExitHandler exitHandler;
//
//    @BeforeEach
//    void setUp() {
//        reader = mock(LineReader.class);
//        terminal = mock(Terminal.class);
//        state = mock(UiState.class);
//        exitHandler = mock(ExitHandler.class);
//
//        StringWriter output = new StringWriter();
//        PrintWriter writer = new PrintWriter(output, true);
//
//        when(terminal.writer()).thenReturn(writer);
//    }
//
//
//    @Test
//    void shouldSetUnaryScreen() throws Exception {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Select: ")).thenReturn("1");
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(state).setScreen(UiState.Screen.UNARY);
//        verify(state, never()).toggleMode();
//        verify(terminal).flush();
//    }
//
//    @Test
//    void shouldSetBinaryScreen() throws Exception {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Select: ")).thenReturn("2");
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(state).setScreen(UiState.Screen.BINARY);
//    }
//
//    @Test
//    void shouldSetMatrixScreen() throws Exception {
//        when(state.isAsyncMode()).thenReturn(false);
//        when(reader.readLine("Select: ")).thenReturn("3");
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(state).setScreen(UiState.Screen.MATRIX);
//    }
//
//    @Test
//    void shouldToggleMode() throws Exception {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Select: ")).thenReturn("4");
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(state).toggleMode();
//        verify(state, never()).setScreen(any());
//    }
//
//    @Test
//    void shouldPrintInvalidChoice() throws Exception {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Select: ")).thenReturn("9");
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(state, never()).toggleMode();
//        verify(state, never()).setScreen(any());
//        verify(terminal).flush();
//    }
//
//}