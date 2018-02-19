package edu.spbu;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Миша on 07.03.2017.
 */
public class Draw extends JPanel {
    double[] arrayX;
    double[] arrayY;


    public Draw(double[] a1, double[] a2) throws IOException {

        //XYChart chart = QuickChart.getChart("Приближенное решение", "t", "un(t)", "un(t)",a1,a2);
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Приближенное решение").xAxisTitle("t").yAxisTitle("un(t)").build();
        // Show it

        new SwingWrapper(chart).displayChart();
        //BitmapEncoder.saveBitmapWithDPI(chart, "5", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public Draw(double[] a1, double[] a2, double[] b2) throws IOException {

        XYChart chart = new XYChartBuilder().width(1024).height(720).title("Приближенное решение").xAxisTitle("t").yAxisTitle("un(t)").build();

        XYSeries series = chart.addSeries("Метод Галёркина", a1, a2);
        series = chart.addSeries("Метод коллокации", a1, b2);

        new SwingWrapper(chart).displayChart();

    }


}