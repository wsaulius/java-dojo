//package org.example.ui.screens;
//
//import org.example.enums.BinaryType;
//import org.example.execution.DefaultAsyncMatrixExecutor;
//import org.example.execution.DefaultMatrixExecutor;
//import org.example.interfaces.ExitHandler;
//import org.example.models.Matrix;
//import org.example.ui.state.UiState;
//import org.jline.reader.LineReader;
//import org.jline.terminal.Terminal;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Future;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//class MatrixScreenTest {
//
//    private LineReader reader;
//    private DefaultMatrixExecutor syncMatrixExecutor;
//    private DefaultAsyncMatrixExecutor asyncMatrixExecutor;
//    private Terminal terminal;
//    private UiState state;
//    private StringWriter output;
//    private ExitHandler exitHandler;
//
//    @BeforeEach
//    void setUp() {
//        reader = mock(LineReader.class);
//        syncMatrixExecutor = mock(DefaultMatrixExecutor.class);
//        asyncMatrixExecutor = mock(DefaultAsyncMatrixExecutor.class);
//        terminal = mock(Terminal.class);
//        state = mock(UiState.class);
//        exitHandler = mock(ExitHandler.class);
//        output = new StringWriter();
//
//        PrintWriter writer = new PrintWriter(output, true);
//
//        when(terminal.writer()).thenReturn(writer);
//    }
//
//    @Test
//    void shouldHandleInvalidOperationAndReturnToMain() {
//        when(reader.readLine("Operation: ")).thenReturn("bad");
//
//        MatrixScreen screen = new MatrixScreen(reader, syncMatrixExecutor, asyncMatrixExecutor, terminal);
//        screen.show(state);
//
//        verify(terminal, atLeastOnce()).flush();
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verifyNoInteractions(syncMatrixExecutor, asyncMatrixExecutor);
//    }
//
//    @Test
//    void shouldHandleInvalidManualMatrixInputAndReturnToMain() {
//        when(reader.readLine("Operation: ")).thenReturn("add");
//        when(reader.readLine("Choose: ")).thenReturn("1");
//        when(reader.readLine("Rows: ")).thenReturn("1");
//        when(reader.readLine("Cols: ")).thenReturn("2");
//        when(reader.readLine("Row 0: ")).thenReturn("1 x");
//
//        MatrixScreen screen = new MatrixScreen(reader, syncMatrixExecutor, asyncMatrixExecutor, terminal);
//        screen.show(state);
//
//        verify(terminal, atLeastOnce()).flush();
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verifyNoInteractions(syncMatrixExecutor, asyncMatrixExecutor);
//    }
//
//    @Test
//    void shouldExecuteSyncMatrixManualInput() throws Exception {
//        when(state.isAsyncMode()).thenReturn(false);
//        when(reader.readLine("Operation: ")).thenReturn("add");
//
//        when(reader.readLine("Choose: ")).thenReturn("1", "1");
//        when(reader.readLine("Rows: ")).thenReturn("1", "1");
//        when(reader.readLine("Cols: ")).thenReturn("2", "2");
//        when(reader.readLine("Row 0: ")).thenReturn("1 2", "3 4");
//
//        Matrix result = new Matrix(new int[][]{{4, 6}});
//
//        @SuppressWarnings("unchecked")
//        Future<Matrix> future = mock(Future.class);
//
//        when(future.get()).thenReturn(result);
//        when(syncMatrixExecutor.execute(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("ADD")))
//                .thenReturn(future);
//
//        MatrixScreen screen = new MatrixScreen(reader, syncMatrixExecutor, asyncMatrixExecutor, terminal);
//        screen.show(state);
//
//        verify(syncMatrixExecutor).execute(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("ADD"));
//        verifyNoInteractions(asyncMatrixExecutor);
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verify(terminal, atLeastOnce()).flush();
//    }
//
//    @Test
//    void shouldExecuteAsyncMatrixManualInput() {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Operation: ")).thenReturn("add");
//
//        when(reader.readLine("Choose: ")).thenReturn("1", "1");
//        when(reader.readLine("Rows: ")).thenReturn("1", "1");
//        when(reader.readLine("Cols: ")).thenReturn("2", "2");
//        when(reader.readLine("Row 0: ")).thenReturn("1 2", "3 4");
//
//        Matrix result = new Matrix(new int[][]{{4, 6}});
//        CompletableFuture<Matrix> future = CompletableFuture.completedFuture(result);
//
//        when(asyncMatrixExecutor.submit(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("ADD")))
//                .thenReturn(future);
//
//        MatrixScreen screen = new MatrixScreen(reader, syncMatrixExecutor, asyncMatrixExecutor, terminal);
//        screen.show(state);
//
//        verify(asyncMatrixExecutor).submit(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("ADD"));
//        verifyNoInteractions(syncMatrixExecutor);
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verify(terminal, atLeastOnce()).flush();
//    }
//
//    @Test
//    void shouldUseRandomMatrixPath() {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Operation: ")).thenReturn("add");
//
//        when(reader.readLine("Choose: ")).thenReturn("2", "2");
//        when(reader.readLine("Size (NxN): ")).thenReturn("1", "1");
//
//        Matrix result = new Matrix(new int[][]{{2}});
//
//        CompletableFuture<Matrix> future = CompletableFuture.completedFuture(result);
//
//        when(asyncMatrixExecutor.submit(any(), any(), eq(BinaryType.ADD), eq("ADD")))
//                .thenReturn(future);
//
//        MatrixScreen screen = new MatrixScreen(reader, syncMatrixExecutor, asyncMatrixExecutor, terminal);
//        screen.show(state);
//
//        verify(asyncMatrixExecutor).submit(any(), any(), eq(BinaryType.ADD), eq("ADD"));
//        verify(state).setScreen(UiState.Screen.MAIN);
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
//
//        assertTrue(output.toString().contains("Invalid choice")); // ← missing
//    }
//
//    @Test
//    void shouldExit() throws Exception {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Select: ")).thenReturn("5"); // must match exactly
//
//        MainMenuScreen screen = new MainMenuScreen(reader, terminal, exitHandler);
//        screen.show(state);
//
//        verify(exitHandler).exit(0);
//    }
//
//}