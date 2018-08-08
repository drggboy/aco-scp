package eu.andredick.aco.heuristic;

import eu.andredick.scp.ObjectiveFunction;
import eu.andredick.scp.Solution;

import java.util.List;

/**
 * <b>Ausprägung der Komponente der Heuristischen Informationen</b><br>
 * Kapitel 3.2.7 Heuristische Information<br>
 * <br>
 * Eine Ausprägung der heuristischen Information <b>H_stat</b><br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicRuleWeights implements HeuristicRule {

    /**
     * Bestimmt die Kosten der Teilmenge <i>subset</i>.<br>
     * Die Kosten der Teilmengen sind durch den Kostenvektor der Zielfunktion des SCP definiert.
     * @see ObjectiveFunction#getWeights()
     *
     * @param solution         partiale Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets alle verfügbaren Teilmengen
     * @param subset           die zu bewertende Teilmenge
     * @return Kosten der Teilmenge
     */
    @Override
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset) {

        return 1f / solution.getProblem().getObjectiveFunction().getWeights()[subset];

    }
}
