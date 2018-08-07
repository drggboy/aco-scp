package eu.andredick.aco.ant;

import eu.andredick.scp.Solution;

/**
 * <b>Abstrakte Klasse für eine Ameise.</b><br>
 * <br>
 * Definiert primär Schnittstellen, die von der Komponente MasterProzess (siehe {@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) verwendet werden.<br>
 * Entwurfsmuster Fassade, in dem Methoden mehrerer Klassen zentralisiert durch eine Schnittstelle angeboten werden.<br>
 * Weil alle Methoden abstrakt sind, dient diese Klassse zur Schnittstellendefinition und kann als Java-Interface realisiert sein.<br>
 * <p><img src="{@docRoot}/images/ACOAnt.svg" alt=""></p>
 */
public abstract class AbstractAnt {

    /**
     * Startet die Konstrukton einer Ameisenlösung.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     * Die von der Ameise erstellte Lösung soll als Objektvariable vorgehalten werden.
     */
    public abstract void constructSolution();

    /**
     * Startet die Verbesserung der konstruierten Ameisenlösung durch Lokale Suche.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     * Die lokal verbesserte Lösung soll als Objektvariable vorgehalten werden.
     */
    public abstract void localSearch();

    /**
     * Liefert den Zielfunktionswert der Ameisenlösung.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     *
     * @return Zielfunktionswert der Ameisenlösung
     */
    public abstract Float evaluateSolution();

    /**
     * Startet die Markierung der Ameisen-Lösung auf den Entitäten des Problems.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     */
    public abstract void markPheromone();

    /**
     * Erneuert den Zustand der Ameise für die nächste Iteration.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     */
    public abstract void resetAnt();

    /**
     * Liefert die Lösung einer Ameise.<br>
     * Je nach Zustand der Ameise kann es sich um die gefundene, verbesserte oder leere Lösung handeln.<br>
     * Abhängig von der Implementierung kann auch unvollständige Lösung geliefert werden.
     *
     * @return Lösung einer Ameise
     */
    public abstract Solution getSolution();

    /**
     * Setzt eine Lösung für die Ameise.<br>
     * Die Methode dient dazu, die Lösung einer Ameise mit einer neuen zu überschreiben.
     * @param solution neue Lösung
     */
    public abstract void setSolution(Solution solution);
}
