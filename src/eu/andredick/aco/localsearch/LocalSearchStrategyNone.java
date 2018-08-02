package eu.andredick.aco.localsearch;

import eu.andredick.scp.Solution;

/**
 * Kapitel 3.2.9	Lokale Suche
 * Triviale Ausprägung der Komponente der Lokalen Suche,
 * wenn keine Lokale Suche erfolgen soll.
 */
public class LocalSearchStrategyNone extends AbstractLocalSearchStrategy {

    /**
     * Starten der Lokalen Suche mit der Ausgangslösung,
     * sofortige Rückgabe der empfangenen Lösung
     */
    public Solution search(Solution solution) {
        return solution;
    }
}
