package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.List;

/**
 * Kapitel 3.2.7	Heuristische Information
 * Schnittstelle einer Klasse der heuristischen Information
 */
public interface HeuristicRule {

    /**
     * Methode wird durch HeuristicInfoSet genutzt um den Wert der heurist. Information zu erhalten
     *
     * @param solution:         partialle Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets: alle verfügbaren Alternativen
     * @param subset:           die zu bewertende Alternative
     * @return: Wert der Alternative
     */
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset);

}
