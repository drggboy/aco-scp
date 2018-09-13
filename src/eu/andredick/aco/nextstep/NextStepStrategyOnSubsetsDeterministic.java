package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>Deterministische Alternativenauswahl</b> - Ausprägung der Komponente der Alternativenauswahl, Pheromonassoziation mit Teilmengen<br>
 * Kapitel 3.3.5, S. 32, Alternativenauswahl<br>
 * <br>
 * Die deterministische Alternativenauswahl liefert als Erbebnis immer die am besten bewertete Alternative.<br>
 * Die Komponente besitzt keine Parameter.<br>
 * <br>
 * Die Komponente Alternativenauswahl wird von Konstruktionsheuristik {@link eu.andredick.aco.construct.AbstractConstructionStrategy}
 * verwendet, um aus der Menge gegebener Alternativen (Lösungskomponenten) eine Alternative auszuwählen.<br>
 * Die Auswahl der Alternative stützt sich auf den heuristischen Informationen {@link HeuristicInfoSet} und
 * den wahrgenommenen Pheromonkonzentrationen {@link AbstractPheromonePerception}, die den Alternativen zugeordnet sind oder für diese berechnet werden.<br>
 * Mittels der Kombinationsfunktion {@link CombinationRule} wird aus heuristischen Informationen und der wahrgenommenen Pheromonkonzentration ein Wert der Alternative gebildet.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsetsDeterministic extends
        AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution> {

    /**
     * Konsturktor
     *
     * @param pheromonesStructure Pheromonassoziation mit Teilmengen
     * @param perceptionRule      Pheromon-Wahrnehmung
     * @param heuristics          heuristische Informationen
     * @param combinationRule     Kombinationsfunktion
     */
    public NextStepStrategyOnSubsetsDeterministic(PheromoneOnSubsets pheromonesStructure,
                                                  AbstractPheromonePerception perceptionRule,
                                                  HeuristicInfoSet heuristics,
                                                  CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * Bestimmt deterministisch die Auswahl, liefert die Alternative mit dem höchsten Wert.<br>
     * Der Wert jeweils einer Alternative wird aus der zugehörigen wahrgenommenen Pheromonkonzentration und den heuristischen Informationen bestimmt.<br>
     * Eine Kombinationsfunktion ({@link CombinationRule}) vereint beide Einflussgrößen.v<br>
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return Ergebnis der Auswahl
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        float maxValue = 0f;
        int indexWithMaxValue = -1;

        // Schleife über alle verfügbaren Alternativen, um die Alternative mit dem höchsten Wert zu bestimmen
        for (int k = 0; k < availableSubsets.size(); k++) {
            int subset = availableSubsets.get(k);
            float ph_k = this.perceptionRule.getPerceptionValue(this.pheromoneStructure.getPheromone(subset));
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
