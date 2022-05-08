package machineLearnig;

public class DistanceFactory implements Factory {

    @Override
    public Distance getDistance(DistanceType distanceType) {
        Distance distancia;

        switch (distanceType){
            case EUCLIDIAN: distancia = new EuclideanDistance();
            break;
            case MANHATTAN: distancia = new ManhattanDistance();
            break;
            default:
                distancia= new EuclideanDistance();
                break;
        }
        return distancia;
    }
}
