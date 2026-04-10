package org.example.models;

/**
 * Represents a mutable integer matrix backed by a two-dimensional array.
 *
 * @param data matrix data
 */
public record Matrix(int[][] data) {

    /**
     * Returns the value at the specified matrix position.
     *
     * @param row row index
     * @param col column index
     * @return value stored at the given row and column
     */
    public int get(int row, int col) {
        return data[row][col];
    }

    /**
     * Sets the value at the specified matrix position.
     *
     * @param row row index
     * @param col column index
     * @param value value to store
     */
    public void set(int row, int col, int value) {
        data[row][col] = value;
    }

    /**
     * Returns the number of rows in the matrix.
     *
     * @return row count
     */
    public int rows() {
        return data.length;
    }

    /**
     * Returns the number of columns in the matrix.
     *
     * @return column count
     */
    public int cols() {
        return data[0].length;
    }
}