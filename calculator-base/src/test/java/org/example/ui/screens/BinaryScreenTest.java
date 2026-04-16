package org.example.ui.screens;

import org.example.enums.BinaryType;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BinaryScreenTest {

    private LineReader reader;
    private DefaultCalculationExecutor sync;
    private DefaultAsyncCalculationExecutor async;
    private Terminal terminal;
    private StringWriter output;
    private PrintWriter writer;
    private BinaryScreen screen;

    @BeforeEach
    void setUp() {
        reader = mock(LineReader.class);
        sync = mock(DefaultCalculationExecutor.class);
        async = mock(DefaultAsyncCalculationExecutor.class);
        terminal = mock(Terminal.class);
        output = new StringWriter();
        writer = new PrintWriter(output, true);

        when(terminal.writer()).thenReturn(writer);

        screen = new BinaryScreen(reader, sync, async, terminal);
    }

    @Test
    void show_shouldRunSyncBinaryCalculation_whenStateIsSync() {
        UiState state = new UiState();

        BinaryCalculationRecord record = new BinaryCalculationRecord(BinaryType.ADD, 5.0, 3.0, 8.0);
        Future<BinaryCalculationRecord> future = CompletableFuture.completedFuture(record);

        when(reader.readLine("Select: ")).thenReturn("1");
        when(reader.readLine("1st number: ")).thenReturn("5");
        when(reader.readLine("2nd number: ")).thenReturn("3");
        when(sync.submitBinary(BinaryType.ADD, 5.0, 3.0)).thenReturn(future);

        screen.show(state);

        verify(sync).submitBinary(BinaryType.ADD, 5.0, 3.0);
        verify(async, never()).submitBinary(any(), anyDouble(), anyDouble());
        assertTrue(output.toString().contains("Result: 8.0"));
        assertTrue(output.toString().contains("Binary took"));
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldRunAsyncBinaryCalculation_whenStateIsAsync() {
        UiState state = new UiState();
        state.toggleMode();

        BinaryCalculationRecord record = new BinaryCalculationRecord(BinaryType.MULTIPLY, 4.0, 2.0, 8.0);
        CompletableFuture<BinaryCalculationRecord> future = CompletableFuture.completedFuture(record);

        when(reader.readLine("Select: ")).thenReturn("3");
        when(reader.readLine("1st number: ")).thenReturn("4");
        when(reader.readLine("2nd number: ")).thenReturn("2");
        when(async.submitBinary(BinaryType.MULTIPLY, 4.0, 2.0)).thenReturn(future);

        screen.show(state);

        verify(async).submitBinary(BinaryType.MULTIPLY, 4.0, 2.0);
        verify(sync, never()).submitBinary(any(), anyDouble(), anyDouble());
        assertTrue(output.toString().contains("Result: 8.0"));
        assertTrue(output.toString().contains("Binary took"));
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldHandleInvalidMenuInput_thenFailParsingAndPrintError() {
        UiState state = new UiState();

        when(reader.readLine("Select: "))
                .thenReturn("99")
                .thenReturn("ignored");
        when(reader.readLine("1st number: ")).thenReturn("x");

        screen.show(state);

        assertTrue(output.toString().contains("Invalid Input"));
        assertTrue(output.toString().contains("Error:"));
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldHandleNumberFormatError_whenFirstNumberIsInvalid() {
        UiState state = new UiState();

        when(reader.readLine("Select: ")).thenReturn("1");
        when(reader.readLine("1st number: ")).thenReturn("bad");

        screen.show(state);

        assertTrue(output.toString().contains("Error:"));
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldHandleExecutorException_inSyncMode() throws Exception {
        UiState state = new UiState();

        when(reader.readLine("Select: ")).thenReturn("4");
        when(reader.readLine("1st number: ")).thenReturn("10");
        when(reader.readLine("2nd number: ")).thenReturn("0");

        @SuppressWarnings("unchecked")
        Future<BinaryCalculationRecord> future = mock(Future.class);
        when(future.get()).thenThrow(new RuntimeException("boom"));
        when(sync.submitBinary(BinaryType.DIVIDE, 10.0, 0.0)).thenReturn(future);

        screen.show(state);

        assertTrue(output.toString().contains("Error: boom"));
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldMapAllBinarySelectionsCorrectly() {
        assertSelectionMapsToType("1", BinaryType.ADD);
        assertSelectionMapsToType("2", BinaryType.SUBTRACT);
        assertSelectionMapsToType("3", BinaryType.MULTIPLY);
        assertSelectionMapsToType("4", BinaryType.DIVIDE);
        assertSelectionMapsToType("5", BinaryType.MODULO);
        assertSelectionMapsToType("6", BinaryType.POWER);
        assertSelectionMapsToType("7", BinaryType.MAX);
        assertSelectionMapsToType("8", BinaryType.MIN);
    }

    private void assertSelectionMapsToType(String selection, BinaryType expectedType) {
        UiState state = new UiState();
        BinaryCalculationRecord record = new BinaryCalculationRecord(expectedType, 1.0, 2.0, 3.0);
        Future<BinaryCalculationRecord> future = CompletableFuture.completedFuture(record);

        reset(reader, sync, async, terminal);
        output.getBuffer().setLength(0);

        when(terminal.writer()).thenReturn(writer);
        when(reader.readLine("Select: ")).thenReturn(selection);
        when(reader.readLine("1st number: ")).thenReturn("1");
        when(reader.readLine("2nd number: ")).thenReturn("2");
        when(sync.submitBinary(expectedType, 1.0, 2.0)).thenReturn(future);

        screen.show(state);

        verify(sync).submitBinary(expectedType, 1.0, 2.0);
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }
}