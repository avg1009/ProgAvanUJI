package controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import machineLearnig.CSV;
import machineLearnig.TableWithLabels;
import modelo.CambioModelo;
import vista.InformaVista;
import vista.InterrogaVista;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ImplementacionControlador implements Controlador {
	private CambioModelo modelo;
	private InterrogaVista vista;


	public ImplementacionControlador() {}
	public void cambiaEjes(){
		this.modelo.cambiaEjes();
	}


	public void setModelo(CambioModelo modelo) {
		this.modelo = modelo;
	}

	public void setVista(InterrogaVista vista) {
		this.vista = vista;
	}
	@Override
	public void openFile() {
		System.out.println("Abrimos el archivo:");
		File file = vista.getFile();
		if (file != null) {
			String nombre = file.getName();

			CSV newCsv=new CSV();
			modelo.añadeTabla(newCsv.readTableWithLabels(nombre));
			//modelo.añadeValoresTabla();
		}
	}



	public List<Double> conseguirCoordenadas(String name){
		List<Double> sample = new LinkedList<>();
		name = name.replace(",", "");

		XYChart.Series seriesPunto = new XYChart.Series();
		for (int i = 0; i < name.length(); i++) {
			char numero = name.charAt(i);
			double d1 = (double) numero;
			//Hay que restar porque está en ASCII
			d1 = d1 - 48;
			sample.add(d1);
		}
		return sample;

	}
	public void leeEstimate(ActionEvent e) {
		String name= vista.getTextCoordenadas();
		List<Double> sample =conseguirCoordenadas(name);

		XYChart.Series seriesPunto = new XYChart.Series();

		int x=vista.getIndexAtributosX();
		int y=vista.getIndexAtributosY();
		seriesPunto.getData().add(new XYChart.Data(sample.get(x), sample.get(y)));

		modelo.añadeDatos(seriesPunto);
		modelo.estimate(sample);

	}
	@Override
	public void creaTabla(String ejeX, String ejeY){
		NumberAxis xAxis=new NumberAxis();
		xAxis.setLabel(ejeX);
		NumberAxis yAxis=new NumberAxis();
		yAxis.setLabel(ejeY);
		modelo.añadeEjes(xAxis,yAxis);
	}
	@Override
	public void muestraTabla(){
		modelo.añadeValoresTabla();
	}

}