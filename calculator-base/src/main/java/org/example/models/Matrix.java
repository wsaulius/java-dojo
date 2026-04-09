package org.example.models;

public record Matrix(int[][] data) {

    public int get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, int value) {
        data[row][col] = value;
    }

    public int rows() { return data.length; }
    public int cols() { return data[0].length; }
}