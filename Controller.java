package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    javafx.scene.control.Label averageLabel;
    @FXML
    javafx.scene.control.Label meanLabel;
    @FXML
    javafx.scene.control.TextField maxRadiusJTF;
    @FXML
    javafx.scene.control.TextField timeJTF;
    @FXML
    javafx.scene.control.TextField layersCountJTF;
    @FXML
    RadioButton radioButton1;
    @FXML
    RadioButton radioButton2;
    @FXML
    RadioButton radioButton4;
    @FXML
    RadioButton radioButton6;

    double t, x, y, l;
    int n;
    List<Double> _h = new ArrayList<Double>();
    double mat, avgmat, mul;
    List<Double> dh = new ArrayList<Double>();
    List<Double> _l = new ArrayList<Double>();
    Random rnd = new Random();

    public void btn(javafx.event.ActionEvent actionEvent) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        mat = 0;
        avgmat = 0;
        mul = 1;
        for (int v=0; v<100; v++) {
            l = 0;
            t = Double.parseDouble(timeJTF.getText());
            _h.add(Double.parseDouble(maxRadiusJTF.getText()));
            n = Integer.parseInt(layersCountJTF.getText());
            if (radioButton1.isSelected()) {
                t = t / 1;
                mul = 1;
            }
            if (radioButton2.isSelected()) {
                t = t / 2;
                mul = 2;
            }
            if (radioButton4.isSelected()) {
                t = t / 4;
                mul = 4;
            }
            if (radioButton6.isSelected()) {
                t = t / 6;
                mul = 6;
            }

            for (int i = 0; i < n - 1; i++) {
                _h.add(_h.get(i) - (_h.get(0) / n));
            }

            for (int i = 1; i <= n; i++) {
                dh.add((double) ((double)(i * i) / (double)(100 * n)));
            }

            for (int i = 0; i < t; i++) {
                x = rnd.nextDouble() * _h.get(0);
                y = rnd.nextDouble() * _h.get(0);
                while (Math.sqrt(x * x + y * y) > _h.get(0))
                {
                    x = rnd.nextDouble() * _h.get(0);
                    y = rnd.nextDouble() * _h.get(0);
                }
                for (int j = 0; j < n - 1; j++)
                {
                    if (j < n - 1)
                    {
                        if ((Math.sqrt(x * x + y * y) <= _h.get(j)) && (Math.sqrt(x * x + y * y) > _h.get(j + 1)))
                            l = l + mul * dh.get(j);
                    }
                    if ((Math.sqrt(x * x + y * y) <= _h.get(n - 1)) && (Math.sqrt(x * x + y * y) >= 0))
                        l = l + mul * dh.get(n - 1);
                }
            }
            System.out.println(l);
            series.getData().add(new XYChart.Data<String, Number>(String.valueOf(v),l));
            mat = mat + l;
            _l.add(l);
        }

        for (int h=0; h<100; h++)
        {
            avgmat = avgmat + (_l.get(h) - mat) * (_l.get(h) - mat);
        }
        avgmat = Math.sqrt(avgmat / 100);
        averageLabel.setText(String.valueOf(mat/100));
        meanLabel.setText(String.valueOf(avgmat));
        barChart.getData().addAll(series);
    }
}
