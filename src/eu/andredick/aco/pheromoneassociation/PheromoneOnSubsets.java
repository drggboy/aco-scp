package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;

/**
 * <b>Ausprägung der Komponente der Pheromonassoziation</b>, Assoziation mit Teilmengen des SCP ({@link SCProblem}).<br>
 * Kapitel 3.3.1, S. 24, Pheromon-Assoziation<br>
 * <br>
 * Bei dieser Ausprägung sind jeder Teilmenge des SCP ein Wert der Pheromonkonzentration zugeordnet.<br>
 * Ameisen markieren in diesem Fall die in ihren Lösungen enthaltenen Teilmengen mit Pheromon.<br>
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
 */
public class PheromoneOnSubsets extends AbstractPheromoneAssociation<SCProblem> {

    /**
     * Konzentrationen des Pheromons auf Problem-Entitäten, repräsentiert durch ein Array.<br>
     * Die Indizes des Arrays entsprchen denen der Entitäten des Problems (eindeutige Zuordnung).<br>
     */
    private float[] pheromoneValues;

    /**
     * Konstruktor
     *
     * @param problem Instanz des Set Covering Problem
     */
    public PheromoneOnSubsets(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()];
    }

    /**
     * Startet einen Zeitschritt der Evaporation des gesamten Pheromons.<br>
     * Alle Pheromon-Konzentrationen werden mittels der Evaporations-Regel {@link #evaporationRule} angepasst.<br>
     */
    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.evaporationRule.evaporate(pheromoneValues[i]);
        }
    }

    /**
     * Initiiert die Pheromon-Konszentrationen für alle Teilmengen des SCP mittels der Regel für die Initiierung {@link #pheromoneInitRule}.<br>
     * Diese Methode stellt den Anfangszustand des gesamten Pheromons her, welcher beim Start des ACO-Algorithmus bestehen soll.
     */
    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.pheromoneInitRule.initValue();
        }
    }

    /**
     * Liefert die mit der Teilmenge j des Problems assoziierte Pheromon-Konzentration
     * @param j Index der Teilmenge des Problems
     * @return mit der Teilmenge j des Problems assoziierte Pheromon-Konzentration
     */
    @Override
    public float getPheromone(int j) {
        return this.pheromoneValues[j];
    }

    /**
     * Fügt zusätzlichen Pheromon durch Addition zum Bestehenden hinzu.<br>
     * Diese Methode wird in der Komponente Pheromon-Markierung ({@link AbstractPheromoneUpdate}) verwendet.
     * @param j        Index der Entität des Problems.
     * @param ph_delta Zugabe der Pheromon-Konzentration
     */
    @Override
    public void addPheromone(int j, float ph_delta) {
        this.pheromoneValues[j] += ph_delta;
    }

}
