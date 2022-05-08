package machineLearnig;

import org.w3c.dom.ls.LSException;

import java.util.*;

public class Table {

    List<String> header;
    public List<Row> row;
    public Table(){
        header=new LinkedList<>();
        row=new LinkedList<>();
    }

    public void setHeader(List<String> header) { this.header=header; }
    public void addRow(Row r){
        this.row.add(r);

    }

    public Row getRowAt(int index){
        return row.get(index);
    }
    protected List<String> getHeader(){
        return header;
    }
    public List<Double> getColumnAt(int columnNumber){
        return null;
    }
}
