package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromonassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.Solution;

import java.util.List;

/**
 * Kapitel 3.2.5	Alternativenauswahl
 * Deterministische Auswahl - Ausprägung der Komponente der Alternativenauswahl
 * Wird von Konstruktionsheuristik verwendet, um die Alternativenauswahl zu treffen
 */
public class NextStepRuleOnSubsetsDeterministic extends
        AbstractNextStepRule<PheromoneOnSubsets> {

    public NextStepRuleOnSubsetsDeterministic(PheromoneOnSubsets pheromonesStructure,
                                              AbstractPheromonePerception perceptionRule,
                                              HeuristicInfoSet heuristics,
                                              CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * Bestimmt deterministisch die Auswahl, liefert die Alternative mit dem maximalen Wert
     *
     * @param solution:         partiale Lösung der Ameise
     * @param availableSubsets: verfügbare Alternativen
     * @return
     */
    @Override
    public Integer chooseSubset(Solution solution, List<Integer> availableSubsets) {

        float maxValue = 0f;
        int indexWithMaxValue = -1;

        for (int k = 0; k < availableSubsets.size(); k++) {
            int subset = availableSubsets.get(k);
            float ph_k = this.perceptionRule.getValue(this.pheromoneStructure.getPheromone(subset));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset);
            float value = this.combinationRule.combine(ph_k, hi_k);
            if (value > maxValue) {
                maxValue = value;
                indexWithMaxValue = k;
            }
        }
        return availableSubsets.get(indexWithMaxValue);
    }

}
