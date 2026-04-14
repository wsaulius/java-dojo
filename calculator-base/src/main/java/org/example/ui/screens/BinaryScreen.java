package org.example.ui.screens;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.execution.*;
import org.example.models.BinaryCalculationRecord;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class BinaryScreen {

    private final LineReader reader;
    private final DefaultCalculationExecutor sync;
    private final DefaultAsyncCalculationExecutor async;
    private final Terminal terminal;
    @Inject
    public BinaryScreen(LineReader reader,
                        DefaultCalculationExecutor sync,
                        DefaultAsyncCalculationExecutor async, Terminal terminal1) {
        this.reader = reader;
        this.sync = sync;
        this.async = async;

        this.terminal = terminal1;
    }

    public void show(UiState state) {
        try {
            BinaryType type = BinaryType.valueOf(
                    reader.readLine("Operation (ADD, SUBTRACT...): ").toUpperCase()
            );

            double a = Double.parseDouble(reader.readLine("Left: "));
            double b = Double.parseDouble(reader.readLine("Right: "));
            long start = System.nanoTime();

            if (state.isAsyncMode()) {

                CompletableFuture<BinaryCalculationRecord> future =
                        async.submitBinary(type, a, b);

                terminal.writer().println("Result: " + future.join().result());
                long end = System.nanoTime();
                terminal.writer().println("\u001B[31mBinary took " +
                        (end - start) / 1_000_000.0 + " ms\u001B[0m");
                terminal.flush();

            } else {

                Future<BinaryCalculationRecord> future =
                        sync.submitBinary(type, a, b);

                terminal.writer().println("Result: " + future.get().result());
                long end = System.nanoTime();
                terminal.writer().println("\u001B[31mBinary took " +
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