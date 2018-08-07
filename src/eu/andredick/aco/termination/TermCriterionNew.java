package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.tools.ArrayTools;

/**
 * Abbruchkriterium für die Iteration des ACO.<br>
 * Subklassen konkretisiert die Bedingungen für den Abbruch.<br>
 * Eine Instanz der Subklasse wird im Masterprozess benutzt.
 */
public class TermCriterionNew extends AbstractTermCriterion {

    /**
     * Maximale Anzahl der Itarationen.
     */
    private int maxIterations;

    /**
     * Maximale Anzahl der Iterationen, seit dem keine bessere Lösunge gefunden wurde.
     */
    private int bestValueAgo;

    /**
     * Konstruktor
     *
     * @param maxIterations Maximale Anzahl der Itarationen
     * @param bestValueAgo  Maximale Anzahl der Iterationen, seit dem keine bessere Lösunge gefunden wurde.
     */
    public TermCriterionNew(int maxIterations, int bestValueAgo) {
        this.maxIterations = maxIterations;
        this.bestValueAgo = bestValueAgo;
    }

    /**
     * Prüfen, ob die Abbruchbedingung erfüllt ist.<br>
     * Abbruchbedingung ist erfüllt, wenn Anzahl der Iterationen ein Maximum überschreitet <br>
     * ODER die maximale Anzahl der Iterationen seit bester Lösunge überschritten wird.
     *
     * @param iteration  Zähler der Iterationen
     * @param statistics Statistiken zum Verlauf der Iteration
     * @return Wahr, wenn Anzahl der Iterationen NICHT über Maximum
     */
    @Override
    public boolean checkTermination(int iteration, Statistics statistics) {
        if (iteration == 0) return true;
        Float globMinVal = statistics.getGlobalMinValue();
        int iterationnOfBestFoundValue = ArrayTools.getIndexOfMinValue(statistics.getIterationMinValuesArray());
        return iteration - iterationnOfBestFoundValue < bestValueAgo && iteration < maxIterations;
    }
}
