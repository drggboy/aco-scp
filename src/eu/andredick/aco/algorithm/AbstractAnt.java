package eu.andredick.aco.algorithm;

import eu.andredick.scp.Solution;

/**
 * Anstrakte Klasse für eine Ameise. <br>
 * Definiert Schnittstellen die primär von der Komponente "masterprocess" verwendet werden. <br>
 * Entwurfsmuster Fassade.
 */
public abstract class AbstractAnt {

    /**
     * Startet die Konstrukton einer Ameisenlösung. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     * Die von der Ameise erstellte Lösung soll als Objektvariable vorgehalten werden.
     */
    public abstract void constructSolution();

    /**
     * Startet die Konstrukton einer Ameisenlösung. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     * Die lokal verbesserte Lösung soll als Objektvariable vorgehalten werden.
     */
    public abstract void localSearch();

    /**
     * Liefert den Zielfunktionswert der Ameisenlösung. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     *
     * @return Zielfunktionswert der Ameisenlösung
     */
    public abstract float evaluateSolution();

    /**
     * Startet die Markierung der Ameisen-Lösung auf den Entitäten des Problems. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     */
    public abstract void markPheromone();

    /**
     * Erneuert den Zustand der Ameise für die nächste Iteration. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     */
    public abstract void resetAnt();

    /**
     * Liefert die Lösung einer Ameise. <br>
     * Je nach Zustand der Ameise kann es sich um die gefundene, verbesserte oder leere Lösung handeln.<br>
     * Abhängig von der Implementierung kann auch unvollständige Lösung geliefert werden.
     *
     * @return Lösung einer Ameise
     */
    public abstract Solution getSolution();

    /**
     * Setzt eine Lösung für die Ameise. <br>
     * Die Methode dient dazu, die Lösung einer Ameise mit einer neuen zu überschreiben.
     * @param solution neue Lösung
     */
    public abstract void setSolution(Solution solution);
}
