package eu.andredick.aco.masterprocess;

import eu.andredick.aco.algorithm.AbstractAnt;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.termination.AbstractTermCriterion;

/**
 * Kapitel 3.2.2	Masterprozess
 * Masterprozess-Basic - Ausprägung der Komponente des Masterprozesses
 */
public class MasterProcessBasic extends AbstractMasterProcess {

    // Konstruktor
    public MasterProcessBasic(AbstractPheromone pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * Logik des Masterprozeess-Basic
     */
    @Override
    public void start() {

        // Initiierung der Pheromone
        this.pheromoneStructure.initPheromones();

        // Iteration bis Abbruchkriterum erfüllt
        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            // Iteration über alle Ameisen
            for (AbstractAnt ant : this.ants) {
                // Konstruiere eine Lösung
                ant.constructSolution();
                // Lokale Suche auf der konstruierten Lösung
                ant.localSearch();
                // Zielfunktionswert
                float value = ant.evaluateSolution();
                // Aktualisiere Statistiken
                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            // Evaporation der Pheromone
            this.pheromoneStructure.evaporatePheromones();

            // Pheromon-Update durch alle Ameisen
            for (AbstractAnt a : this.ants) {
                a.markPheromone();
                a.resetAnt();
            }

            System.out.println("MasterProcessBasic ... Interation: " + iteration);
        }

    }
}
