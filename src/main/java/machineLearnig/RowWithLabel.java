package machineLearnig;

import java.util.LinkedList;
import java.util.List;

public class RowWithLabel extends Row{
    String label;

    public RowWithLabel(List<Double> r, String lab){
        super(r);
        label=lab;
    }
    public void setLabel(String l) {
        label=l;
    }
    public String getLabel() {
        return label;
    }
}
