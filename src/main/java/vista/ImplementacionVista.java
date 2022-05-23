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
    public final Stage stage;

    private Controlador controlador;
    private InterrogaModelo modelo;
    // texto donde se almacena las coordenadas
    private TextField textCoordenadas;
    //texto donde mostraremos la nueva estimacion
    private TextField textEstimación;
    private ScatterChart scatterChart;
    //grafico


    //COMBO BOX X Y DISTANCIAS

     ComboBox comboX;
     ComboBox comboY;
     ComboBox comboDistancias;

    //Lista distancias
    private ObservableList distancias;
    //Lista atributos
    private ObservableList atributos;
    //String de la estimacion del nuevo punto
    private String labelNuevoPunto;

    //objeto de la fabrica de distancias
    static DistanceFactory distanceFactory;

    //Boton estimate
    private Button botonEstimate;

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
        textEstimación = new TextField("Label");
        //textEstimación.setDisable(true);


        //Boton
        botonEstimate = new Button("Estimate");
        // llamaria al controlador para volver a crear la grafica añadiendo el punto de referencia
        botonEstimate.setOnAction(e-> controlador.leeEstimate(e));

        //Listas
        atributos = FXCollections.observableArrayList("sepal length","sepal width" ,"petal length","petal witdth");
        distancias = FXCollections.observableArrayList("Euclidean" , "Manhattan");


        //Combos Y
        comboY = new ComboBox<>(atributos);
        comboY.getSelectionModel().select(comboY.getSelectionModel().getSelectedItem());
        comboY.getSelectionModel().select(0);
        //llama a controlador
        comboY.setOnAction(e-> controlador.cambiaEjes());

        //Combos X
        comboX = new ComboBox<>(atributos);
        comboX.getSelectionModel().select(comboX.getSelectionModel().getSelectedIndex());
        comboX.getSelectionModel().select(1);
        //llama controlador
        comboX.setOnAction(e-> controlador.cambiaEjes());



        BorderPane borderPane = new BorderPane();
        comboDistancias = new ComboBox<>(distancias);
        //llama controlador en este caso es solo una llamada
        comboDistancias.setOnAction(e-> System.out.println(comboDistancias.getSelectionModel().getSelectedIndex()));
        comboDistancias.getSelectionModel().select(0);



        //ESCOGER Y LEER UN FICHERO controlador

        Button OpenFileButton = new Button("Abrir archivo");
        OpenFileButton.setOnAction(e -> controlador.openFile());

        //Grafico
        //Establecer Ejes
        controlador.creaTabla(comboX.getSelectionModel().getSelectedItem().toString(),comboY.getSelectionModel().getSelectedItem().toString());

        //Border Pane

        //borderPane.setCenter(scatterChart);
        borderPane.setCenter(modelo.getScatterChart());
        borderPane.setLeft(comboY);
        borderPane.setBottom(comboX);

        //VBox DERECHA
        VBox vbox=new VBox(OpenFileButton,comboDistancias,textCoordenadas,textEstimación,botonEstimate);
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

    public void setScatterChart(ScatterChart scatterChart) {
        this.scatterChart = scatterChart;
    }

    public File getFile(){
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(stage);
    }
    @Override
    public void changeTextEstimacion(String label){
        textEstimación = new TextField(label);

    }
    @Override
    public int getIndexAtributosX() {
        return comboX.getSelectionModel().getSelectedIndex();
    }

    @Override
    public int getIndexAtributosY() {
        return comboY.getSelectionModel().getSelectedIndex();
    }

    @Override
    public int getIndexDistancia() {
        return comboDistancias.getSelectionModel().getSelectedIndex();
    }

    @Override
    public String getTextCoordenadas() {
        return textCoordenadas.getText().toString();
    }



}
