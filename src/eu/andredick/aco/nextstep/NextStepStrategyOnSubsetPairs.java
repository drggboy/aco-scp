package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>Stochastische / probabilistische Alternativen-Auswahl mit Pheromonassoziation auf </b>  - Ausprägung der Komponente der Alternativenauswahl<br>
 * Kapitel 3.3.5, S. 32, Alternativenauswahl<br>
 * <br>
 * Die Stochastische Alternativenauswahl bestimmt aus gegebener Alternativen-Menge eine Auswahl mittels einer Zufallszahl {@code 0 <= z <= 1}.
 * Das Interval {@code [0.0, 1.0]} wird dazu in genau so viele Bereiche unterteilt, wie es Alternativen gibt.
 * Die Größe jedes Bereiches wird durch den Wert der zugehörigen Alternative bestimmt.<br>
 * Die Komponente besitzt keine Parameter.<br>
 * <br>
 * Die Komponente Alternativenauswahl wird von Konstruktionsheuristik {@link eu.andredick.aco.construct.AbstractConstructionStrategy}
 * verwendet, um aus der Menge gegebener Alternativen (Lösungskomponenten) eine Alternative auszuwählen.<br>
 * Die Auswahl der Alternative stützt sich auf den heuristischen Informationen {@link HeuristicInfoSet} und
 * den wahrgenommenen Pheromonkonzentrationen {@link AbstractPheromonePerception}, die den Alternativen zugeordnet sind oder für diese berechnet werden.<br>
 * Mittels der Kombinationsfunktion {@link CombinationRule} wird aus heuristischen Informationen und der wahrgenommenen Pheromonkonzentration ein Wert der Alternative gebildet.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsetPairs extends
        AbstractNextStepStrategy<PheromoneOnSubsetPairs, SCPSolution> {

    /**
     * Konsturktor
     *
     * @param pheromonesStructure Pheromonassoziation
     * @param perceptionRule      Pheromon-Wahrnehmung
     * @param heuristics          heuristische Informationen
     * @param combinationRule     Kombinationsfunktion
     */
    public NextStepStrategyOnSubsetPairs(PheromoneOnSubsetPairs pheromonesStructure,
                                         AbstractPheromonePerception perceptionRule,
                                         HeuristicInfoSet heuristics,
                                         CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * Die Stochastische Alternativenauswahl bestimmt aus gegebener Alternativen-Menge eine Auswahl mittels einer Zufallszahl {@code 0 <= z <= 1}.
     * Das Interval {@code [0.0, 1.0]} wird dazu in genau so viele Bereiche unterteilt, wie es Alternativen gibt.
     * Die Größe jedes Bereiches wird durch den Wert der zugehörigen Alternative bestimmt.<br>
     * <br>
     * Bei der Assoziation des Pheromons mit Teilmengenpaaren (siehe {@link PheromoneOnSubsetPairs})
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return Ergebnis der Auswahl
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        // Wenn
        List<Integer> varsList = solution.getSubsets();
        boolean firstSubsetInSolution = varsList.isEmpty();
        Integer lastSubset = firstSubsetInSolution ? null : solution.getSubsets().get(solution.getSubsets().size() - 1);

        // Ein Glücksrad mit unterschiedlich großen Abschnitten (im übertagenen Sinne)
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
