package edu.spbu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by Миша on 17.02.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Galerkin galerkin = new Galerkin();
        CollocationMethod method = new CollocationMethod();
        System.out.println("Проекционные методы решения краевых задач для ОДУ 2го порядка");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите n - параметр задачи");
        int n = Integer.parseInt(bufferedReader.readLine());
        DoubleUnaryOperator[] polynomials = new DoubleUnaryOperator[n + 1];
        polynomials[0] = x -> 1;
        polynomials[1] = x -> 2 * x;
        for (int i = 2; i <= n; i++) {
            int finalI = i;
            polynomials[i] = x -> (finalI + 1) * ((2 * finalI + 1) * x * polynomials[finalI - 1].applyAsDouble(x) -
                    finalI * polynomials[finalI - 2].applyAsDouble(x)) / (finalI * (finalI + 2));
        }
        DoubleUnaryOperator p = x -> (x - 4) / (5 - 2 * x);
        DoubleUnaryOperator r = x -> (1 - x) / 2;
        DoubleUnaryOperator q = x -> Math.log(x + 3) / 2;
        DoubleUnaryOperator f = x -> 1 + x / 3;

        DoubleUnaryOperator[] polynomials_0 = new DoubleUnaryOperator[n + 1];
        polynomials_0[0] = x -> 1;
        polynomials_0[1] = x -> x;
        for (int i = 2; i <= n; i++) {
            int finalI = i;
            polynomials_0[i] = x -> ((2 * finalI - 1) * x * polynomials_0[finalI - 1].applyAsDouble(x) -
                    (finalI - 1) * polynomials_0[finalI - 2].applyAsDouble(x)) / (finalI);
        }

        DoubleUnaryOperator[] Lphi = new DoubleUnaryOperator[n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            Lphi[i] = x -> ((x - 4) / (2 * x - 5) * (finalI + 1) * (finalI + 2) + Math.log(x + 3) / 2 * (1 - x * x)) * polynomials[finalI].applyAsDouble(x) +
                    (x - 1) * (finalI + 1) * polynomials_0[finalI + 1].applyAsDouble(x);
        }
        double[] a1 = new double[100];
        double[] a2, b2;
        for (int i = 0; i < a1.length; i++) {
            double j = i;
            a1[i] = -1 + 2 * j / a1.length;
        }
        a2 = galerkin.solve(p, r, q, f, Lphi, polynomials, n);
        b2 = method.solve(p, r, q, f, Lphi, polynomials, n);
        Draw d = new Draw(a1, a2, b2);

    }
}
