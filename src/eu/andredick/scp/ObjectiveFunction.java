package eu.andredick.scp;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Die Klasse repräsentiert die Zielfunktion des Set Covering Problems.
 * Die Zielfunktion wird als ein Vektor von Kosten vorgehalten.
 */
public class ObjectiveFunction {

    /**
     * Kosten bzw. Gewicht bzw. Koeffizienten
     */
    private float[] weights;

    /**
     * Konstruktor mit Iniziirung zufälliger Koeffizienten
     *
     * @param size Anzahl der Entscheidungsvariablen = Anzahl der Teilmengen des SCP
     */
    public ObjectiveFunction(int size) {
        this.initRandomWeights(size);
    }

    /**
     * Konstruktor mit Übernahme der Koeffizienten
     *
     * @param weights´Koeffizienten
     */
    public ObjectiveFunction(float[] weights) {
        this.weights = weights;
    }


    /**
     * Liefert den Zielfunktionswert einer SCP-Lösung
     *
     * @param solution SCP-Lösung
     * @return Zielfunktionswert
     */
    public float getValue(Solution solution) {
        float value = 0f;
        boolean[] vars = solution.getVars();
        for (int j = 0; j < vars.length; j++) {
            value += (vars[j]) ? this.weights[j] : 0f;
        }
        return value;
    }

    /**
     * Liefert den Vektor der Koeffizienten
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
     * Initiiert Koeffizienten mit dem Wert 1
     *
     * @param size Anzahl der Entscheidungsvariablen
     */
    public void initUniWeights(int size) {
        this.weights = new float[size];
        for (int j = 0; j < size; j++) {
            this.weights[j] = 1f;
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
