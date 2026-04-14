package org.example.ui.screens;

import com.google.inject.Inject;
import org.example.consumers.MatrixPrinter;
import org.example.enums.BinaryType;
import org.example.execution.DefaultAsyncMatrixExecutor;
import org.example.execution.DefaultMatrixExecutor;
import org.example.models.Matrix;
import org.example.ui.state.UiState;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class MatrixScreen {

    private final LineReader reader;
    private final DefaultMatrixExecutor syncMatrixExecutor;
    private final DefaultAsyncMatrixExecutor asyncMatrixExecutor;
    private final Terminal terminal;

    @Inject
    public MatrixScreen(LineReader reader,
                        DefaultMatrixExecutor syncMatrixExecutor,
                        DefaultAsyncMatrixExecutor asyncMatrixExecutor, Terminal terminal) {
        this.reader = reader;
        this.syncMatrixExecutor = syncMatrixExecutor;
        this.asyncMatrixExecutor = asyncMatrixExecutor;
        this.terminal = terminal;
    }

    public void show(UiState state) {

        try {
            terminal.writer().println("\n=== MATRIX OPERATIONS ===");
            terminal.writer().println("ADD / SUBTRACT / MULTIPLY");
            terminal.writer().println("Type operation:");
            terminal.flush();

            BinaryType type = BinaryType.valueOf(
                    reader.readLine("Operation: ").toUpperCase()
            );

            terminal.writer().println("\nMatrix A:");
            Matrix A = new Matrix(readMatrix());
            terminal.flush();

            terminal.writer().println("\nMatrix B:");
            Matrix B = new Matrix(readMatrix());
            terminal.flush();

            Matrix result;
            long start = System.nanoTime();
            if (state.isAsyncMode()) {

                CompletableFuture<Matrix> future =
                        asyncMatrixExecutor.submit(A, B, type, type.name());

                terminal.writer().println("Calculating async...");
                terminal.flush();

                result = future.join();

            } else {

                Future<Matrix> future =
                        syncMatrixExecutor.execute(A, B, type, type.name());

                terminal.writer().println("Calculating sync...");
                terminal.flush();

                result = future.get();
            }

            long end = System.nanoTime();
            terminal.writer().println("\u001B[31mBinary took " +
                    (end - start) / 1_000_000.0 + " ms\u001B[0m");
            terminal.flush();

        } catch (Exception e) {
            terminal.writer().println("Error: " + e.getMessage());
            terminal.flush();
        }

        // return to main menu after operation
        state.setScreen(UiState.Screen.MAIN);
    }

    // =========================
    // 📥 MATRIX INPUT
    // =========================
    private int[][] readMatrix() {

        int rows = Integer.parseInt(reader.readLine("Rows: "));
        int cols = Integer.parseInt(reader.readLine("Cols: "));

        int[][] data = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] parts = reader.readLine("Row " + i + ": ").split(" ");

            for (int j = 0; j < cols; j++) {
                data[i][j] = Integer.parseInt(parts[j]);
            }
        }

        return data;
    }
}