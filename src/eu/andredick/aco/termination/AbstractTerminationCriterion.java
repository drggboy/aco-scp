package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * <b>Abstraktes Abbruchkriterium</b> für die Iteration des ACO-Algorithmus<br>
 * <br>
 * Die Ausprägungen werden im Masterprozess verwendet, um die Iteration in Abhängigkeit von zu bestimmenden Bedingungen abzubrechen.<br>
 *
 * <p><img src="{@docRoot}/images/Termination.svg" alt=""></p>
 */
public abstract class AbstractTerminationCriterion {

    /**
     * Prüfen, ob die Abbruchbedingung erfüllt ist<br>
     *
     * @param iteration  Zähler der Iterationen
     * @param statistics Statistiken zum Verlauf der Iteration
     * @return Wahr, wenn Abbruchbedingungen NICHT erfüllt
     */
    public abstract boolean checkTermination(int iteration, Statistics statistics);
}
