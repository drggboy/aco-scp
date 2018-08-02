package eu.andredick.aco.localsearch;

import eu.andredick.scp.Solution;

/**
 * Kapitel 3.2.9	Lokale Suche
 * Abstrakte Komponente der Lokalen Suche
 */
public abstract class AbstractLocalSearchStrategy {

    /**
     * Start der Lokalen Suche
     * Schnittstelle zu der Komponente ACOAnt
     *
     * @param solution: Ausgangslösung
     * @return: Verbesserte Lösung
     */
    public abstract Solution search(Solution solution);

}
