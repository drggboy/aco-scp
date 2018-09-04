package eu.andredick.aco.solutionquality;

import eu.andredick.aco.problem.AbstractSolution;

/**
 * Kapitel 3.3.12 <br>
 * Abstrakte Gütefunktion für Lösungen <br>
 * Die abstrakte Komponente Gütefunktion wird von der Komponente PheromoneUpdate (Pheromon-Markierung) verwendet. <br>
 */
public abstract class AbstractSolutionQuality<S extends AbstractSolution> {

    /**
     * Liefert die Güte der Lösung
     * @param solution zu bewertende Lösung
     * @return Güte der Lösung
     */
    public abstract float getQuality(S solution);

}
