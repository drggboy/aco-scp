package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.List;

/**
 * Kapitel 3.2.7	Heuristische Information
 * Eine Ausprägung der heuristischen Information Regel H_stat
 */
public class HeuristicRuleWeights implements HeuristicRule {

    /**
     * Bestimmt die Kosten der Teilmenge
     *
     * @param solution:         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets: alle verfügbaren Alternativen
     * @param subset:           die zu bewertende Alternative
     * @return
     */
    @Override
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset) {

        return 1f / solution.getProblem().getObjectiveFunction().getWeights()[subset];

    }
}
