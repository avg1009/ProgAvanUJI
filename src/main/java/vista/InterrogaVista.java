package vista;

import java.io.File;

public interface InterrogaVista {
    //Quiere recibir información de la vista
    public File getFile();
    public int getIndexAtributosX();
    public int getIndexAtributosY();
    public int getIndexDistancia();
    public String getTextCoordenadas();

}
