package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.List;

/**
 * <b>Schnittstelle für Klassen der Heuristischen Information</b><br>
 * Kapitel 3.2.7 Heuristische Information<br>
 * <br>
 * Ausprägungen der Komponente der Heuristische Informationen müssen diese Schnittstelle implemenntieren.<br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 */
public interface HeuristicRule {

    /**
     * Liefert den Wert der Heuristischen Informationen
     *
     * @param solution         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets alle verfügbaren Alternativen
     * @param subset           Alternative, für die die Heuristischen Informationen geliefert werden sollen
     * @return Wert der Heuristischen Informationen
     */
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset);

}
