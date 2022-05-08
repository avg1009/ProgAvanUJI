package machineLearnig;

/**
 * @author Adolfo Jesús Viñé y Belén Ariño
 */
public class RegresionLineal implements Algorithm<Double,Double,Table> {
    private Double mediaX;
    private Double mediaY;
    protected Double alpha;
    protected Double betha;
    private Double sumatorioDividendo = 0.0;
    private Double sumatorioDivisor = 0.0;
    private Double sumX = 0.0;
    private Double sumY = 0.0;


    private Table table;
    /**
     * Calculamos tanto el alfa como el beta
     *
     * @param data
     */
    @Override
    public void train(Table data) {
        this.table = data;
        recorrer();
        mediaX = Matematicas.calculateMedia(sumX,table.row.size());
        mediaY = Matematicas.calculateMedia(sumY,table.row.size());
        sumatorios();
        calcularAlpha();
        calcularBeta();
    }

    private void recorrer() {
        for (Row r : table.row) {
            sumX += r.data.get(0);
            sumY += r.data.get(1);
        }
    }
    private void sumatorios(){
        for (Row r : table.row){
            double x = r.data.get(0);
            double y = r.data.get(1);
            sumatorioDividendo += (x - mediaX) * (y - mediaY);
            sumatorioDivisor += (x - mediaX) * (x - mediaX);
        }
    }


    private Double calcularAlpha() {
        if (sumatorioDivisor <= 0){
            throw new ArithmeticException();
        }
        alpha = sumatorioDividendo / sumatorioDivisor;
        return alpha;
    }

    private Double calcularBeta() {
        betha = mediaY - (alpha * mediaX);
        return betha;
    }

    /**
     * Calculamos una estimación
     *
     * @param x
     * @return
     */
    @Override
    public Double estimate(Double x) {

        if (x == null) {
            throw new NullPointerException();
        }
        Double estimacion = (this.alpha * x) + this.betha;
        return estimacion;
    }
}