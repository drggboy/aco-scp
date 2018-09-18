package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;

/**
 * <b>Ausprägung der Komponente der Pheromonassoziation</b>j, Assoziation mit Paaren von Teilmengen des SCP ({@link SCProblem}).<br>
 * Kapitel 3.3.1, S. 24, Pheromon-Assoziation<br>
 * <br>
 * Bei dieser Ausprägung ist das Pheromon mit Paaren von Teilmengen des SCP (Entität des Problems) assoziiert.<br>
 * Ein Paar von Teilmengen des SCP kann durch Ameise mit Pheromon markiert werden,<br>
 * wenn beide Teilmengen in der konstruierten Lösung der Ameise enthalten sind.<br>
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
 * <li>Masterprozess {@link AbstractMasterProcess}</li>
 * <li>Pheromon-Initiierung {@link AbstractPheromoneInit}</li>
 * <li>Pheromon-Evaporation {@link AbstractPheromoneEvaporation}</li>
 * <li>Alternativen-Auswahl {@link AbstractNextStepStrategy}</li>
 * <li>Pheromon-Markierung  {@link AbstractPheromoneUpdate}</li>
 * </ul>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneAssociation-a.svg" alt=""></p>
 * <br>
 */
public class PheromoneOnSubsetPairs extends AbstractPheromoneAssociation<SCProblem> {

    /**
     * Konzentrationen des Pheromons auf Paaren von Teilmengen des SCP, repräsentiert durch ein zweidimensionales symetrisches Array (Matrix).<br>
     * Die Indizes i, j des Arrays entsprchen den Indizes der zwei Teilmengen des SCP (eindeutige Zuordnung).<br>
     */
    private float[][] pheromoneValues;

    /**
     * Konstruktor
     *
     * @param problem Instanz des Set Covering Problem
     */
    public PheromoneOnSubsetPairs(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()][problem.getStructure().subsetsSize()];
    }

    /**
     * Startet einen Zeitschritt der Evaporation des gesamten Pheromons.<br>
     * Alle Pheromon-Konzentrationen werden mittels der Evaporations-Regel {@link #evaporationRule} angepasst.<br>
     */
    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.evaporationRule.evaporate(pheromoneValues[i][j]);
            }
        }
    }

    /**
     * Initiiert die Pheromon-Konszentrationen für alle Teilmengen des SCP mittels der Regel für die Initiierung {@link #pheromoneInitRule}.<br>
     * Diese Methode stellt den Anfangszustand des gesamten Pheromons her, welcher beim Start des ACO-Algorithmus bestehen soll.
     */
    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.pheromoneInitRule.initValue();
            }
        }
    }

    /**
     * Liefert immer 0.
     * Diese Methode ist geerbt, kann jedoch nicht verwendet werden, weil nur ein Parameter übergeben werden kann.
     * Benötigt werden zwei Parameter für die beiden Indizes i,j
     *
     * @param j Index der Entität des Problems
     * @return 0, unabhängig von der Eingabe
     */
    @Override
    public float getPheromone(int j) {
        System.out.println("PheromoneOnSubsetPairs.getPheromone / nicht erlaubt!");
        return 0;
    }

    /**
     * Liefert die mit dem Teilmengen-Paar (i,j) des SCP-Problems assoziierte Pheromon-Konzentration
     * @param i Index der ersten Teilmenge des Paares
     * @param j Index der zweiten Teilmenge des Paares
     * @return Pheromon-Konzentration, die mit dem Teilmengen-Paar (i,j) assoziiert ist
     */
    public float getPheromone(int i, int j) {
        return this.pheromoneValues[i][j];
    }

    /**
     * Diese Methode ist geerbt, kann jedoch nicht verwendet werden, weil der Index nur einer Teilmenge als Eingabe übergeben wird.<br>
     * Keine Änderung des Zustandes der Pheromon-Assoziation unabhängig von der Eingabe.
     * @param j        Index der Entität des Problems.
     * @param ph_delta Zugabe der Pheromon-Konzentration
     */
    @Override
    public void addPheromone(int j, float ph_delta) {
        System.out.println("PheromoneOnSubsetPairs.addPheromone / nicht erlaubt!");
    }


    /**
     * Fügt zusätzlichen Pheromon durch Addition zum Bestehenden hinzu.<br>
     * Diese Methode wird in Ausprägungen der Komponente Pheromon-Markierung ({@link AbstractPheromoneUpdate}) verwendet.<br>
     * Die assoziierte Pheromon-Konzentration ist durch die Indizes des Teilmengen-Paars (i,j) des SCProblems bestimmt.
     *
     * @param i Index der ersten Teilmenge des Paares
     * @param j Index der zweiten Teilmenge des Paares
     * @param ph_delta Zugabe der Pheromon-Konzentration
     */
    public void addPheromone(int i, int j, float ph_delta) {
        this.pheromoneValues[i][j] += ph_delta;
    }

}
