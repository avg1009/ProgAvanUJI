package machineLearnig;

public interface Algorithm <L,S,T extends Table>{

    public void train(T table);
    public S estimate( L valor );

}