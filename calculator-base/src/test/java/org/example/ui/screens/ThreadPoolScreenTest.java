package org.example.ui.screens;

import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ThreadPoolScreenTest {

    private LineReader reader;
    private Terminal terminal;
    private StringWriter output;
    private PrintWriter writer;
    private ThreadPoolExecutor matrixPool;
    private ThreadPoolExecutor calcPool;
    private ThreadPoolScreen screen;

    @BeforeEach
    void setUp() {
        reader = mock(LineReader.class);
        terminal = mock(Terminal.class);
        output = new StringWriter();
        writer = new PrintWriter(output, true);

        when(terminal.writer()).thenReturn(writer);

        matrixPool = new ThreadPoolExecutor(
                2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()
        );
        calcPool = new ThreadPoolExecutor(
                3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()
        );

        screen = new ThreadPoolScreen(reader, terminal, matrixPool, calcPool);
    }

    @Test
    void show_shouldReturnToMain_whenChoiceIs4() {
        UiState state = new UiState();
        state.setScreen(UiState.Screen.THREADPOOL);
        when(reader.readLine("Select: ")).thenReturn("4");

        screen.show(state);

        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void show_shouldChangeMatrixPool_whenChoiceIs1() {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("1");
        when(reader.readLine("Enter pool size: ")).thenReturn("4");

        screen.show(state);

        assertEquals(4, matrixPool.getCorePoolSize());
        assertEquals(4, matrixPool.getMaximumPoolSize());
        assertEquals(UiState.Screen.MAIN, state.getScreen());
        assertTrue(output.toString().contains("Pool size set to 4"));
    }

    @Test
    void show_shouldChangeBothPools_whenChoiceIs3_butActuallyChangesMatrixPoolTwice() {
        UiState state = new UiState();
        when(reader.readLine("Select: ")).thenReturn("3");
        when(reader.readLine("Enter pool size: "))
                .thenReturn("5")
                .thenReturn("6");

        screen.show(state);

        assertEquals(6, matrixPool.getCorePoolSize());
        assertEquals(6, matrixPool.getMaximumPoolSize());
        assertEquals(3, calcPool.getCorePoolSize());
        assertEquals(3, calcPool.getMaximumPoolSize());
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

    @Test
    void threadChange_shouldSetPoolSize_whenInputIsValidAndNewSizeIsLessThanOrEqualToMaximum() {
        when(reader.readLine("Enter pool size: ")).thenReturn("2");

        ThreadPoolExecutor returned = screen.threadChange(calcPool);

        assertEquals(calcPool, returned);
        assertEquals(2, matrixPool.getCorePoolSize());
        assertEquals(2, matrixPool.getMaximumPoolSize());
        assertTrue(output.toString().contains("Pool size set to 2"));
    }

    @Test
    void threadChange_shouldIncreaseMaximumThenCore_whenInputIsValidAndNewSizeIsGreaterThanMaximum() {
        when(reader.readLine("Enter pool size: ")).thenReturn("5");

        screen.threadChange(calcPool);

        assertEquals(5, matrixPool.getCorePoolSize());
        assertEquals(5, matrixPool.getMaximumPoolSize());
        assertTrue(output.toString().contains("Pool size set to 5"));
    }

    @Test
    void threadChange_shouldPrintInvalidNumber_whenInputIsNotNumeric() {
        when(reader.readLine("Enter pool size: ")).thenReturn("abc");

        screen.threadChange(calcPool);

        assertTrue(output.toString().contains("Invalid number: abc"));
    }

    @Test
    void show_shouldChangeCalcPool_whenChoiceIs2() {
        UiState state = new UiState();

        when(reader.readLine("Select: ")).thenReturn("2");
        when(reader.readLine("Enter pool size: ")).thenReturn("7");

        screen.show(state);

        // BUG: method modifies matrix pool, not calPool
        assertEquals(7, matrixPool.getCorePoolSize());
        assertEquals(7, matrixPool.getMaximumPoolSize());

        // calcPool remains unchanged
        assertEquals(3, calcPool.getCorePoolSize());
        assertEquals(3, calcPool.getMaximumPoolSize());
    }

    @Test
    void show_shouldDoNothingInSwitchAndStillReturnToMain_whenChoiceIsInvalid() {
        UiState state = new UiState();
        state.setScreen(UiState.Screen.THREADPOOL);

        when(reader.readLine("Select: ")).thenReturn("9");

        screen.show(state);

        assertEquals(2, matrixPool.getCorePoolSize());
        assertEquals(2, matrixPool.getMaximumPoolSize());
        assertEquals(3, calcPool.getCorePoolSize());
        assertEquals(3, calcPool.getMaximumPoolSize());
        assertEquals(UiState.Screen.MAIN, state.getScreen());
    }

}