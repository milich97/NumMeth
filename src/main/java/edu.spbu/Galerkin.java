package edu.spbu;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by Миша on 17.02.2018.
 */
public class Galerkin {
    public double[] solve(DoubleUnaryOperator p, DoubleUnaryOperator r, DoubleUnaryOperator q, DoubleUnaryOperator f, DoubleUnaryOperator[] Lphi, DoubleUnaryOperator[] polynomials, int n) throws Exception {
        int a = -1, b = 1;
        int N = 8;
        System.out.println("Метод Галёркина");

        double[][] A = new double[n][n];
        DoubleUnaryOperator[][] F = new DoubleUnaryOperator[n][n];
        Gauss g = new Gauss();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int finalJ = j;
                int finalI = i;
                F[i][j] = x -> Lphi[finalJ].applyAsDouble(x) * (1 - x * x) * polynomials[finalI].applyAsDouble(x);
                A[i][j] = g.takeIntegral(a, b, F[i][j], N);
            }
        }

        DoubleUnaryOperator[] Fi = new DoubleUnaryOperator[n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            Fi[i] = x -> f.applyAsDouble(x) * (1 - x * x) * polynomials[finalI].applyAsDouble(x);
        }
        double[] fi = new double[n];
        for (int i = 0; i < n; i++) {
            fi[i] = g.takeIntegral(a, b, Fi[i], N);
        }


        Matrix X = new Matrix(A);
        Vector y = new Vector(fi);
        Kramer kramer = new Kramer();
        if (X.det() == 0) throw new Exception();
        double[] c = kramer.solve(X, y);

        System.out.println();
        System.out.println("Расширенная матрица системы:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println(fi[i]);
        }
        System.out.println();
        System.out.println("cond(A)= " + X.cond());
        System.out.println();
        System.out.println("Коэффициенты разложения приближённого решения:");
        for (int i = 0; i < n; i++) {
            System.out.println("c" + i + "= " + c[i]);
        }
        DoubleUnaryOperator[] ui = new DoubleUnaryOperator[n];
        ui[0] = x -> c[0] * polynomials[0].applyAsDouble(x);
        for (int i = 1; i < n; i++) {
            int finalI = i;
            ui[i] = x -> ui[finalI - 1].applyAsDouble(x) + c[finalI] * polynomials[finalI].applyAsDouble(x);
        }
        DoubleUnaryOperator un = x -> (1 - x * x) * ui[n - 1].applyAsDouble(x);

        System.out.println();
        System.out.println("un(-1/2)= " + un.applyAsDouble((double) (-1) / 2));
        System.out.println("un(0)= " + un.applyAsDouble(0));
        System.out.println("un(1/2)= " + un.applyAsDouble((double) 1 / 2));


        double[] a1 = new double[100];
        double[] a2 = new double[100];
        for (int i = 0; i < a1.length; i++) {
            double j = i;
            a1[i] = -1 + 2 * j / a1.length;
            a2[i] = un.applyAsDouble(a1[i]);
        }

//        Draw d = new Draw(a1, a2);
        return a2;
    }

}
