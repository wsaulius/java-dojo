//package org.example.ui.screens;
//
//import org.example.enums.UnaryIntType;
//import org.example.execution.DefaultAsyncCalculationExecutor;
//import org.example.execution.DefaultCalculationExecutor;
//import org.example.models.UnaryCalculationRecord;
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
//import static org.mockito.Mockito.*;
//
//class UnaryScreenTest {
//
//    private LineReader reader;
//    private DefaultCalculationExecutor sync;
//    private DefaultAsyncCalculationExecutor async;
//    private Terminal terminal;
//    private UiState state;
//
//    @BeforeEach
//    void setUp() {
//        reader = mock(LineReader.class);
//        sync = mock(DefaultCalculationExecutor.class);
//        async = mock(DefaultAsyncCalculationExecutor.class);
//        terminal = mock(Terminal.class);
//        state = mock(UiState.class);
//
//        StringWriter output = new StringWriter();
//        PrintWriter writer = new PrintWriter(output, true);
//
//        when(terminal.writer()).thenReturn(writer);
//    }
//
//    @Test
//    void shouldHandleInvalidUnaryTypeAndReturnToMain() {
//        when(reader.readLine("Operation (SQUARE, etc): ")).thenReturn("bad");
//
//        UnaryScreen screen = new UnaryScreen(reader, sync, async, terminal);
//        screen.show(state);
//
//        verify(terminal, atLeastOnce()).flush();
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verifyNoInteractions(sync, async);
//    }
//
//    @Test
//    void shouldHandleInvalidUnaryValueAndReturnToMain() {
//        when(reader.readLine("Operation (SQUARE, etc): ")).thenReturn("square");
//        when(reader.readLine("Value: ")).thenReturn("abc");
//
//        UnaryScreen screen = new UnaryScreen(reader, sync, async, terminal);
//        screen.show(state);
//
//        verify(terminal, atLeastOnce()).flush();
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verifyNoInteractions(sync, async);
//    }
//
//    @Test
//    void shouldExecuteAsyncUnary() {
//        when(state.isAsyncMode()).thenReturn(true);
//        when(reader.readLine("Operation (SQUARE, etc): ")).thenReturn("square");
//        when(reader.readLine("Value: ")).thenReturn("4");
//
//        @SuppressWarnings("unchecked")
//        UnaryCalculationRecord<UnaryIntType, Integer, Integer> record =
//                mock(UnaryCalculationRecord.class);
//
//        when(record.result()).thenReturn(16);
//
//        CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
//                CompletableFuture.completedFuture(record);
//
//        when(async.submitUnaryInt(eq(UnaryIntType.SQUARE), eq(4))).thenReturn(future);
//
//        UnaryScreen screen = new UnaryScreen(reader, sync, async, terminal);
//        screen.show(state);
//
//        verify(async).submitUnaryInt(UnaryIntType.SQUARE, 4);
//        verifyNoInteractions(sync);
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verify(terminal, atLeastOnce()).flush();
//    }
//
//    @Test
//    void shouldExecuteSyncUnary() throws Exception {
//        when(state.isAsyncMode()).thenReturn(false);
//        when(reader.readLine("Operation (SQUARE, etc): ")).thenReturn("square");
//        when(reader.readLine("Value: ")).thenReturn("4");
//
//        @SuppressWarnings("unchecked")
//        UnaryCalculationRecord<UnaryIntType, Integer, Integer> record =
//                mock(UnaryCalculationRecord.class);
//
//        when(record.result()).thenReturn(16);
//
//        @SuppressWarnings("unchecked")
//        Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
//                mock(Future.class);
//
//        when(future.get()).thenReturn(record);
//        when(sync.submitUnaryInt(eq(UnaryIntType.SQUARE), eq(4))).thenReturn(future);
//
//        UnaryScreen screen = new UnaryScreen(reader, sync, async, terminal);
//        screen.show(state);
//
//        verify(sync).submitUnaryInt(UnaryIntType.SQUARE, 4);
//        verifyNoInteractions(async);
//        verify(state).setScreen(UiState.Screen.MAIN);
//        verify(terminal, atLeastOnce()).flush();
//    }
//}