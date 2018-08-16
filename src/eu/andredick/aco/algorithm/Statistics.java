package eu.andredick.aco.algorithm;

import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.Solution;

import java.util.ArrayList;

/**
 * <b>Statistiken zum Ablauf eines Algorithmus.</b><br>
 * In jedem Masterprozess, wird ein Objekt der Klasse Statistics erzeugt,<br>
 * welches Daten über die Berechnungsergebnisse entlang des Iterationsverlaufes sammelt.<br>
 */
public class Statistics {

    /**
     * Liste der minimalen Zielfunktionswerte in jeder Iteration
     */
    private ArrayList<Float> iterationMinValues;

    /**
     * Liste der maximalen Zielfunktionswerte in jeder Iteration
     */
    private ArrayList<Float> iterationMaxValues;

    /**
     * Niedrigster Zielfunktionswert aller Iterationen
     */
    private Float globalMinValue;

    /**
     * Höchster Zielfunktionswert aller Iterationen
     */
    private Float globalMaxValue;

    /**
     * Lösung des niedrigsten Zielfunktionswertes aller Iterationen
     */
    private SCPSolution globalMinSolution;

    /**
     * Konstruktor
     */
    public Statistics() {
        this.iterationMinValues = new ArrayList<>(100);
        this.iterationMaxValues = new ArrayList<>(100);
    }

    /**
     * Liefert den niedrigsten Zielfunktionswert aller Iterationen.
     *
     * @return niedrigster Zielfunktionswert aller Iterationen
     */
    public Float getGlobalMinValue() {
        return globalMinValue;
    }

    /**
     * Liefert Lösung des niedrigsten Zielfunktionswertes aller Iterationen.
     *
     * @return Lösung des niedrigsten Zielfunktionswertes aller Iterationen
     */
    public SCPSolution getGlobalMinSolution() {
        return globalMinSolution;
    }

    /**
     * Liefert Liste der minimalen Zielfunktionswerte in jeder Iteration
     *
     * @return Liste der minimalen Zielfunktionswerte in jeder Iteration
     */
    public float[] getIterationMinValuesArray() {
        return listToArray(this.iterationMinValues);
    }

    /**
     * Liefert Liste der maximalen Zielfunktionswerte in jeder Iteration
     *
     * @return Liste der maximalen Zielfunktionswerte in jeder Iteration
     */
    public float[] getIterationMaxValuesArray() {
        return listToArray(this.iterationMaxValues);
    }

    /**
     * Methode zum Aufzeichnen der Zielfunktionswerte und ggf. zugehöriger Lösungen entlang der Iterationen.<br>
     * Für die Statistik soll in jeder Iteration jeder gefundene Zielfunktonswert mit zugehöriger Lösung übergeben werden.<br>
     * In dieser Methode werden die Listen für maximale und minimale Zielfunktionswerte aktualisiert.<br>
     * Die Methode wird durch den Masterprozess aufgerufen.<br>
     *
     * @param iteration Index der Iteration
     * @param value     Zielfunktionswert
     * @param solution  zugehöriger Lösung
     */
    public void setValue(int iteration, float value, SCPSolution solution) {
        if (this.setIterationMinValue(iteration, value, solution)) {
            this.setGlobalMinValue(value, solution);
        }
        if (this.setIterationMaxValue(iteration, value, solution)) {
            this.setGlobalMaxValue(value, solution);
        }
    }

    /**
     * Setzen des <b>minimalen</b> Zielfunktonswertes der Iteration und der zugehörigen Lösung.
     *
     * @param iteration Index der Iteration
     * @param value     Zielfunktionswert
     * @param solution  zugehöriger Lösung
     * @return Wahr, wenn das Setzen erfolgreich war
     */
    private boolean setIterationMinValue(int iteration, float value, Solution solution) {
        Float oldValue = null;
        try {
            oldValue = iterationMinValues.get(iteration);
        } catch (IndexOutOfBoundsException e) {
            iterationMinValues.add(iteration, value);
            return true;
        }

        if (oldValue == null || value < oldValue) {
            iterationMinValues.set(iteration, value);
            return true;
        }
        return false;
    }

    /**
     * Setzen des <b>maximalen</b> Zielfunktonswertes der Iteration und der zugehörigen Lösung.
     *
     * @param iteration Index der Iteration
     * @param value     Zielfunktionswert
     * @param solution  zugehöriger Lösung
     * @return Wahr, wenn das Setzen erfolgreich war
     */
    private boolean setIterationMaxValue(int iteration, float value, Solution solution) {
        Float oldValue = null;
        try {
            oldValue = iterationMaxValues.get(iteration);
        } catch (IndexOutOfBoundsException e) {
            iterationMaxValues.add(iteration, value);
            return true;
        }

        if (oldValue == null || value > oldValue) {
            iterationMaxValues.set(iteration, value);
            return true;
        }
        return false;
    }

    /**
     * Setzen des <b>maximalen</b> Zielfunktonswertes aller Iterationen
     *
     * @param value Zielfunktonswert
     * @param solution zugehöriger Lösung
     * @return Wahr, wenn das Setzen erfolgreich war
     */
    private boolean setGlobalMaxValue(float value, Solution solution) {
        if (this.globalMaxValue == null || value > this.globalMaxValue) {
            this.globalMaxValue = value;
            return true;
        }
        return false;
    }

    /**
     * Setzen des <b>minimalen</b> Zielfunktonswertes aller Iterationen
     *
     * @param value Zielfunktonswert
     * @param solution zugehöriger Lösung
     * @return Wahr, wenn das Setzen erfolgreich war
     */
    private boolean setGlobalMinValue(float value, SCPSolution solution) {
        if (this.globalMinValue == null || value < this.globalMinValue) {
            this.globalMinValue = value;
            this.globalMinSolution = solution;
            return true;
        }
        return false;
    }

    /**
     * Umwandlung der Datenstruktur Liste in ein Array
     *
     * @param list ArrayList aus Float-Elementen
     * @return Array float[]
     */
    private float[] listToArray(ArrayList<Float> list) {
        float[] values = new float[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = list.get(i);
        }
        return values;
    }
}
