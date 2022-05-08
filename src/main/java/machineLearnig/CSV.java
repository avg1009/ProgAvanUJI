package machineLearnig;

import java.io.*;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Adolfo Viñé y Belén Ariño
 */

public class CSV {
    private static final String SEPARATOR = ";";

    /**
     * Le pasamos un fichero por parametro y este metodo
     * @param fileName
     * @return tabla
     * @throws FileNotFoundException
     */

    public Table readTable(String fileName) {
        BufferedReader br = null;
        Table tabla = new Table();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            String[] fields = line.split(SEPARATOR);
            List<String> nombresCampos = new LinkedList<>();
            for (String campo : fields) {
                nombresCampos.add(campo);
            }
            tabla.setHeader(nombresCampos);
            line = br.readLine();

            while (null != line) {
                fields = line.split(SEPARATOR);
                List<Double> row=new LinkedList<>();
                for (String el : fields) {
                    row.add(Double.parseDouble(el));
                }

                tabla.addRow(new Row(row));

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } finally {
            try {
                if (null != br) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tabla;
    }

    /**
     * Pasamos un fichero por parametro con etiquetas y lo leemos
     * @param fileName
     * @return tableWithLabels
     */
    public TableWithLabels readTableWithLabels(String fileName) {
        BufferedReader br = null;
        TableWithLabels tableWithLabels = new TableWithLabels();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            String[] fields = line.split(SEPARATOR);
            List<String> nombresCampos = new LinkedList<>();
            int numCampos = 0;
            for (String campo : fields) {
                nombresCampos.add(campo);
                numCampos++;
            }

            tableWithLabels.setHeader(nombresCampos);

            line = br.readLine();
            while (null != line) {
                fields = line.split(SEPARATOR);
                int i = 0;
                List<Double> row=new LinkedList<>();
                for (String el : fields) {
                    if (i == numCampos-1) {
                        RowWithLabel newRow=new RowWithLabel(row,el);
                        tableWithLabels.addRow(newRow);
                    } else {
                        row.add(Double.parseDouble(el));
                    }
                    i++;
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } finally {
            try {
                if (null != br) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tableWithLabels;
    }
}
