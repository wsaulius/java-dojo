package org.example.consumers;

import com.google.inject.Singleton;
import jakarta.jms.JMSException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.models.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Singleton
public final class MatrixXlsx implements Consumer<Matrix> {

    /** {@inheritDoc} */
    @Override
    public void accept(Matrix matrix) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Matrix");
        AtomicInteger rowIndex = new AtomicInteger(1);

        Arrays.stream(matrix.data()).forEach(row -> {
            Row excelRow = sheet.createRow(rowIndex.getAndIncrement());

            AtomicInteger colIndex = new AtomicInteger(1); // start at 1
            Arrays.stream(row).forEach(value -> {
                Cell cell = excelRow.createCell(colIndex.getAndIncrement());
                cell.setCellValue(value);
            });
        });

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            byte[] excelBytes = out.toByteArray();

            MatrixSend matrixSend = new MatrixSend();
            matrixSend.accept(excelBytes);

            try (FileOutputStream fileOut = new FileOutputStream("matrix.xlsx")) {
                fileOut.write(excelBytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
