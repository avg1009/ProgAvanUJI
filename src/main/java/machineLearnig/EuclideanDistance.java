package machineLearnig;

import java.util.List;

public class EuclideanDistance implements Distance{
    @Override
    public Double calculateDistance(List<Double> p, List<Double> q) {
        if(p.size()>q.size() || q.size()>p.size()){
            throw new ArithmeticException();
        }
        Double sum = 0.0;
        for (int i = 0; i < p.size(); i++){
            sum += Math.pow(p.get(i)-q.get(i),2);
        }
        return Math.sqrt(sum);
    }
}
