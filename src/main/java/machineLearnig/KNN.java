package machineLearnig;

import java.util.LinkedList;
import java.util.List;

public class KNN implements Algorithm<List<Double>,String,TableWithLabels>,DistanceClient {
    private TableWithLabels table = new TableWithLabels() ;
    String labelmenor;
    private Double sepalLength; private Double sepalWidth; private Double petalLength; private Double petalWidth;

    private Distance distance;
    public KNN(Distance distance){
        setDistance(distance);
    }
    /**
     * inicializamos la tabla con los datos del train.
     * @param data
     */
    @Override
    public void train (TableWithLabels data){
        this.table = data;
    }

    /**
     * Hacemos una estimación de la etiqueta que tiene que dar calculando la distancia euclidea.
     * @param sample
     * @return etiqueta
     */
    @Override
    public String estimate(List<Double> sample){
        Double distancemenor=null ;
        for (Row r: table.row){
            List<Double> comprobacion = new LinkedList<>();
            sepalLength = r.data.get(0);
            sepalWidth = r.data.get(1);
            petalLength = r.data.get(2);
            petalWidth = r.data.get(3);
            comprobacion.add(sepalLength);
            comprobacion.add(sepalWidth);
            comprobacion.add(petalLength);
            comprobacion.add(petalWidth);
            Double distancia = distance.calculateDistance(comprobacion, sample);
            if(distancemenor==null){
                distancemenor = distancia;
                RowWithLabel newRowWithLabels=(RowWithLabel) r;
                labelmenor = newRowWithLabels.getLabel();
            }else if(distancia < distancemenor){
                distancemenor = distancia;
                RowWithLabel newRowWithLabels=(RowWithLabel) r;
                labelmenor = newRowWithLabels.getLabel();
            }

        }
        return labelmenor;
    }


    @Override
    public void setDistance(Distance distance) {
        this.distance=distance;
    }
}


