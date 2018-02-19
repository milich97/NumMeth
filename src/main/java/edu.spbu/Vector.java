package edu.spbu;

/**
 * Created by Миша on 10.10.2017.
 */


/**
 * Created by Миша on 26.09.2017.
 */
public class Vector {
    double[] vector;

    public Vector(int n) {
        vector = new double[n];
    }

    public Vector(double[] b) {
        int n = b.length;
        vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = b[i];
        }
    }


    public void printVector() {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
    }
}

