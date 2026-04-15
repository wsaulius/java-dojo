package org.example.ui.screens;

import org.example.execution.DefaultAsyncCalculationExecutor;
import org.example.execution.DefaultCalculationExecutor;
import org.example.models.BinaryCalculationRecord;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.mockito.Mockito.*;

class BinaryScreenTest {

    private LineReader reader;
    private DefaultCalculationExecutor sync;
    private DefaultAsyncCalculationExecutor async;
    private Terminal terminal;
    private UiState state;

    @BeforeEach
    void setUp() {
        reader = mock(LineReader.class);
        sync = mock(DefaultCalculationExecutor.class);
        async = mock(DefaultAsyncCalculationExecutor.class);
        terminal = mock(Terminal.class);
        state = mock(UiState.class);

        StringWriter output = new StringWriter();
        PrintWriter writer = new PrintWriter(output, true);

        when(terminal.writer()).thenReturn(writer);
    }

    @Test
    void shouldHandleInvalidBinaryTypeAndReturnToMain() {
        when(reader.readLine("Operation (ADD, SUBTRACT...): ")).thenReturn("bad");

        BinaryScreen screen = new BinaryScreen(reader, sync, async, terminal);
        screen.show(state);

        verify(terminal).flush();
        verify(state).setScreen(UiState.Screen.MAIN);
        verifyNoInteractions(sync, async);
    }

    @Test
    void shouldHandleInvalidLeftValueAndReturnToMain() {
        when(reader.readLine("Operation (ADD, SUBTRACT...): ")).thenReturn("add");
        when(reader.readLine("Left: ")).thenReturn("abc");

        BinaryScreen screen = new BinaryScreen(reader, sync, async, terminal);
        screen.show(state);

        verify(terminal).flush();
        verify(state).setScreen(UiState.Screen.MAIN);
        verifyNoInteractions(sync, async);
    }

    @Test
    void shouldExecuteAsyncBinaryAndPrintResult() throws Exception {
        when(state.isAsyncMode()).thenReturn(true);

        when(reader.readLine("Operation (ADD, SUBTRACT...): ")).thenReturn("add");
        when(reader.readLine("Left: ")).thenReturn("5");
        when(reader.readLine("Right: ")).thenReturn("3");

        var record = mock(org.example.models.BinaryCalculationRecord.class);
        when(record.result()).thenReturn(8.0);

        var future = CompletableFuture.completedFuture(record);

        when(async.submitBinary(any(), eq(5.0), eq(3.0))).thenReturn(future);

        BinaryScreen screen = new BinaryScreen(reader, sync, async, terminal);
        screen.show(state);

        verify(async).submitBinary(any(), eq(5.0), eq(3.0));
        verifyNoInteractions(sync);

        verify(state).setScreen(UiState.Screen.MAIN);
        verify(terminal, atLeastOnce()).flush();
    }

    @Test
    void shouldExecuteSyncBinaryAndPrintResult() throws Exception {
        when(state.isAsyncMode()).thenReturn(false);

        when(reader.readLine("Operation (ADD, SUBTRACT...): ")).thenReturn("add");
        when(reader.readLine("Left: ")).thenReturn("5");
        when(reader.readLine("Right: ")).thenReturn("3");

        var record = mock(org.example.models.BinaryCalculationRecord.class);
        when(record.result()).thenReturn(8.0);

        Future<BinaryCalculationRecord> future = mock(Future.class);
        when(future.get()).thenReturn(record);

        when(sync.submitBinary(any(), eq(5.0), eq(3.0))).thenReturn(future);

        BinaryScreen screen = new BinaryScreen(reader, sync, async, terminal);
        screen.show(state);

        verify(sync).submitBinary(any(), eq(5.0), eq(3.0));
        verifyNoInteractions(async);

        verify(state).setScreen(UiState.Screen.MAIN);
    }




}