package eu.andredick.aco.localsearch;

import eu.andredick.scp.SCPSolution;

/**
 * <b>Dummy der Komponente der Lokale Suche - Liefert Ausgangslösung zurück</b><br>
 * Kapitel 3.3.9, S. 37, Lokale Suche<br>
 * <br>
 * Wird verwendet, wenn keine Lokale Suche durchgeführt werden soll.<br>
 * Die Lokale Suche wird durch die Ameise in der Klasse {@link eu.andredick.aco.ant.ACOAnt} verwendet.<br>
 * Die Lokale Suche erfolgt auf Basis der zuvor konstruierten Lösung und liefert ggf verbesserte Lösung als Ergebnis.<br>
 * <p><img src="{@docRoot}/images/LocalSearch.svg" alt=""></p>
 */
public class LocalSearchStrategyNone extends AbstractLocalSearchStrategy<SCPSolution> {


    /**
     * Starten der Lokalen Suche mit der Ausgangslösung, sofortige Rückgabe der empfangenen Lösung
     *
     * @param solution Ausgangslösung
     * @return selbige Ausgangslösung
     */
    public SCPSolution search(SCPSolution solution) {
        return solution;
    }
}
