package eu.andredick.aco.masterprocess;

import eu.andredick.aco.algorithm.AbstractAnt;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.termination.AbstractTermCriterion;

/**
 * Kapitel 3.2.2	Masterprozess
 * Abstrakte Komponente des Masterprozess
 */
public abstract class AbstractMasterProcess {

    // Die zu koordinierenden Ameisen
    protected AbstractAnt[] ants;

    // Pheromonassotiation
    protected AbstractPheromone pheromoneStructure;

    // Abbruchkriterium
    protected AbstractTermCriterion termCriterion;

    // Statistiken
    protected Statistics statistics;

    // Konstruktor
    public AbstractMasterProcess(AbstractPheromone pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        this.pheromoneStructure = pheromoneStructure;
        this.ants = ants;
        this.termCriterion = termCriterion;
        this.statistics = new Statistics();
    }


    /**
     * Schnittstelle zur Klasse ACOAlgorithm
     * Durch die Methode erfolgt der Start des Masterprozesses
     */
    public abstract void start();

    // Liefert Statistiken zurück
    public Statistics getStatistics() {
        return statistics;
    }
}
