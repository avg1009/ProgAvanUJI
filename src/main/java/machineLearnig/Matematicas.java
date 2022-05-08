package machineLearnig;

import java.util.List;

public class Matematicas {
    /**
     * Calcula la distancia euclidia de dos listas
     * @param x
     * @param y
     * @return
     */
    public static Double calcularDistanciaEuclidia(List<Double> x, List<Double> y){
        Double sum = 0.0;
        for (int i = 0; i < x.size(); i++)
        {
            sum += Math.pow(x.get(i)-y.get(i),2);
        }
        return Math.sqrt(sum);
    }
    public static Double calculateMedia(Double sumatorio,int numElementos) {
        if(numElementos <= 0){
            throw new ArithmeticException();
        }
       return sumatorio / numElementos;
    }
}
