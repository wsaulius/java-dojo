package org.example.ui.commands;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.Command;
import org.example.models.Matrix;
import org.example.services.MatrixFacade;

public class MatrixCommand implements Command {

    private final MatrixFacade facade;

    @Inject
    public MatrixCommand(MatrixFacade facade) {
        this.facade = facade;
    }

    @Override
    public String execute(String[] args) {

        if (args.length < 4) {
            return "Usage: matrix <type> <matrixA> <matrixB>";
        }

        try {
            BinaryType type = BinaryType.valueOf(args[1].toUpperCase());

            Matrix a = parseMatrix(args[2]);
            Matrix b = parseMatrix(args[3]);

            // ASYNC execution
            Matrix result = facade
                    .computeAsync(a, b, type, args[1])
                    .join(); // CLI blocks here

            return formatMatrix(result);

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private Matrix parseMatrix(String input) {
        String[] rows = input.split(";");
        int[][] data = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] cols = rows[i].split(",");
            data[i] = new int[cols.length];

            for (int j = 0; j < cols.length; j++) {
                data[i][j] = Integer.parseInt(cols[j]);
            }
        }

        return new Matrix(data);
    }

    private String formatMatrix(Matrix m) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                sb.append(m.get(i, j)).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}