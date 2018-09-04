package eu.andredick.aco.localsearch;

import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>Abstrakte Komponente der Lokalen Suche</b><br>
 * Kapitel 3.3.9, S. 37, Lokale Suche<br>
 * <br>
 * Die Lokalen Suche wird durch die Ameise in der Klasse {@link eu.andredick.aco.ant.ACOAnt} verwendet.<br>
 * Die Lokale Suche erfolgt auf Basis der zuvor konstruierten Lösung und liefert ggf verbesserte Lösung als Ergebnis.
 * <p><img src="{@docRoot}/images/LocalSearch.svg" alt=""></p>
 */
public abstract class AbstractLocalSearchStrategy<S extends AbstractSolution> {

    /**
     * Startet die Lokale Suche anhand der Ausgangslösung und liefert eine verbesserte oder gleiche Lösung zurück.<br>
     * Schnittstelle zu der Komponente der Ameise der Klasse {@link eu.andredick.aco.ant.ACOAnt}
     *
     * @param solution Ausgangslösung
     * @return Verbesserte Lösung
     */
    public abstract S search(S solution);

}
