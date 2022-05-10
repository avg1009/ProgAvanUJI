package vista;

import controlador.Controlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import java.util.*;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import machineLearnig.*;
import modelo.InterrogaModelo;
import javafx.stage.Stage.*;
import java.awt.Desktop;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class ImplementacionVista implements InterrogaVista, InformaVista{
    private final Stage stage;
    private Controlador controlador;
    private InterrogaModelo modelo;
    private TextField textCoordenadas;

    ScatterChart scatterChart;
    ComboBox comboX;
    ComboBox comboY;
    ComboBox comboDistancias;
    ObservableList distancias;
    String labelNuevoPunto;
    Distance distancia;

    static DistanceFactory distanceFactory;

    private List<Double> sample = new LinkedList<>();

    public ImplementacionVista(final Stage stage) {
        this.stage = stage;
    }

    public void setModelo(final InterrogaModelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(final Controlador controlador) {
        this.controlador = controlador;
    }

    public void creaGUI() {
        //Solapas
        Tab solapa1 = new Tab("Knn");
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(solapa1);

        //Text Fields
        textCoordenadas = new TextField("Nuevo Punto");
        TextField textLabel = new TextField("Label");
        textLabel.setDisable(true);

        //Boton
        Button botonEstimate = new Button("Estimate");
        // llamaria al controlador para volver a crear la grafica añadiendo el punto de referencia
         botonEstimate.setOnAction(e-> leeEstimate(e));

        //Listas
        ObservableList atributos = FXCollections.observableArrayList("sepal length","sepal width" ,"petal length","petal witdth");
        ObservableList distancias = FXCollections.observableArrayList("Euclidean" , "Manhattan");


        //Combos Y
        comboY = new ComboBox<>(atributos);
        comboY.getSelectionModel().select(comboY.getSelectionModel().getSelectedIndex());
        comboY.getSelectionModel().select(0);

        comboY.setOnAction(e-> controlador.cambiaEjes());

        //Combos X
        comboX = new ComboBox<>(atributos);
        comboX.getSelectionModel().select(comboX.getSelectionModel().getSelectedIndex());
        comboX.getSelectionModel().select(1);
        comboX.setOnAction(e-> controlador.cambiaEjes());
        //comboX.setOnAction(e -> comboX.getSelectionModel().getSelectedItem());



        BorderPane borderPane = new BorderPane();
        comboDistancias = new ComboBox<>(distancias);
        comboDistancias.setOnAction(e-> System.out.println(comboDistancias.getSelectionModel().getSelectedIndex()));
        comboDistancias.getSelectionModel().select(0);



        //ESCOGER Y LEER UN FICHERO
        FileChooser fileChooser=new FileChooser();

        Button OpenFileButton=new Button("OpenFile");
        OpenFileButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        File file=fileChooser.showOpenDialog(stage);
                        if(file!=null){
                            String nombre=file.getName();
                            //Crear un objeto CSV y ejecutar el método ReadTable
                            controlador.crearTabla(nombre);
                        }
                    }
                }

        );

        //Grafico
        //Todavia no cambia dependiendo del elegido se queda con el original
        NumberAxis xAxis=new NumberAxis();
        xAxis.setLabel(comboX.getSelectionModel().getSelectedItem().toString());
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel(comboY.getSelectionModel().getSelectedItem().toString());

        scatterChart=new ScatterChart(xAxis,yAxis);
        scatterChart.setTitle(comboX.getSelectionModel().getSelectedItem().toString() + " vs " + comboY.getSelectionModel().getSelectedItem().toString());
        //Border Pane

        borderPane.setCenter(scatterChart);
        borderPane.setLeft(comboY);
        borderPane.setBottom(comboX);

        //VBox DERECHA
        VBox vbox=new VBox(OpenFileButton,comboDistancias,textCoordenadas,textLabel,botonEstimate);
        BorderPane.setAlignment(vbox,Pos.BOTTOM_CENTER);
        borderPane.setRight(vbox);
        BorderPane.setAlignment(vbox,Pos.CENTER_RIGHT);
        BorderPane.setMargin(comboDistancias,new Insets(15,15,15,15));
        BorderPane.setAlignment(comboY,Pos.CENTER_LEFT);
        BorderPane.setMargin(comboY,new Insets(15,15,15,15));
        BorderPane.setAlignment(comboX,Pos.BOTTOM_CENTER);
        BorderPane.setMargin(comboX,new Insets(15,15,15,15));



        solapa1.setContent(borderPane);
        Scene scene = new Scene(tabPane);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void estimate(List<Double>sample){

        if(distancias.get(comboDistancias.getSelectionModel().getSelectedIndex()).equals("Euclidean")){
            distancia=distanceFactory.getDistance(DistanceType.EUCLIDIAN);
        }else{
            distancia=distanceFactory.getDistance(DistanceType.MANHATTAN);
        }
        KNN knn=new KNN(distancia);
        knn.train(this.modelo.getTabla());

        labelNuevoPunto=knn.estimate(sample);
        System.out.println(labelNuevoPunto);


    }
    @Override
    public void nuevosDatos(){
        scatterChart.getData().remove(0,scatterChart.getData().size());
    }

    @Override
    public void muestraTabla( ){
        TableWithLabels tabla=this.modelo.getTabla();

        XYChart.Series seriesSetosa=new XYChart.Series();
        XYChart.Series seriesVersicolor=new XYChart.Series();
        XYChart.Series seriesVirginica=new XYChart.Series();
        XYChart.Series seriesOther=new XYChart.Series();

        for(Row row:tabla.row){

            List<Double> filaData= row.getData();
            RowWithLabel labelRow=(RowWithLabel) row;
            String label=labelRow.getLabel();
            if(label.equals("Iris-setosa")){
                seriesSetosa.getData().add(new XYChart.Data(filaData.get(comboX.getSelectionModel().getSelectedIndex()),
                        filaData.get(comboY.getSelectionModel().getSelectedIndex())));
            }else if (label.equals("Iris-versicolor")){
                seriesVersicolor.getData().add(new XYChart.Data(filaData.get(comboX.getSelectionModel().getSelectedIndex()),
                        filaData.get(comboY.getSelectionModel().getSelectedIndex())));
            }else if(label.equals("Iris-virginica")){
                seriesVirginica.getData().add(new XYChart.Data(filaData.get(comboX.getSelectionModel().getSelectedIndex()),
                        filaData.get(comboY.getSelectionModel().getSelectedIndex())));
            }else {seriesOther.getData().add(new XYChart.Data(filaData.get(comboX.getSelectionModel().getSelectedIndex()),
                    filaData.get(comboY.getSelectionModel().getSelectedIndex())));}

        }

        scatterChart.getData().addAll(seriesSetosa,seriesVirginica,seriesVersicolor,seriesOther);
    }

    public void leeEstimate(ActionEvent e) {
        estimate(sample);
        String name = textCoordenadas.getText();
        name = name.replace(",", "");
        XYChart.Series seriesPunto = new XYChart.Series();
        for (int i = 0; i < name.length(); i++) {
            char numero = name.charAt(i);
            double d1 = (double) numero;
            //Hay que restar porque está en ASCII
            d1 = d1 - 48;
            sample.add(d1);
        }

        seriesPunto.getData().add(new XYChart.Data(sample.get(comboX.getSelectionModel().getSelectedIndex()),
                sample.get(comboY.getSelectionModel().getSelectedIndex())));
        scatterChart.getData().addAll(seriesPunto);
        estimate(sample);

        System.out.println(labelNuevoPunto);

    }
}
