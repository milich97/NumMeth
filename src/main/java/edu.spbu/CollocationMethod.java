package edu.spbu;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by Миша on 19.02.2018.
 */
public class CollocationMethod {
    public double[] solve(DoubleUnaryOperator p, DoubleUnaryOperator r, DoubleUnaryOperator q, DoubleUnaryOperator f, DoubleUnaryOperator[] Lphi, DoubleUnaryOperator[] polynomials, int n) throws Exception {
        double[] ti = new double[n];
        for (int i = 0; i < n; i++) {
            double j = i + 1;
            ti[i] = Math.cos(((2 * j - 1) / (2 * n)) * Math.PI);
        }
        System.out.println("Метод коллокации");

        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Lphi[j].applyAsDouble(ti[i]);
            }
        }
        double[] fi = new double[n];
        for (int i = 0; i < n; i++) {
            fi[i] = f.applyAsDouble(ti[i]);
        }

        Matrix X = new Matrix(a);
        Vector y = new Vector(fi);
        Kramer kramer = new Kramer();
        if (X.det() == 0) throw new Exception();
        double[] c = kramer.solve(X, y);

        System.out.println();
        System.out.println("Расширенная матрица системы:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
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
