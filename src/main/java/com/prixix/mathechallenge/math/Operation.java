package com.prixix.mathechallenge.math;

public enum Operation {

    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE;

    public double operationResult(double a, double b) {
        return switch (this) {
            case PLUS -> a + b;
            case MINUS -> a - b;
            case MULTIPLY -> a * b;
            case DIVIDE -> a / b;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public char getOperationSymbol() {
        return switch (this) {
            case PLUS -> '+';
            case MINUS -> '-';
            case MULTIPLY -> '*';
            case DIVIDE -> '/';
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public static Operation getOperationByCode(int code) {
        return switch (code) {
            case 0 -> PLUS;
            case 1 -> MINUS;
            case 2 -> MULTIPLY;
            case 3 -> DIVIDE;
            default -> throw new IllegalStateException("Unexpected value: " + code);
        };
    }

}
