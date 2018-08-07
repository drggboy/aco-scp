package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * Abbruchkriterium für die Iteration des ACO.<br>
 * Subklassen konkretisiert die Bedingungen für den Abbruch.<br>
 * Eine Instanz der Subklasse wird im Masterprozess benutzt.
 */
public class TermCriterion extends AbstractTermCriterion {

    /**
     * Maximale Anzahl der Itarationen
     */
    private int maxIterations;

    /**
     * Konstruktor
     * @param maxIterations Maximale Anzahl der Itarationen
     */
    public TermCriterion(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * Prüfen, ob die Abbruchbedingung erfüllt ist.<br>
     * Abbruchbedingung ist erfüllt, wenn Anzahl der Iterationen ein Maximum überschreitet.
     * @param iteration: Zähler der Iterationen
     * @param statistics: Statistiken zum Verlauf der Iteration
     * @return Wahr, wenn Anzahl der Iterationen NICHT über Maximum
     */
    @Override
    public boolean checkTermination(int iteration, Statistics statistics) {
        return iteration < maxIterations;
    }
}
