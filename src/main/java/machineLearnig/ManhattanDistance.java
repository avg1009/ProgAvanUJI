package machineLearnig;

import java.util.List;

public class ManhattanDistance implements Distance{
    public Double calculateDistance(List<Double> p, List<Double> q) {
        if(p.size()>q.size() || q.size()>p.size()){
            throw new ArithmeticException();
        }
        Double distancia = 0.0;
        for(int i= 0; i< p.size();i++){
            distancia += Math.abs(p.get(i)-q.get(i));
        }
        return distancia;
    }
}
