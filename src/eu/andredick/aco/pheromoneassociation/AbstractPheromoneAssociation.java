package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.problem.AbstractProblem;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;

/**
 * <b>Abstrakte Komponente der Pheromonassoziation</b><br>
 * Kapitel 3.3.1, S. 24, Pheromon-Assoziation<br>
 * <br>
 * Das mit den Entitäten eines konkreten Problems assoziierte Pheromon kann als ein Layer betrachtet werden,<br>
 * welchem bestimmte Regeln (Pheromon-Evaporation und Pheromon-Initiierung) zugeordnet sind.<br>
 * Die Pheromonassoziation ordnet den Entitäten eines konkreten Problems Pheromonkonzentrationen zu.<br>
 * Daher ist jede Ausprägung der Pheromonassoziation einer bestimmten Ausprägung eines Problems zugewiesen.<br>
 * Diese Abhängigkeit wird durch die Generische Programmierung {@code <P extends AbstractProblem>} realisiert.<br>
 * Bei der Ableitung ist damit festzulegen, mit welchem konkreten Problem die Pheromonassoziation besteht.<br>
 * <br>
 * Die Pheromonassoziation wird in folgenden Komponenten benötigt:
 * <ul>
 * <li>Pheromon-Initiierung {@link AbstractPheromoneInit}</li>
 * <li>Pheromon-Evaporation {@link AbstractPheromoneEvaporation}</li>
 * <li>Alternativen-Auswahl {@link AbstractNextStepStrategy}</li>
 * <li>Pheromon-Markierung  {@link AbstractPheromoneUpdate}</li>
 * </ul>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneAssociation-a.svg" alt=""></p>
 * <br>
 *
 * @param <P> Konkretisierte Klasse des Problems
 */
public abstract class AbstractPheromoneAssociation<P extends AbstractProblem> {

    /**
     * Ausprägung eines Problems, dessen Entitäten mit Pheromon assoziiert sind
     */
    protected P problem;

    /**
     * Ausprägung der Pheromon-Evaporation
     */
    protected AbstractPheromoneEvaporation evaporationRule;

    /**
     * Ausprägung der Pheromon-Initiierung
     */
    protected AbstractPheromoneInit pheromoneInitRule;

    /**
     * Konstruktor
     *
     * @param problem Das Problem, auf dessen Entitäten Pheromon abgelagert werden soll (Instanz)
     */
    public AbstractPheromoneAssociation(P problem) {
        this.problem = problem;
    }

    /**
     * Startet einen Zeitschritt der Evaporation des gesamten Pheromons.<br>
     * Alle Pheromon-Konzentrationen sollen mittels der Evaporations-Regel {@link #evaporationRule} angepasst werden.<br>
     *
     */
    public abstract void evaporatePheromones();

    /**
     * Initiiert die Pheromon-Konszentrationen für alle Entitäten des Problems mittels der Regel für die Initiierung {@link #pheromoneInitRule}.<br>
     * Diese Methode stellt den Anfangszustand des gesamten Pheromons her, welcher beim Start des ACO-Algorithmus bestehen soll.
     */
    public abstract void initPheromones();

    /**
     * Liefert die mit der Entität j des Problems assoziierte Pheromon-Konzentration
     *
     * @param j Index der Entität des Problems
     * @return Pheromonkonzentration, die mit der Entität j des Problems assoziiert ist
     */
    public abstract float getPheromone(int j);


    /**
     * Fügt zusätzlichen Pheromon zum Bestehenden hinzu.<br>
     * Diese Methode wird in der Komponente Pheromon-Markierung ({@link AbstractPheromoneUpdate}) verwendet.
     *
     * @param j        Index der Entität des Problems.
     * @param ph_delta Zugabe der Pheromon-Konzentration
     */
    public abstract void addPheromone(int j, float ph_delta);

    /**
     * Setter-Methode für Ausprägung der Pheromon-Evaporation
     * @param evaporationRule Pheromon-Evaporation
     */
    public void setEvaporationRule(AbstractPheromoneEvaporation evaporationRule) {
        this.evaporationRule = evaporationRule;
    }

    /**
     * Setter-Methode für Ausprägung der Pheromon-Initiierung
     * @param pheromoneInitRule Pheromon-Initiierung
     */
    public void setPheromoneInitRule(AbstractPheromoneInit pheromoneInitRule) {
        this.pheromoneInitRule = pheromoneInitRule;
    }


}