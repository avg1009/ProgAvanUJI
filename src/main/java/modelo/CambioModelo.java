package modelo;


import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import machineLearnig.Table;
import machineLearnig.TableWithLabels;

import java.util.List;

public interface CambioModelo {

    //void muestraTabla();
    void cambiaEjes();
    void añadeDatos(XYChart.Series series);
    void vaciaDatos();
    void añadeEjes(NumberAxis X,NumberAxis Y);
    void añadeValoresTabla();
    void añadeTabla(TableWithLabels table);
    void estimate(List<Double> sample);


}
