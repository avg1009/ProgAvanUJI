package controlador;

import javafx.stage.FileChooser;
import modelo.CambioModelo;
import vista.InterrogaVista;

public class ImplementacionControlador implements Controlador {
	private CambioModelo modelo;
	private InterrogaVista vista;


	public ImplementacionControlador() {}
	public void cambiaEjes(){
		this.modelo.cambiaEjes();
	}


	public void crearTabla(String fileName){
		this.modelo.anyadeNuevaTabla(fileName);
	}

	public void setModelo(CambioModelo modelo) {
		this.modelo = modelo;
	}

	public void setVista(InterrogaVista vista) {
		this.vista = vista;
	}

}