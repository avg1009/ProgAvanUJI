package controlador;

import javafx.event.ActionEvent;
import machineLearnig.TableWithLabels;

import java.io.File;
import java.util.List;

public interface Controlador {
    void creaTabla(String ejeX, String ejeY);
    void muestraTabla();
    void cambiaEjes();
    void openFile( );
    //void convierteArchivoATabla(String fileName);
    List<Double> conseguirCoordenadas(String coordenadas);
    void leeEstimate(ActionEvent e);


}