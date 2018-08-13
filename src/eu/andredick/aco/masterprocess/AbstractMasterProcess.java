package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.aco.pheromonassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTermCriterion;

/**
 * <b>Abstrakte Komponente des Masterprozess</b><br>
 * Kapitel 3.2.2 Masterprozess<br>
 * <br>
 * Implementierung des Masterprozess bildet den übergeordneten Ablauf der ACO-Metaheuristik ab,<br>
 * indem die Initiirung und Evaporation des Pheromons (siehe {@link AbstractPheromoneAssociation})<br>
 * und die Population der Ameisen (siehe {@link AbstractAnt}) koordiniert wird.<br>
 * Dazu ist innerhalb der Methode {@link #start()} eine iterative Schleife zu implementieren,<br>
 * welche in Abhängigkeit des Abbruchkriteriums {@link AbstractTermCriterion} ausgeführt wird.<br>
 * Ein Masterprozess wird im {@link eu.andredick.aco.algorithm.ACOAlgorithm} verwendet und dort gestartet.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 */
public abstract class AbstractMasterProcess {

    /**
     * Die zu koordinierende Ameisenpopulation
     */
    protected AbstractAnt[] ants;

    /**
     * Pheromonassoziation mit dem zu lösnden Problem
     */
    protected AbstractPheromoneAssociation pheromoneStructure;

    /**
     * Abbruchkriterium für die Iteration
     */
    protected AbstractTermCriterion termCriterion;

    /**
     * Statistiken zum Ablauf des Algorithmus
     */
    protected Statistics statistics;

    /**
     * Konstruktor
     *
     * @param pheromoneStructure Pheromonassoziation mit dem zu lösnden Problem
     * @param ants               Population der Ameisen
     * @param termCriterion      Abbruchkriterium für die Iteration
     */
    public AbstractMasterProcess(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        this.pheromoneStructure = pheromoneStructure;
        this.ants = ants;
        this.termCriterion = termCriterion;
        this.statistics = new Statistics();
    }


    /**
     * Schnittstelle zur Klasse {@link eu.andredick.aco.algorithm.ACOAlgorithm}.<br>
     * Durch die Methode erfolgt der Start des Masterprozesses.
     */
    public abstract void start();

    /**
     * Liefert Statistiken zum Ablauf des Algorithmus.
     *
     * @return Statistiken zum Ablauf des Algorithmus
     */
    public Statistics getStatistics() {
        return statistics;
    }
}
