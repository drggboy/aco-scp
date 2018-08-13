package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromonassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.Solution;

/**
 * Kapitel 3.3.11 <br>
 * Pheromon-Markierung auf den assoziierten Entitäten des Problems, die in der zu markierenden Lösung enthalten sind. <br>
 * @param <E>: Ausprägung der Pheromon-Assoziation
 */
public abstract class AbstractPheromoneUpdate<E extends AbstractPheromoneAssociation> {

    /**
     * Pheromon-Assoziationen als generische Refferenz
     */
    protected E pheromoneStructure;
    /**
     * Gütefunktion der Lösung
     */
    protected AbstractSolutionQuality solutionQuality;

    /**
     * Konstruktor
     * @param pheromoneStructure: Pheromon-Assoziationen als generische Refferenz.
     * @param solutionQuality: Gütefunktion der Lösung.q
     */
    public AbstractPheromoneUpdate(E pheromoneStructure, AbstractSolutionQuality solutionQuality) {
        this.pheromoneStructure = pheromoneStructure;
        this.solutionQuality = solutionQuality;
    }

    /**
     * Markiert in Abhängigkeit der Lösung die assoziirten Entitäten des Problems mit Pheromon.
     * @param solution: Lösung bzw. Ameisen-Pfad, die/der mit Pheromon markiert werden soll.
     */
    public abstract void update(Solution solution);
}
