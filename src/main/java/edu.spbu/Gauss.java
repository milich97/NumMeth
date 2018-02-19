package edu.spbu;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by Миша on 14.05.2017.
 */
public class Gauss {

    public Gauss() throws IOException {
    }

    public static double takeIntegral(double a, double b, DoubleUnaryOperator f, int N) throws IOException {
        DoubleUnaryOperator P0 = x -> 1;
        DoubleUnaryOperator P1 = x -> x;
        DoubleUnaryOperator[] mas = new DoubleUnaryOperator[N + 1];
        mas[0] = P0;
        mas[1] = P1;
        for (int i = 2; i < N + 1; i++) {
            int finalI = i;
            DoubleUnaryOperator help = x -> (((double) (2 * finalI - 1) / finalI) * x * mas[finalI - 1].applyAsDouble(x) - ((double) (finalI - 1) / finalI) * mas[finalI - 2].applyAsDouble(x));
            mas[i] = help;
        }
        double tk[];
        Solver solver = new Solver(mas[N], N);
        solver.tab();
        solver.solve();
        tk = solver.getMas();

        double ck[] = new double[N];
        for (int i = 0; i < ck.length; i++) {
            ck[i] = 2 / ((1 - tk[i] * tk[i]) * Math.pow(dif(mas, tk[i], N), 2));
        }

        double ans = 0;
        for (int i = 0; i < N; i++) {
            ans = ans + ck[i] * f.applyAsDouble((b - a) / 2 * tk[i] + (b + a) / 2);
        }
        return ans * (b - a) / 2;
    }


    public static double dif(DoubleUnaryOperator[] mas, double z, int N) {
        double dif;
        DoubleUnaryOperator f = x -> x;
        dif = (mas[N - 1].applyAsDouble(z) - mas[N].applyAsDouble(z) * f.applyAsDouble(z)) * (double) N / (1 - f.applyAsDouble(z) * f.applyAsDouble(z));
        return dif;
    }


}
