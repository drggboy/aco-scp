package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.termination.AbstractTermCriterion;

/**
 * Kapitel 3.2.2	Masterprozess
 * Elitist-Strategie - Ausprägung der Komponente des Masterprozesses
 */
public class MasterProcessElitist extends AbstractMasterProcess {

    // Konstruktor
    public MasterProcessElitist(AbstractPheromone pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * Logik des Masterprozeess-Elitist
     */
    @Override
    public void start() {

        // Initiierung der Pheromone
        this.pheromoneStructure.initPheromones();

        // Iteration bis Abbruchkriterum erfüllt
        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            // Favoritwerte der Iteration
            AbstractAnt bestIterAnt = null;
            Float bestIterValue = null;

            // Iteration über alle Ameisen
            for (AbstractAnt ant : this.ants) {
                // Konstruiere eine Lösung
                ant.constructSolution();
                // Lokale Suche auf der konstruierten Lösung
                ant.localSearch();
                // Zielfunktionswert
                float value = ant.evaluateSolution();

                // Bestimme die Favorit-Ameise
                if (bestIterValue == null || value < bestIterValue) {
                    bestIterValue = value;
                    bestIterAnt = ant;
                }
                // Aktualisiere Statistiken
                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            // Evaporation der Pheromone
            this.pheromoneStructure.evaporatePheromones();

            // Pheromon-Update nur durch iterationsbeste Ameisen
            bestIterAnt.markPheromone();

            for (AbstractAnt a : this.ants) {
                a.resetAnt();
            }

            System.out.println("MasterProcessElitist ... Interation: " + iteration);
        }

    }
}
