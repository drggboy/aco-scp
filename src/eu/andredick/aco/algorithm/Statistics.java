package eu.andredick.aco.algorithm;

import eu.andredick.scp.Solution;

import java.util.ArrayList;

/**
 * In jedem Masterprozess, wird ein Objekt der Klasse Statistics erzeugt,
 * welches Daten über die Berechnungsergebnisse entlang des Iterationsverlaufes sammelt.
 */
public class Statistics {

    // Liste der minimalen z-Werte in jeder Iteration
    private ArrayList<Float> iterationMinValues;

    // Liste der maximalen z-Werte in jeder Iteration
    private ArrayList<Float> iterationMaxValues;

    // Niedrigster z-Wert aller Iterationen
    private Float globalMinValue;

    // Größter z-Wert aller Iterationen
    private Float globalMaxValue;

    // Lösung des niedrigsten z-Wertes aller Iterationen
    private Solution globalMinSolution;

    // Konstruktor
    public Statistics() {
        this.iterationMinValues = new ArrayList<>(100);
        this.iterationMaxValues = new ArrayList<>(100);
    }

    // Liefert niedrigsten z-Wert aller Iterationen
    public Float getGlobalMinValue() {
        return globalMinValue;
    }

    // Liefert Lösung des niedrigsten z-Wertes aller Iterationen
    public Solution getGlobalMinSolution() {
        return globalMinSolution;
    }

    // Liefert Liste der minimalen z-Werte in jeder Iteration
    public float[] getIterationMinValuesArray() {
        return listToArray(this.iterationMinValues);
    }

    // Liefert Liste der maximalen z-Werte in jeder Iteration
    public float[] getIterationMaxValuesArray() {
        return listToArray(this.iterationMaxValues);
    }

    // Schnittstellen-Methode zum Masterprozess
    // Es wird für die Statistik in jeder Iteration jeder gefundenen Zielfunktonswert mit zugehöriger Lösung übergeben.
    public void setValue(int iteration, float value, Solution solution) {
        if (this.setIterationMinValue(iteration, value, solution)) {
            this.setGlobalMinValue(value, solution);
        }
        if (this.setIterationMaxValue(iteration, value, solution)) {
            this.setGlobalMaxValue(value, solution);
        }
    }

    // Setzen des minimalen z-Wertes der Iteration und der zugehörigen Lösung
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

    // Setzen des maximalen z-Wertes der Iteration und der zugehörigen Lösung
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

    // Setzen des maximalen z-Wertes aller Iterationen
    private boolean setGlobalMaxValue(float value, Solution solution) {
        if (this.globalMaxValue == null || value > this.globalMaxValue) {
            this.globalMaxValue = value;
            return true;
        }
        return false;
    }

    // Setzen des minimalen z-Wertes aller Iterationen
    private boolean setGlobalMinValue(float value, Solution solution) {
        if (this.globalMinValue == null || value < this.globalMinValue) {
            this.globalMinValue = value;
            this.globalMinSolution = solution;
            return true;
        }
        return false;
    }

    // Umwandlung der Datenstruktur Liste in ein Array
    private float[] listToArray(ArrayList<Float> list) {
        float[] values = new float[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = list.get(i);
        }
        return values;
    }
}
