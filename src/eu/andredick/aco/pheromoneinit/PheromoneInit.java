package eu.andredick.aco.pheromoneinit;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>Ausprägung der Komponente der Pheromon-Initialisierung</b><br>
 * Kapitel 3.3.3, S. 29, Initialisierung des Pheromons<br>
 * <br>
 * Für alle assoziierten Entitäten eines Problems wird der gleiche Startwert der Pheromon-Konzentration erzeugt.<br>
 * Der Initialwert des Pheromons stellt einen Parameter der Komponente dar.<br>
 * <br>
 * Mit der Initialisierung der Pheromonwerte wird ein Anfangszustand für nachfolgende Iteration des ACO-Algorithmus erstellt.<br>
 * Wie die Pheromon-Konzentrationen initialisiert werden, soll in den Subklassen dieser Komponente festgelegt werden.<br>
 * <br>
 * Die Pheromon-Initialisierung wird durch den Masterprozess ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) koordiniert.<br>
 * Dies geschieht, indem die Methode {@link AbstractPheromoneAssociation#initPheromones()} der Komponenten {@link AbstractPheromoneAssociation} aufgerufen wird.<br>
 * Anschließend benutzt {@link AbstractPheromoneAssociation} eine Ausprägung der Komponente Pheromon-Initialisierung zur Anpassung aller Pheromon-Konzentrationen.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneInit.svg" alt=""></p>
 */
public class PheromoneInit implements AbstractPheromoneInit {

    /**
     * Initial-Wert der Pheromon-Konzentration als Parameter der Komponente<br>
     */
    private float pheromoneInitValue;

    /**
     * Konstruktor
     *
     * @param pheromoneInitValue Initial-Wert der Pheromon-Konzentration als Parameter der Komponente
     */
    public PheromoneInit(float pheromoneInitValue) {
        this.pheromoneInitValue = pheromoneInitValue;
    }


    /**
     * Liefert den Initial-Wert der Pheromon-Konzentration
     *
     * @return Initial-Wert der Pheromon-Konzentration
     */
    @Override
    public float initValue() {
        return this.pheromoneInitValue;
    }
}
