package com.prixix.mathechallenge.math;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Problem {

    private static final int DEFAULT_MIN = 2;
    private static final int DEFAULT_MAX = 100;

    @Getter @Setter
    private int firstNumber;
    @Getter @Setter
    private int secondNumber;
    @Getter @Setter
    private Operation operation;

    public static Problem generateRandomProblem(int min, int max) {
        Problem problem = new Problem();
        Random random = new Random();

        int firstNumber = random.nextInt(max - min) + min;
        int secondNumber = random.nextInt(max - min) + min;

        problem.setFirstNumber(firstNumber);
        problem.setSecondNumber(secondNumber);

        Operation operation = Operation.getOperationByCode(random.nextInt(Operation.values().length));
        problem.setOperation(operation);

        return problem;
    }

    public static Problem generateRandomProblem() {
        return generateRandomProblem(DEFAULT_MIN, DEFAULT_MAX);
    }
}
