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
            BinaryType type = null;
            terminal.writer().println("Binary Operations List");
            terminal.writer().println("1.ADD");
            terminal.writer().println("2.SUBTRACT");
            terminal.writer().println("3.MULTIPLY");
            terminal.writer().println("4.DIVIDE");
            terminal.writer().println("5.MODULO");
            terminal.writer().println("6.POWER");
            terminal.writer().println("7.MAX");
            terminal.writer().println("8.MIN");
            terminal.flush();
            String select = reader.readLine("Select: ");
            switch (select) {
                case "1" :
                    type = BinaryType.valueOf("ADD");
                    break;
                case "2" :
                    type = BinaryType.valueOf("SUBTRACT");
                    break;
                case "3" :
                    type = BinaryType.valueOf("MULTIPLY");
                    break;
                case "4" :
                    type = BinaryType.valueOf("DIVIDE");
                    break;
                case "5" :
                    type = BinaryType.valueOf("MODULO");
                    break;
                case "6" :
                    type = BinaryType.valueOf("POWER");
                    break;
                case "7" :
                    type = BinaryType.valueOf("MAX");
                    break;
                case "8" :
                    type = BinaryType.valueOf("MIN");
                    break;
                default:
                    terminal.writer().println("Invalid Input");
                    terminal.flush();
                    state.setScreen(UiState.Screen.MAIN);
                    select = reader.readLine("Select: ");
            }

            double a = Double.parseDouble(reader.readLine("1st number: "));
            double b = Double.parseDouble(reader.readLine("2nd number: "));
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