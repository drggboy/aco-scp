package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>Abstrakte Komponente der Pheromon-Markierung</b><br>
 * Kapitel 3.3.11, S. 45, Pheromon-Markierung<br>
 * <br>
 * Pheromon-Markierung auf den assoziierten Entitäten des Problems, die in der zu markierenden Lösung enthalten sind.<br>
 * <br>
 * Die Markierung der Lösungen ({@link AbstractSolution}) eines Problems ({@link eu.andredick.aco.problem.AbstractProblem}) mit Pheromon erfolgt koordiniert durch den Masterprozess ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}),
 * welcher hierzu die Ameisen ({@link eu.andredick.aco.ant.AbstractAnt}) anleitet.<br>
 * Um diese Aufgabe auszuführen, besitzen die Ameisen eine Ausprägung der Komponente Pheromon-Markierung.<br>
 * Für die zu markierende Instanz der Ameisen-Lösung {@link AbstractSolution} wird zunächst mittels der Qualitätsfunktion ({@link AbstractSolutionQuality}) die Menge des aufzutragenden Pheromons bestimmt.<br>
 * Anschließend kann mittels der Komponente Pheromon-Assoziation ({@link AbstractPheromoneAssociation}) die Konzentration des Pheromons auf den Lösungskomponenten erhöht werden.<br>
 * <br>
 * Die ableitenden Klassen dieser Komponente müssen sich bezüglich der Ausprägungen von Pheromon-Assoziation und Problemlösung festlegen.<br>
 * Der Grund hierfür ist, dass das Markieren von Entitäten (Lösungskomponenten) die Kenntniss über den Aufbau des Problems und der zugehörigen Pheromon-Assoziation erfordert.<br>
 * Umgesetzt ist diese Abhängigkeit mittels Generischer Programmierung.<br>
 *
 * <p><img src="{@docRoot}/images/PheromoneUpdate.svg" alt=""></p>
 *
 * @param <E> Ausprägung der Pheromon-Assoziation
 * @param <S> Ausprägung der zu einem Problem zugehörigen Lösung
 */
public abstract class AbstractPheromoneUpdate<E extends AbstractPheromoneAssociation, S extends AbstractSolution> {

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
     *
     * @param pheromoneStructure Pheromon-Assoziationen als generische Refferenz
     * @param solutionQuality    Gütefunktion der Lösung
     */
    public AbstractPheromoneUpdate(E pheromoneStructure, AbstractSolutionQuality solutionQuality) {
        this.pheromoneStructure = pheromoneStructure;
        this.solutionQuality = solutionQuality;
    }

    /**
     * Markiert in Abhängigkeit der Lösung die assoziirten Entitäten des Problems mit Pheromon.
     *
     * @param solution: Lösung bzw. Ameisen-Pfad, die/der mit Pheromon markiert werden soll.
     */
    public abstract void update(S solution);
}
