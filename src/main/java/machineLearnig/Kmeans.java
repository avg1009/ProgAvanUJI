package machineLearnig;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Belen Ariño y Adolfo Viñé
 */


public class Kmeans implements Algorithm<Row,Integer,Table>,DistanceClient {
    private List<Row> representantes = new LinkedList<>();
    //tenemos una lista de 3 listas (una por cada representante)
    // dentro de estas listas tendremos el valor acomulado de las variables
    //      sepal length;sepal width;petal length;petal width
    private List<List<Double>> centroidesGrupos=new LinkedList<>();
    private List<Integer> elementosPorGrupo=new LinkedList<>(); //num de elementos asignados a cada grupo
    private int iterations;
    private int numberClusters;
    private Random random;

    private Distance distance;

    public Kmeans(int numberClusters, int iterations, long seed,Distance distance){
        this.iterations = iterations;
        this.numberClusters= numberClusters;
        this.random = new Random(seed);
        setDistance(distance);

    }

    @Override
    //El argumento dado tiene que ser Table o una clase hija de Table, como por ejemplo TableWithLabels.
    public void train(Table table) throws IndexOutOfBoundsException{
        if(numberClusters>table.row.size()){
            throw new IndexOutOfBoundsException();
        }
        elegirRepresentantes(numberClusters,table);  //Elegimos los representantes que queramos
        for(int j=0;j<iterations;j++) {



            for (Row elemento : table.row) {   //Calcular distancia de cada punto a los representantes
                List<Double> distancias = calcularDistancias(elemento);
                actualizarCentroides(buscarRepresentante(distancias), elemento);
            }


            // tenemos en centroidesGrupos la suma de todos los valores de los centroides
            int posCentroide=0;

            for(List<Double> centroide:centroidesGrupos){
                int pos=0;
                for(Double num:centroide){
                    centroidesGrupos.get(posCentroide).set(pos,num/elementosPorGrupo.get(posCentroide));
                    pos++;
                }
                representantes.get(posCentroide).data = centroide;
                posCentroide++;
            }
            int i=0;
            //El nuevo centroide de cada grupo pasrá a ser el nuevo representante
            for(Row row:representantes){
                row.data=centroidesGrupos.get(i);
                i++;
            }
        }
    }

    @Override
    //Proporcione un nuevo ejemplar Row y que devuelva el grupo al que el algoritmo estima que pertenece el ejemplar
    public Integer estimate(Row row) { //asignar a la muestra la etiqueta más cercana
        List <Double> distancias=calcularDistancias(row);
        int grupo=buscarRepresentante(distancias);
        return grupo;

    }
    private void elegirRepresentantes(int numRepesentantes, Table tabla) {
        int pos;
        for (int i = 0; i < numRepesentantes; i++) {
            pos=(int)(Math.abs(random.nextInt()) % tabla.row.size());
            Row newRep = tabla.row.get(pos);
            representantes.add(newRep);
            centroidesGrupos.add(new LinkedList<>());
            elementosPorGrupo.add(i,0);
        }
    }
    public List<Double> calcularDistancias(Row elemento) {
        List<Double> distanciasRepesentantes = new LinkedList<>();
        for (int i = 0; i < representantes.size(); i++) {
            Double distancia = distance.calculateDistance(elemento.data, representantes.get(i).data);
            distanciasRepesentantes.add(distancia);
        }
        return distanciasRepesentantes;

    }
    private int buscarRepresentante(List <Double> distanciasRepesentantes){
        Double distanciaMenor = null;
        int grupoElegido = 0;
        for (int i = 0; i < distanciasRepesentantes.size(); i++) {
            if (distanciaMenor == null) {
                distanciaMenor = distanciasRepesentantes.get(i);
                grupoElegido = i;
            } else {
                if (distanciasRepesentantes.get(i) < distanciaMenor) {
                    distanciaMenor = distanciasRepesentantes.get(i);
                    grupoElegido = i;
                }
            }
        }
        return grupoElegido;

    }
    private void actualizarCentroides(int grupoElegido, Row elemento ) {
        elementosPorGrupo.set(grupoElegido, elementosPorGrupo.get(grupoElegido) + 1);
        if (centroidesGrupos.get(grupoElegido).size() == 0) {
            centroidesGrupos.set(grupoElegido, elemento.data);
        } else {
            for (int i = 0; i < elemento.data.size(); i++) {
                Double valor = Math.round((elemento.data.get(i) + centroidesGrupos.get(grupoElegido).get(i))*100)/ 100d;
                centroidesGrupos.get(grupoElegido).set(i, valor);
            }
        }
    }


    @Override
    public void setDistance(Distance distance) {
            this.distance=distance;
    }

}