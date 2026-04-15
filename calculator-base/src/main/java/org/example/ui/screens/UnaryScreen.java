package org.example.ui.screens;

import com.google.inject.Inject;
import org.example.execution.*;
import org.example.enums.UnaryIntType;
import org.example.models.UnaryCalculationRecord;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class UnaryScreen {

    private final LineReader reader;
    private final DefaultCalculationExecutor sync;
    private final DefaultAsyncCalculationExecutor async;
    private final Terminal terminal;

    @Inject
    public UnaryScreen(LineReader reader,
                       DefaultCalculationExecutor sync,
                       DefaultAsyncCalculationExecutor async, Terminal terminal) {
        this.reader = reader;
        this.sync = sync;
        this.async = async;
        this.terminal = terminal;
    }

    public void show(UiState state) {
        try {
            UnaryIntType type = UnaryIntType.valueOf(
                    reader.readLine("Operation (SQUARE, etc): ").toUpperCase()
            );

            int input = Integer.parseInt(reader.readLine("Value: "));
            long start = System.nanoTime();
            if (state.isAsyncMode()) {

                CompletableFuture<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                        async.submitUnaryInt(type, input);

                terminal.writer().println("Result: " + future.join().result());
                long end = System.nanoTime();
                terminal.writer().println("\u001B[31mUnary took " +
                        (end - start) / 1_000_000.0 + " ms\u001B[0m");
                terminal.flush();
            } else {

                Future<UnaryCalculationRecord<UnaryIntType, Integer, Integer>> future =
                        sync.submitUnaryInt(type, input);

                terminal.writer().println("Result: " + future.get().result());
                long end = System.nanoTime();
                terminal.writer().println("\u001B[31mUnary took " +
                        (end - start) / 1_000_000.0 + " ms\u001B[0m");
                terminal.flush();
            }

        } catch (Exception e) {
            terminal.writer().println("Error: " + e.getMessage());
            terminal.flush();
        }

        state.setScreen(UiState.Screen.MAIN);
    }
}