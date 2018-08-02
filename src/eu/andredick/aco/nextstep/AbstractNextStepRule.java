package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.Solution;

import java.util.List;

/**
 * Kapitel 3.2.5	Alternativenauswahl
 * Abstrakte Komponente der Alternativenauswahl
 * Wird von Konstruktionsheuristik verwendet, um die Alternativenauswahl zu treffen
 */
public abstract class AbstractNextStepRule<E extends AbstractPheromone> {

    protected E pheromoneStructure;
    protected AbstractPheromonePerception perceptionRule;
    protected HeuristicInfoSet heuristics;
    protected CombinationRule combinationRule;


    /**
     * Konsturktor
     *
     * @param pheromoneStructure: Pheromonassoziation
     * @param perceptionRule:     Pheromon-Wahrnehmung
     * @param heuristics:         heuristische Informationen
     * @param combinationRule:    Kombinationsfunktion
     */
    public AbstractNextStepRule(E pheromoneStructure,
                                AbstractPheromonePerception perceptionRule,
                                HeuristicInfoSet heuristics,
                                CombinationRule combinationRule) {

        this.pheromoneStructure = pheromoneStructure;
        this.perceptionRule = perceptionRule;
        this.heuristics = heuristics;
        this.combinationRule = combinationRule;
    }

    /**
     * Schnittstelle zur Konstruktionsheuristic (AbstractConstructionStrategy)
     *
     * @param solution:         partiale Lösung der Ameise
     * @param availableSubsets: verfügbare Alternativen
     * @return: gewählte Alternative
     */
    public abstract Integer chooseSubset(Solution solution, List<Integer> availableSubsets);
}
