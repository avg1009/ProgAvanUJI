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
import machineLearnig.Row;
import machineLearnig.Table;
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
    //p
    ScatterChart scatterChart;
    ComboBox comboX;
    ComboBox comboY;

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

        Tab solapa1 = new Tab("Knn");
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(solapa1);

        Label arriba = new Label("Arriba");
        Label abajo = new Label("Abajo");
        Label izquierda = new Label("Izquierda");

        //ComboBox
        ObservableList atributos = FXCollections.observableArrayList("sepal length","sepal width" ,"petal length","petal witdth");
        ObservableList distancias = FXCollections.observableArrayList("Euclidean" , "Manhattan");

        comboY = new ComboBox<>(atributos);
        comboY.getSelectionModel().select(comboY.getSelectionModel().getSelectedIndex());
        comboY.getSelectionModel().select(0);
        comboY.setOnAction(e-> controlador.cambiaEjes());


        comboX = new ComboBox<>(atributos);
        comboX.getSelectionModel().select(comboX.getSelectionModel().getSelectedIndex());
        comboX.getSelectionModel().select(1);
        comboX.setOnAction(e-> controlador.cambiaEjes());


        BorderPane borderPane = new BorderPane();
        ComboBox comboDistancias = new ComboBox<>(distancias);
        comboDistancias.setOnAction(e-> System.out.println(comboDistancias.getSelectionModel().getSelectedIndex()));
        comboDistancias.getSelectionModel().select(0);



        //ESCOGER Y LEER UN FICHERO
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Buscar fichero");
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


        NumberAxis xAxis=new NumberAxis();
        xAxis.setLabel("Eje x");
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Eje y");
        scatterChart=new ScatterChart(xAxis,yAxis);

        borderPane.setTop(arriba);
        borderPane.setCenter(scatterChart);
        borderPane.setLeft(comboY);
        borderPane.setBottom(comboX);

        //VBox DERECHA
        VBox vbox=new VBox(comboDistancias,OpenFileButton);
        BorderPane.setAlignment(vbox,Pos.BOTTOM_CENTER);
        borderPane.setRight(vbox);
        BorderPane.setAlignment(vbox,Pos.CENTER_RIGHT);
        BorderPane.setMargin(comboDistancias,new Insets(15,15,15,15));
        BorderPane.setAlignment(comboY,Pos.CENTER_LEFT);
        BorderPane.setMargin(comboY,new Insets(15,15,15,15));
        BorderPane.setAlignment(comboX,Pos.BOTTOM_CENTER);
        BorderPane.setMargin(comboX,new Insets(15,15,15,15));

        BorderPane.setAlignment(arriba,Pos.TOP_CENTER);
        BorderPane.setAlignment(abajo,Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(izquierda,Pos.CENTER_LEFT);


        solapa1.setContent(borderPane);
        Scene scene = new Scene(tabPane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void nuevosDatos(){
        scatterChart.getData().remove(0,scatterChart.getData().size());
    }

    @Override
    public void muestraTabla( ){
        Table tabla=this.modelo.getTabla();
        XYChart.Series series=new XYChart.Series();
        for(Row row:tabla.row){
            List<Double> filaData= row.getData();
            series.getData().add(new XYChart.Data(filaData.get(comboX.getSelectionModel().getSelectedIndex()),
                    filaData.get(comboY.getSelectionModel().getSelectedIndex())));
        }
        scatterChart.getData().add(series);
    }

}
