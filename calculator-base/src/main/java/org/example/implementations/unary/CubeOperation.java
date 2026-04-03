package org.example.implementations.unary;

import org.example.interfaces.UnaryOperation;

public class CubeOperation implements UnaryOperation<Integer, Integer> {
    @Override
    public Integer apply(Integer n) {
        return n * n * n;
    }
}