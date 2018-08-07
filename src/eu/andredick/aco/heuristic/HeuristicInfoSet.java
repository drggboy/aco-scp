package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Eine Menge für Heuristische Informationen</b><br>
 * Kapitel 3.2.7 Heuristische Information<br>
 * <br>
 * Die Klasse ist ein Behälter für unterschiedliche Heuristische Informationen,<br>
 * die eine Ameise bei der Lösungskonsturktion verwenden soll.<br>
 * Die Komponente wird bei der Alternativenauswahl verwendet (siehe {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy}).<br>
 */
public class HeuristicInfoSet implements HeuristicRule{

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
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset) {
        float product = 1;
        for (HeuristicRule rule : rules) {
            product *= rule.getValue(solution, availableSubsets, subset);
        }
        return product;
    }
}
