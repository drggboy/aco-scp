package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromonassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.Solution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Kapitel 3.2.5	Alternativenauswahl
 * Probabilistische Auswahl auf Paaren von Teilmengen - Ausprägung der Komponente der Alternativenauswahl
 * Wird von Konstruktionsheuristik verwendet, um die Alternativenauswahl zu treffen
 */
public class NextStepRuleOnSubsetPairs extends
        AbstractNextStepRule<PheromoneOnSubsetPairs> {

    public NextStepRuleOnSubsetPairs(PheromoneOnSubsetPairs pheromonesStructure,
                                     AbstractPheromonePerception perceptionRule,
                                     HeuristicInfoSet heuristics,
                                     CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * @param solution:         partiale Lösung der Ameise
     * @param availableSubsets: verfügbare Alternativen
     * @return
     */
    @Override
    public Integer chooseSubset(Solution solution, List<Integer> availableSubsets) {
        List<Integer> varsList = solution.getSubsets();
        boolean firstSubsetInSolution = varsList.isEmpty();
        Integer lastSubset = firstSubsetInSolution ? null : solution.getSubsets().get(solution.getSubsets().size() - 1);

        float[] summands = new float[availableSubsets.size()];
        float sumSummands = 0f;
        for (int k = 0; k < summands.length; k++) {
            int subset_j = availableSubsets.get(k);
            int subset_i = firstSubsetInSolution ? subset_j : lastSubset;
            float ph_k = this.perceptionRule.getValue(this.pheromoneStructure.getPheromone(subset_i, subset_j));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset_j);
            float summand = this.combinationRule.combine(ph_k, hi_k);
            sumSummands += summand;
            summands[k] = summand;
        }

        float z = ThreadLocalRandom.current().nextFloat() * sumSummands;
        float sumCounter = 0f;

        for (int k = 0; k < summands.length; k++) {
            sumCounter += summands[k];
            if (z < sumCounter) {
                return availableSubsets.get(k);
            }
        }
        return null;
    }

}
