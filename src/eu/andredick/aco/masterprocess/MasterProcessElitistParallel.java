package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.termination.AbstractTermCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * Kapitel 3.2.2	Masterprozess
 * Elitist-Strategie - Ausprägung der Komponente des Masterprozesses
 * Parallele Ausführung
 */
public class MasterProcessElitistParallel extends AbstractMasterProcess {

    // Konstruktor
    public MasterProcessElitistParallel(AbstractPheromone pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * Logik des Masterprozeess-Elitist
     * Parallele Ausführung der Ameisen
     */
    @Override
    public void start() {
        this.pheromoneStructure.initPheromones();

        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            AbstractAnt bestIterAnt = null;
            Float bestIterValue = null;


            List<Thread> threads = new LinkedList<>();
            for (AbstractAnt ant : this.ants) {

                threads.add(new Thread(
                        new Runnable() {

                            @Override
                            public void run() {

                                ant.constructSolution();

                                ant.localSearch();

                            }
                        }
                ));
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (AbstractAnt ant : this.ants) {

                float value = ant.evaluateSolution();

                if (bestIterValue == null || value < bestIterValue) {
                    bestIterValue = value;
                    bestIterAnt = ant;
                }

                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            this.pheromoneStructure.evaporatePheromones();

            bestIterAnt.markPheromone();

            for (AbstractAnt a : this.ants) {
                a.resetAnt();
            }

            System.out.println("MasterProcessElitist ... Interation: " + iteration);
        }
    }
}
