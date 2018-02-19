package edu.spbu;

import java.util.Random;


/**
 * Created by Миша on 10.10.2017.
 */
public class Matrix {
    double[][] matrix;

    public Matrix(int n) {
        matrix = new double[n][n];
    }

    public Matrix(double[][] a) {
        int n = a.length;
        matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = a[i][j];
            }
        }
    }


    public double det() {
        double res;
        int N = matrix.length;
        if (N == 1)
            res = matrix[0][0];
        else if (N == 2)
            res = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        else {
            res = 0;
            for (int j1 = 0; j1 < N; j1++) {
                Matrix m = new Matrix(N);
                m.matrix = generateSubArray(matrix, N, j1);
                res += Math.pow(-1.0, 1.0 + j1 + 1.0) * matrix[0][j1] * m.det();
            }
        }
        return res;
    }

    public double[][] generateSubArray(double A[][], int N, int j1) {
        double[][] m = new double[N - 1][];
        for (int k = 0; k < (N - 1); k++)
            m[k] = new double[N - 1];

        for (int i = 1; i < N; i++) {
            int j2 = 0;
            for (int j = 0; j < N; j++) {
                if (j == j1)
                    continue;
                m[i - 1][j2] = A[i][j];
                j2++;
            }
        }
        return m;
    }

    public Matrix getInverse() throws Exception {
        Matrix inverseMatrix = new Matrix(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            Vector b = new Vector(matrix.length);
            for (int j = 0; j < matrix.length; j++) {
                b.vector[j] = kroneker(i, j);
            }
            Kramer kramer = new Kramer();
            b.vector = kramer.solve(this, b);
            for (int j = 0; j < b.vector.length; j++) {
                inverseMatrix.matrix[j][i] = b.vector[j];
            }
        }
        return inverseMatrix;
    }

    private int kroneker(int i, int j) {
        if (i == j) return 1;
        return 0;
    }

    public double norm() {
        double m = 0;
        for (int j = 0; j < matrix.length; j++) {
            double sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > m) m = sum;
        }
        return m;
    }

    public double cond() throws Exception {
        return (this.getInverse().norm() * this.norm());
    }

    public void printfMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
