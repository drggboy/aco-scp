package eu.andredick.aco.heuristic;

import eu.andredick.scp.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Kapitel 3.2.7	Heuristische Information
 * Die Klasse ist ein Behälter für unterschiedliche Heuristische Informationen,
 * die eine Ameise beid der Lösungskonsturktion verwenden soll
 */
public class HeuristicInfoSet {
    private ArrayList<HeuristicRule> rules;

    // Konstruktor
    public HeuristicInfoSet() {
        rules = new ArrayList<>();
    }

    // Fügt eine Regel der heuristischen Information hinzu
    public void addRule(HeuristicRule rule) {
        rules.add(rule);
    }

    // Leitet die Anfrage zur Bereichnung der der Werte an alle enthaltenen Heuristiken weiter,
    // bildet ein Produkt der erhaltenen Werte und liefert diesen zurück
    public float getValue(Solution solution, List<Integer> availableSubsets, Integer subset) {
        float product = 1;
        for (HeuristicRule rule : rules) {
            product *= rule.getValue(solution, availableSubsets, subset);
        }
        return product;
    }
}
