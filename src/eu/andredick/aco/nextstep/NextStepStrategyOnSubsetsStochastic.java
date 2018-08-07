package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromonassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.Solution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Kapitel 3.2.5	Alternativenauswahl
 * Probabilistische Auswahl - Ausprägung der Komponente der Alternativenauswahl
 * Wird von Konstruktionsheuristik verwendet, um die Alternativenauswahl zu treffen
 */
public class NextStepStrategyOnSubsetsStochastic extends
        AbstractNextStepStrategy<PheromoneOnSubsets> {

    public NextStepStrategyOnSubsetsStochastic(PheromoneOnSubsets pheromonesStructure,
                                               AbstractPheromonePerception perceptionRule,
                                               HeuristicInfoSet heuristics,
                                               CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * Bestimmt probabilistisch die Auswahl, liefert die Alternative mit dem maximalen Wert
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return Ergebnis der Auswahl
     */
    @Override
    public Integer chooseSubset(Solution solution, List<Integer> availableSubsets) {

        // Ein Glücksrad mit unterschiedlich großen Abschnitten (im übertagenen Sinne)
        float[] summands = new float[availableSubsets.size()];
        float sumSummands = 0f;
        for (int k = 0; k < summands.length; k++) {
            int subset = availableSubsets.get(k);
            float ph_k = this.perceptionRule.getValue(this.pheromoneStructure.getPheromone(subset));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset);
            float summand = this.combinationRule.combine(ph_k, hi_k);
            sumSummands += summand;
            summands[k] = summand;
        }

        // Alle Alternativen-Werte = null => zufällige Auswahl
        if (sumSummands == 0f)
            return availableSubsets.get(ThreadLocalRandom.current().nextInt(availableSubsets.size()));

        // Glücksrad wird gedreht
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
