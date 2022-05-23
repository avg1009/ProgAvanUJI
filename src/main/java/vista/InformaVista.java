package vista;

import javafx.scene.chart.ScatterChart;

import java.util.List;

public interface InformaVista {
	//La vista recive información

	void setScatterChart(ScatterChart scatterChart);
	int getIndexAtributosX();
	 int getIndexAtributosY();
	 int getIndexDistancia();
	 void changeTextEstimacion(String label);
}
