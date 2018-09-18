package eu.andredick.aco.heuristic;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Eine Menge für Heuristische Informationen</b><br>
 * Kapitel 3.3.7, S. 34, Heuristische Information<br>
 * <br>
 * Die Klasse ist ein Behälter für unterschiedliche Heuristische Informationen,<br>
 * die eine Ameise bei der Lösungskonsturktion verwenden soll.<br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 *
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicInfoSet<S extends AbstractSolution> implements HeuristicRule<S> {

    /**
     * Menge von Ausprägungen der Komponente Heuristischer Informationen
     */
    private ArrayList<HeuristicRule> rules;

    /**
     * Konstruktor
     */
    public HeuristicInfoSet() {
        rules = new ArrayList<>();
    }

    /**
     * Fügt eine Ausprägung von Heuristischen Information hinzu
     *
     * @param rule Ausprägung von Heuristischen Information
     */
    public void addRule(HeuristicRule rule) {
        rules.add(rule);
    }

    /**
     * Leitet die Anfrage zur Bereichnung der der Werte an alle enthaltenen Heuristiken weiter,<br>
     * bildet ein Produkt der erhaltenen Werte und liefert diesen zurück.
     *
     * @param solution         partiale Lösung im Konstruktionsprozess der Ameise
     * @param availableSubsets alle verfügbaren Alternativen
     * @param subset           Alternative, für die die Heuristischen Informationen geliefert werden sollen
     * @return Wert der Heuristischen Informationen
     */
    public float getValue(S solution, List<Integer> availableSubsets, Integer subset) {
        float product = 1;
        for (HeuristicRule rule : rules) {
            product *= rule.getValue(solution, availableSubsets, subset);
        }
        return product;
    }
}
