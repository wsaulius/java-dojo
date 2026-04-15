package org.example.ui.screens;

import com.google.inject.Inject;

import org.example.interfaces.annotations.CalcPool;
import org.example.interfaces.annotations.MatrixPool;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolScreen {
    private final LineReader reader;
    private final Terminal terminal;
    private final ThreadPoolExecutor pool;
    private final ThreadPoolExecutor calPool;


    @Inject
    public ThreadPoolScreen(LineReader reader, Terminal terminal, @MatrixPool ThreadPoolExecutor pool, @CalcPool ThreadPoolExecutor calPool) {
        this.reader = reader;
        this.terminal = terminal;
        this.pool = pool;
        this.calPool = calPool;
    }

    public void show(UiState state) {
        int total = pool.getPoolSize();
        int calTotal = calPool.getPoolSize();

        terminal.writer().println("Total matrix threads: " + total);
        terminal.writer().println("Total calculator threads: " + calTotal);
        terminal.writer().println("1. Change matrix thread pool.");
        terminal.writer().println("2. Change calculator thread pool.");
        terminal.writer().println("3. Change both pool.");
        terminal.writer().println("4. Change none");
        terminal.flush();
        String c = reader.readLine("Select: ");
        switch (c) {
            case "1":
                threadChange(pool);
                break;
            case "2":
                threadChange(calPool);
                break;
            case "3":
                threadChange(pool);
                threadChange(calPool);
                break;
            case "4":
                state.setScreen(UiState.Screen.MAIN);
                break;
        }
        total = pool.getPoolSize();
        terminal.writer().println("New total threads: " + total);
        terminal.flush();
        state.setScreen(UiState.Screen.MAIN);
    }
    public ThreadPoolExecutor threadChange(ThreadPoolExecutor changePool) {
        String input = reader.readLine("Enter pool size: ");
        try {
            int size = Integer.parseInt(input);
            if (pool.getMaximumPoolSize() >= size) {
                pool.setCorePoolSize(size);
                pool.setMaximumPoolSize(size);
            } else {
                pool.setMaximumPoolSize(size);
                pool.setCorePoolSize(size);
            }
            terminal.writer().println("Pool size set to " + size);
        } catch (NumberFormatException e) {
            terminal.writer().println("Invalid number: " + input);
        }
        return changePool;
    }
}