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
 * Gemischte Auswahl - Ausprägung der Komponente der Alternativenauswahl
 * Wird von Konstruktionsheuristik verwendet, um die Alternativenauswahl zu treffen
 */
public class NextStepStrategyOnSubsets extends
        AbstractNextStepStrategy<PheromoneOnSubsets> {

    private NextStepStrategyOnSubsetsDeterministic nextStepDeterministic;
    private NextStepStrategyOnSubsetsStochastic nextStepStochastic;
    private float q0_parameter;

    /**
     * Konsturktor
     *
     * @param pheromonesStructure Pheromonassoziation
     * @param perceptionRule     Pheromon-Wahrnehmung
     * @param heuristics         heuristische Informationen
     * @param combinationRule    Kombinationsfunktion
     * @param q0_parameter       Parameter, der den Einfluss der probabilistischen und deterministischen Komponente regelt
     */
    public NextStepStrategyOnSubsets(PheromoneOnSubsets pheromonesStructure,
                                     AbstractPheromonePerception perceptionRule,
                                     HeuristicInfoSet heuristics,
                                     CombinationRule combinationRule,
                                     float q0_parameter) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);

        this.q0_parameter = q0_parameter;

        this.nextStepDeterministic =
                new NextStepStrategyOnSubsetsDeterministic(pheromonesStructure, perceptionRule, heuristics, combinationRule);

        this.nextStepStochastic =
                new NextStepStrategyOnSubsetsStochastic(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * Liefert in abhängigkeit des realisierten Wertes einer Zufallszahl und des Parameters q0
     * die probabilistische oder die deterministische Ergebnisse der Alternativenauswahl
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return Ergebnis der Auswahl
     */
    @Override
    public Integer chooseSubset(Solution solution, List<Integer> availableSubsets) {

        float q = ThreadLocalRandom.current().nextFloat();

        if (q < q0_parameter) {
            return this.nextStepStochastic.chooseSubset(solution, availableSubsets);
        } else {
            return this.nextStepDeterministic.chooseSubset(solution, availableSubsets);
        }
    }

}
