package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>Gemischte Alternativen-Auswahl</b> aus stochastischer und deterministischer Alternativenauswahl, Pheromonassoziation mit Teilmengen<br>
 * Kapitel 3.3.5, S. 32, Alternativenauswahl<br>
 * <br>
 * Die Gemischte Alternativenauswahl liefert entweder das Ergebnis der stochastischen
 * (siehe {@link NextStepStrategyOnSubsetsStochastic}) oder der deterministischen
 * Alternativenauswahl (siehe {@link NextStepStrategyOnSubsetsDeterministic}).<br>
 * Dies wird durch die Realisierung einer Zufallszahl {@code 0.0 <= q <= 1.0} bestimmt, welche in Bezug auf einen
 * festgelegten Parameter {@code q0} entweder unterhalb {@code (q < q0)} oder oberhalb {@code (q0 <= q)} liegt.<br>
 * Die Komponente besitzt also einen Parameter.<br>
 * <br>
 * Die Komponente Alternativenauswahl wird von Konstruktionsheuristik {@link eu.andredick.aco.construct.AbstractConstructionStrategy}
 * verwendet, um aus der Menge gegebener Alternativen (Lösungskomponenten) eine Alternative auszuwählen.<br>
 * Die Auswahl der Alternative stützt sich auf den heuristischen Informationen {@link HeuristicInfoSet} und
 * den wahrgenommenen Pheromonkonzentrationen {@link AbstractPheromonePerception}, die den Alternativen zugeordnet sind oder für diese berechnet werden.<br>
 * Mittels der Kombinationsfunktion {@link CombinationRule} wird aus heuristischen Informationen und der wahrgenommenen Pheromonkonzentration ein Wert der Alternative gebildet.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsets extends
        AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution> {

    /**
     * Deterministische Alternativenauswahl (Referenz auf ein eigenes Objekt)
     */
    private NextStepStrategyOnSubsetsDeterministic nextStepDeterministic;

    /**
     * Stochastische Alternativenauswahl (Referenz auf ein eigenes Objekt)
     */
    private NextStepStrategyOnSubsetsStochastic nextStepStochastic;

    /**
     * Parameter, der den jeweiligen Einfluss der stochastischen und deterministischen Alternativenauswahl bestimmt
     */
    private float q0_parameter;

    /**
     * Konsturktor
     *
     * @param pheromonesStructure Pheromonassoziation mit Teilmengen
     * @param perceptionRule      Pheromon-Wahrnehmung
     * @param heuristics          heuristische Informationen
     * @param combinationRule     Kombinationsfunktion
     * @param q0_parameter        Parameter, der den Einfluss der stochastischen und deterministischen Komponente regelt
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
     * Liefert in abhängigkeit des realisierten Wertes einer Zufallszahl {@code q} und des Parameters {@link #q0_parameter}
     * die probabilistische oder die deterministische Ergebnisse der Alternativenauswahl.
     * Zur Bestimmung einer Alternative wird eintweder die Komponente {@link #nextStepStochastic} oder {@link #nextStepDeterministic} verwendet.
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return Ergebnis der Auswahl
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        // Zufallszahl 0.0 <= q <= 1.0 erzeugen
        float q = ThreadLocalRandom.current().nextFloat();

        /* Je nach Realisierung der Zufallszahl und dem Parameterwert q0
         * erfolgt die Alternativenauswahl entweder stochastisch oder deterministisch
         */
        if (q < q0_parameter) {
            return this.nextStepStochastic.chooseSubset(solution, availableSubsets);
        } else {
            return this.nextStepDeterministic.chooseSubset(solution, availableSubsets);
        }
    }

}
