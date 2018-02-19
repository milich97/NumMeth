package edu.spbu;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class Solver {
    static double eps = 0.0000001;
    static int Q;
    static int mas_i;
    static DoubleUnaryOperator operator;
    static double[] mas;
    private static ArrayList<Double> a;

    public Solver(DoubleUnaryOperator operator, int Q) {
        this.operator = operator;
        this.Q=Q;
    }


    public static void half(double x1, double x2) {
        double m = (x1 + x2) / 2;
        double ans;
        if (Math.abs((f(m))) > eps) {
            if (f(x1) * f(m) < 0) {
                half(x1, m);
            } else half(m, x2);
        } else {
            ans = (x1 + x2) / 2;
            mas[mas_i]=ans;
            //System.out.println("ans=" + ans);
        }

    }


    public static void tab() {
        int N = 10000;
        double A = -1, B = 1;
        a = new ArrayList<>();
        int counter = 0;
        double h = (B - A) / N;
        double x1, x2, y1, y2;
        x1 = A;
        x2 = A + h;
        while (x2 < B) {
            y1 = f(x1);
            y2 = f(x2);
            if (y1 * y2 <= 0) {
                counter++;
                a.add(x1);
                a.add(x2);
            }
            x1 = x2;
            x2 = x1 + h;
        }

        x2 = B;
        if (f(x1) * f(B) <= 0) {
            counter++;
            a.add(x1);
            a.add(B);

        }
    }

    public static void solve() {
        // System.out.println("                                                   Метод бисекции:");
        mas=new double[Q];
        for (int i = 0; i < a.size(); i += 2) {
            mas_i=i/2;
            half(a.get(i), a.get(i + 1));
        }
    }


    private static double f(double x) {
        return operator.applyAsDouble(x);
    }


    public double[] getMas() {
        return mas;
    }
}
