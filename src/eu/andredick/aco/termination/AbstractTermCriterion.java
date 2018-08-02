package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * Abstraktes Abbruchkriterium für die Iteration des ACO.<br>
 * Subklassen konkretisieren die Bedingungen für den Abbruch.<br>
 * Eine Instanz der Subklasse wird im Masterprozess benutzt.
 */
public abstract class AbstractTermCriterion {

    /**
     * Prüfen, ob die Abbruchbedingung erfüllt ist
     * @param iteration: Zähler der Iterationen
     * @param statistics: Statistiken zum Verlauf der Iteration
     * @return: Wahr, wenn Abbruchbedingungen NICHT erfüllt
     */
    public abstract boolean checkTermination(int iteration, Statistics statistics);

}
