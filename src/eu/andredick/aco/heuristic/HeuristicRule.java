package eu.andredick.aco.heuristic;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.List;

/**
 * <b>Schnittstelle für Klassen der Heuristischen Information</b><br>
 * Kapitel 3.3.7, S. 34, Heuristische Information<br>
 * <br>
 * Ausprägungen der Komponente der Heuristische Informationen müssen diese Schnittstelle implemenntieren.<br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public interface HeuristicRule<S extends AbstractSolution> {

    /**
     * Liefert den Wert der Heuristischen Informationen
     *
     * @param solution         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets alle verfügbaren Alternativen
     * @param subset           Alternative, für die die Heuristischen Informationen geliefert werden sollen
     * @return Wert der Heuristischen Informationen
     */
    public float getValue(S solution, List<Integer> availableSubsets, Integer subset);

}
