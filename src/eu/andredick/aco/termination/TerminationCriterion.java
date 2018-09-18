package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * <b>Ausprägung des Abbruchkriterium</b> für die Iteration des ACO-Algorithmus<br>
 * <br>
 * Die Ausprägungen werden im Masterprozess verwendet, um die Iteration abzubrechen.<br>
 * Abbruchbedingung ist erfüllt, wenn Anzahl der Iterationen ein Maximum überschreitet.
 *
 * <p><img src="{@docRoot}/images/Termination.svg" alt=""></p>
 */
public class TerminationCriterion extends AbstractTerminationCriterion {

    /**
     * Maximale Anzahl der Itarationen
     */
    private int maxIterations;

    /**
     * Konstruktor
     *
     * @param maxIterations Maximale Anzahl der Itarationen
     */
    public TerminationCriterion(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * Prüfen, ob die Abbruchbedingung erfüllt ist.<br>
     * Abbruchbedingung ist erfüllt, wenn Anzahl der Iterationen ein Maximum überschreitet.
     *
     * @param iteration:  Zähler der Iterationen
     * @param statistics: Statistiken zum Verlauf der Iteration
     * @return Wahr, wenn Anzahl der Iterationen NICHT über Maximum
     */
    @Override
    public boolean checkTermination(int iteration, Statistics statistics) {
        return iteration < maxIterations;
    }
}
