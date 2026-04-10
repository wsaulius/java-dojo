package org.example.matrix;

import org.example.models.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest {
    @Test
    public void testGetSet() {
        Matrix m = new Matrix(new int[2][2]);
        m.set(0, 0, 5);
        assertEquals(5, m.get(0, 0));
    }
}
