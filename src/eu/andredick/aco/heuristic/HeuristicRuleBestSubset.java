package eu.andredick.aco.heuristic;

import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>Ausprägung der Komponente der Heuristischen Informationen</b><br>
 * Kapitel 3.3.7, S. 34, Heuristische Information<br>
 * <br>
 * Eine Ausprägung der heuristischen Information  <b>H_dyn</b><br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicRuleBestSubset implements HeuristicRule<SCPSolution> {

    /**
     * Berechnet die Anzahl möglicher neuer Überdeckungen der Grundelemente durch die Auswahl der Teilmenge.<br>
     * Dazu wird für jedes Grundelement, welches durch die Teilmenge <i>subset</i> überdeckt wird, geprüft,<br>
     * ob dieses bereits durch die Teilmengen aus der partialen Lösung überdeckt ist.<br>
     * Die Summe aller Grundelemente, für die dies nicht zutrifft, ist der Wert der Heuristischen Information.
     *
     * @param solution:         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets alle verfügbaren Teilmengen
     * @param subset           die zu bewertende Teilmenge
     * @return Summe der möglichen Neuüberdeckungen
     */
    @Override
    public float getValue(SCPSolution solution, List<Integer> availableSubsets, Integer subset) {

        // Bestimmen der Grundelemente, welche in der Teilmenge "subset" enthalten sind
        List<Integer> elementsInSubset = solution.getProblem().getStructure().getElementsInSubset(subset);

        // Initiieren des Zählers "sum"
        int sum = 0;

        // Iteration über alle vorab bestimmten Grundelemente
        for (Integer e : elementsInSubset) {

            // Wenn das aktuelle Grundelement "e" nicht bereits durch die partiale Lösung überdeckt ist,
            // dann inkrementiere den Zähler "sum"
            if (!solution.isElementCovered(e)) sum++;
        }

        // Rückgabe der Summe der möglichen Neuüberdeckungen
        return ((float) sum);
    }
}
