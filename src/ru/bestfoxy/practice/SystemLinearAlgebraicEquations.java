package ru.bestfoxy.practice;

public class SystemLinearAlgebraicEquations {

    private static final String FORMAT = String.format("%s%d%s", "%", 8, ".1f");

    private final double[][] matrix;
    private final double[] answers;
    private final boolean[] lines;

    public SystemLinearAlgebraicEquations(int[][] matrix, int[] answers) {
        this.checkArguments(matrix, answers);
        this.matrix = parseMatrixIntToDouble(matrix);
        this.answers = parseIntToDouble(answers);
        this.lines = new boolean[answers.length];
    }

    private void checkArguments(int[][] matrix, int[] answers) {
        if(matrix == null || matrix.length == 0)
            throw new IllegalArgumentException("Matrix not may be null or empty.");
        if(answers == null || answers.length == 0)
            throw new IllegalArgumentException("Answers not may be null or empty.");
        if(matrix.length != answers.length)
            throw new IllegalArgumentException("Amount answer not equal height matrix.");
        if(!checkSquareMatrix(matrix))
            throw new IllegalArgumentException("Matrix not may be not square.");
    }

    private boolean checkSquareMatrix(int[][] matrix) {
        int size = matrix.length;
        for(int[] line : matrix) {
            if(line.length != size)
                return false;
        }
        return true;
    }

    private double[][] parseMatrixIntToDouble(int[][] matrix) {
        double[][] result = new double[matrix.length][];
        int count = 0;
        for(int[] line : matrix)
            result[count++] = parseIntToDouble(line);
        return result;
    }

    private double[] parseIntToDouble(int[] line) {
        double[] result = new double[line.length];
        for(int i = 0; i < line.length; i++)
            result[i] = line[i];
        return result;
    }

    public void calculation() {
        System.out.println("Дана СЛАУ:");
        printSLAU(matrix, answers);

        double element;
        int index;
        int row = 0;

        while(!isEnd(lines)) {
            System.out.println("####################### СТОЛБЕЦ №" + (row + 1) + " #######################\n");

            System.out.println("Ищем разрешающий элемент в столбце #" + (row + 1) + ":");
            index = getIndexMin(matrix, lines, row);
            element = matrix[index][row];
            System.out.printf("элемент = %.1f;\n", element);

            System.out.println("\nДелим строку #" + (index + 1) + " на " + element + ":");
            divToElement(matrix[index], element);
            answers[index] /= element;
            printSLAU(matrix, answers);

            System.out.println("Обнулим в столбце #" + (row + 1) + " все элементы, кроме разрешающего:");
            toNullifyElements(matrix, answers, index, row);
            printSLAU(matrix, answers);

            lines[index] = true;
            row++;
        }
        System.out.println("###########################################################\n");
    }

    public double[] getResult() {
        if(matrix == null || matrix.length == 0 || answers == null || answers.length == 0)
            throw new IllegalArgumentException("Calculation failed.");
        return getAnswer(matrix, answers);
    }

    private double[] getAnswer(double[][] matrix, double[] answers) {
        double[] result = new double[answers.length];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 1.) {
                    result[j] = answers[i];
                }
            }
        }
        return result;
    }

    private boolean isEnd(boolean[] lines) {
        for(boolean index : lines) {
            if(!index)
                return false;
        }
        return true;
    }

    private void toNullifyElements(double[][] matrix, double[] answers, int exceptLine, int row) {
        for(int i = 0; i < matrix.length; i++) {
            if(i == exceptLine)
                continue;
            double first = matrix[i][row];
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] != 0.)
                    matrix[i][j] -= first * matrix[exceptLine][j];
            }
            if(answers[i] != 0.)
                answers[i] -= first * answers[exceptLine];
        }
    }

    private void divToElement(double[] line, double div) {
        for(int index = 0; index < line.length; index++) {
            if(line[index] != 0.)
                line[index] /= div;
        }
    }

    private void printSLAU(double[][] matrix, double[] answers) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < answers.length; i++) {
            sb.append("|");
            for(int j = 0; j < matrix[i].length; j++)
                sb.append(" ").append(String.format(FORMAT, matrix[i][j]));
            sb.append(" | ").append(String.format(FORMAT, answers[i])).append(" |\n");
        }
        System.out.println(sb);
    }

    private int getIndexMin(double[][] matrix, boolean[] lines, int index) {
        int res = -1;
        double min = Integer.MAX_VALUE;
        for(int i = 0; i < matrix.length; i++) {
            if(!lines[i] && Math.abs(matrix[i][index]) < Math.abs(min) && matrix[i][index] != 0) {
                res = i;
                min = matrix[i][index];
            }
        }
        return res;
    }
}
