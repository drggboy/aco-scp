package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.List;

/**
 * Kapitel 3.2.7	Heuristische Information
 * Eine Ausprägung der heuristischen Information Regel H_dyn
 */
public class HeuristicRuleBestSubset implements HeuristicRule {

    /**
     * Berechnet die mögliche Anzahl neuer Überdeckungen der Grundelemente
     *
     * @param solution:         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets: alle verfügbaren Alternativen
     * @param subset:           die zu bewertende Alternative
     * @return
     */
    @Override
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset) {

        List<Integer> elementsInSubset = solution.getProblem().getStructure().getElementsInSubset(subset);

        int sum = 0;
        for (Integer e : elementsInSubset) {
            if (!solution.isElementCovered(e)) sum++;
        }
        return ((float) sum);
    }
}
