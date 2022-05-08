package machineLearnig;

import java.util.*;

public class Row {
    List<Double> data;

    public Row(List<Double> data){
        this.data=data;
    }

    public void addData(Double el) {
        data.add(el);
    }

    public List<Double> getData() {
        return data;
    }
}