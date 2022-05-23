package modelo;
import java.util.ArrayList;
import java.util.List;
import controlador.Controlador;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import machineLearnig.*;
import vista.InformaVista;


public class ImplementacionModelo implements CambioModelo, InterrogaModelo {

	private InformaVista vista;
	private CSV newCsv=new CSV();
	private TableWithLabels table;
	//objeto de la fabrica de distancias
	static DistanceFactory distanceFactory;
	private ScatterChart scatterChart;


	public ImplementacionModelo(){}
	
	public void setVista(InformaVista vista) {
		this.vista = vista;
	}

	public void añadeEjes(NumberAxis xAxis,NumberAxis yAxis){
		scatterChart=new ScatterChart(xAxis,yAxis);
	}

	public ScatterChart getScatterChart(){
		return scatterChart;
	}

	public void cambiaEjes(){
		vaciaDatos();
		añadeValoresTabla();
	}


	public void añadeDatos(XYChart.Series series){
		vaciaDatos();
		añadeTabla(table);
		scatterChart.getData().add(series);
		vista.setScatterChart(scatterChart);
	}

	public void vaciaDatos(){
		scatterChart.getData().remove(0,scatterChart.getData().size());
	}

	public void añadeTabla(TableWithLabels table){
		this.table=table;
		añadeValoresTabla();
	}



	@Override
	public void añadeValoresTabla( ){
		TableWithLabels tabla=table;

		XYChart.Series seriesSetosa=new XYChart.Series();
		XYChart.Series seriesVersicolor=new XYChart.Series();
		XYChart.Series seriesVirginica=new XYChart.Series();
		XYChart.Series seriesOther=new XYChart.Series();

		for(Row row:tabla.row){

			List<Double> filaData= row.getData();
			RowWithLabel labelRow=(RowWithLabel) row;
			String label=labelRow.getLabel();
			int x= vista.getIndexAtributosX();
			int y= vista.getIndexAtributosY();
			if(label.equals("Iris-setosa")){
				seriesSetosa.getData().add(new XYChart.Data(filaData.get(x), filaData.get(y)));
			}else if (label.equals("Iris-versicolor")){
				seriesVersicolor.getData().add(new XYChart.Data(filaData.get(x), filaData.get(y)));
			}else if(label.equals("Iris-virginica")){
				seriesVirginica.getData().add(new XYChart.Data(filaData.get(x), filaData.get(y)));
			}else {seriesOther.getData().add(new XYChart.Data(filaData.get(x), filaData.get(y)));}

		}

		scatterChart.getData().addAll(seriesSetosa,seriesVirginica,seriesVersicolor,seriesOther);
		vista.setScatterChart(scatterChart);

	}

	@Override
	public void estimate(List<Double>sample){
		Distance distancia;

		if(vista.getIndexDistancia() == 0){
			distancia=new EuclideanDistance();

		}else{
			distancia=new ManhattanDistance();
		}
		KNN knn=new KNN(distancia);
		knn.train(table);

		String labelNuevoPunto=knn.estimate(sample);

		vista.changeTextEstimacion(labelNuevoPunto);
		System.out.println(labelNuevoPunto);

	}


}