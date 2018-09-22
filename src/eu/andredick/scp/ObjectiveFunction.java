package eu.andredick.scp;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * <b>Zielfunktion des Set Covering Problems</b><br>
 * Die Zielfunktion des SCP ist die Summe aller gewichteten Entscheidungsvariablen.<br>
 * Kapitel 2.3, S. 17, Set Covering Problem (SCP). <br>
 * <br>
 * Die Gewichte (auch Kosten, Koeffizienten) c_j sind eindeutig den Teilmengen j des SCP zugeordnet.<br>
 * Die Entscheidungsvariablen sind explizit in der Lösung des SCP ({@link SCPSolution}) festgelegt.<br>
 * Die Zielfunktion besteht damit aus Gewichten ({@link #weights}) und einer Vorschrift ({@link #getValue(SCPSolution)}),<br>
 * welche mittels der Gewichte ({@link #weights}) und den Ausprägungen der Entscheidungsvariablen ({@link SCPSolution}) den Zielfunktionswert berechnet.<br>
 *
 * <p><img src="{@docRoot}/images/SCP.svg" alt=""></p>
 */
public class ObjectiveFunction {

    /**
     * Gewichte (auch Kosten, Koeffizienten)<br>
     * Der Index j des Gewichtes c_j entspricht dem Index der Entscheidungsvariable x_j und damit der Teilmenge des SCP<br>
     */
    private float[] weights;

    /**
     * Konstruktor mit Iniziirung zufälliger Gewichte
     *
     * @param size Anzahl der Entscheidungsvariablen = Anzahl der Teilmengen des SCP
     */
    public ObjectiveFunction(int size) {
        this.initRandomWeights(size);
    }

    /**
     * Konstruktor mit Übernahme gegebener Gewichte
     *
     * @param weights Gewichte
     */
    public ObjectiveFunction(float[] weights) {
        this.weights = weights;
    }


    /**
     * Liefert den Zielfunktionswert einer Lösung des SCP ({@link SCPSolution})<br>
     * Der Zielfunktionswert ist als Summme der gewichteten Entscheidungsvariablen definiert.<br>
     * @param solution Instanz einer Lösung des SCP
     * @return berechneter Zielfunktionswert
     */
    public float getValue(SCPSolution solution) {
        float value = 0f;
        boolean[] vars = solution.getVars();
        for (int j = 0; j < vars.length; j++) {
            value += (vars[j]) ? this.weights[j] : 0f;
        }
        return value;
    }

    /**
     * Liefert den Array der Gewichte
     *
     * @return Vektor der Koeffizienten
     */
    public float[] getWeights() {
        return weights;
    }


    /**
     * Initiiert zufällige Koeffizienten
     *
     * @param size Anzahl der Entscheidungsvariablen
     */
    private void initRandomWeights(int size) {
        Random r = new Random();
        this.weights = new float[size];
        for (int j = 0; j < size; j++) {
            this.weights[j] = r.nextFloat();
        }
    }


    /**
     * Drucken
     */
    public void print() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Weights:");
        for (int j = 0; j < this.weights.length; j++) {
            String value = df.format(this.weights[j]);
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
