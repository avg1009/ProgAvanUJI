package modelo;
import java.util.ArrayList;

import machineLearnig.CSV;
import machineLearnig.Table;
import vista.InformaVista;


public class ImplementacionModelo implements CambioModelo, InterrogaModelo {
	private ArrayList<String> entradas = new ArrayList<String>();
	private int posicionActual = 0;
	private InformaVista vista;
	private CSV newCsv=new CSV();
	private Table table;

	public ImplementacionModelo(){}
	
	public void setVista(InformaVista vista) {
		this.vista = vista;
	}

	public void cambiaEjes(){
		this.vista.nuevosDatos();
		this.vista.muestraTabla();
	}


	@Override
	public Table getTabla(){
		return table;
	}

	@Override
	public void anyadeNuevaTabla(String fileName) {
		table=newCsv.readTableWithLabels(fileName);
		this.vista. muestraTabla();
	}
}