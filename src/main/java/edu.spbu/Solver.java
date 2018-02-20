package edu.spbu;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class Solver {
    static double eps = 0.0000001;
    static int N;
    static int M;
    static DoubleUnaryOperator[] P;
    static double[] mas;


    public Solver(DoubleUnaryOperator[] P, int N) {
        this.P = P;
        this.N = N;
    }


    public static void solve() {
        mas = new double[N];
        for (int k = 0; k < N; k++) {
            M = 0;
            mas[k] = H1(Math.cos(Math.PI * ((k - 0.25) / (N + 0.5))));
            System.out.println(M);
        }
    }

    public static double H1(double x0) {
        double dif;
        dif = dif(P, x0);
        double x1 = x0 - f(x0) / dif;
        if ((abs(x0 - x1) > eps)) {
            M++;
            return H1(x1);
        } else
            return x1;

    }

    private static double f(double x) {
        return P[N].applyAsDouble(x);
    }

    private static double abs(double x) {
        if (x > 0) return x;
        else return (-x);
    }

    public static double dif(DoubleUnaryOperator[] mas, double z) {
        double dif;
        DoubleUnaryOperator f = x -> x;
        dif = (mas[N - 1].applyAsDouble(z) - mas[N].applyAsDouble(z) * f.applyAsDouble(z)) * (double) N / (1 - f.applyAsDouble(z) * f.applyAsDouble(z));
        return dif;
    }

    public double[] getMas() {
        return mas;
    }
}
