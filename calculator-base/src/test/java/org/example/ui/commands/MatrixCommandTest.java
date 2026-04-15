package org.example.ui.commands;

import org.example.enums.BinaryType;
import org.example.models.Matrix;
import org.example.services.MatrixFacade;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MatrixCommandTest {

    @Test
    void shouldReturnUsageWhenArgumentsAreMissing() {
        MatrixFacade facade = mock(MatrixFacade.class);
        MatrixCommand command = new MatrixCommand(facade);

        String result = command.execute(new String[]{"matrix", "add", "1,2;3,4"});

        verifyNoInteractions(facade);
        assertEquals("Usage: matrix <type> <matrixA> <matrixB>", result);
    }

    @Test
    void shouldExecuteAndFormatMatrixResult() {
        MatrixFacade facade = mock(MatrixFacade.class);
        Matrix resultMatrix = new Matrix(new int[][]{{6, 8}, {10, 12}});

        when(facade.computeAsync(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("add")))
                .thenReturn(CompletableFuture.completedFuture(resultMatrix));

        MatrixCommand command = new MatrixCommand(facade);

        String result = command.execute(new String[]{
                "matrix", "add", "1,2;3,4", "5,6;7,8"
        });

        verify(facade).computeAsync(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("add"));
        assertEquals("6 8 \n10 12 \n", result);
    }

    @Test
    void shouldReturnErrorForInvalidMatrixData() {
        MatrixFacade facade = mock(MatrixFacade.class);
        MatrixCommand command = new MatrixCommand(facade);

        String result = command.execute(new String[]{
                "matrix", "add", "1,x;3,4", "5,6;7,8"
        });

        verifyNoInteractions(facade);
        assertEquals("Error: For input string: \"x\"", result);
    }

    @Test
    void shouldReturnErrorWhenFacadeFails() {
        MatrixFacade facade = mock(MatrixFacade.class);

        when(facade.computeAsync(any(Matrix.class), any(Matrix.class), eq(BinaryType.ADD), eq("add")))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("boom")));

        MatrixCommand command = new MatrixCommand(facade);

        String result = command.execute(new String[]{
                "matrix", "add", "1,2;3,4", "5,6;7,8"
        });

        assertEquals("Error: java.lang.RuntimeException: boom", result);
    }
}