package ru.bestfoxy.practice;

/**
 *  1YTC
 *  ANDREY ASHLA
 *  "The Gauss — Jordan method"
 */
public class Main {

    public static void main(String[] args) {
        int[] answers = {128, 208, 240, 154};
        int[][] matrix = {
                {12, 6, 2, 16},
                {20, 56, 18, 17},
                {18, 0, 34, 15},
                {2, 5, 17, 17}
        };
        SystemLinearAlgebraicEquations slau = new SystemLinearAlgebraicEquations(matrix, answers);
        slau.calculation();
        double[] result = slau.getResult();
        System.out.println("Ответ:");
        for(int i = 0; i < result.length; i++)
            System.out.printf("   x%d = %7.1f;\n", i + 1, result[i]);
    }
}
