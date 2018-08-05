package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromonassociation.AbstractPheromone;
import eu.andredick.aco.termination.AbstractTermCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * Kapitel 3.2.2	Masterprozess
 * Ausprägung der Komponente des Masterprozesses
 */
public class MasterProcessBasicParallel extends AbstractMasterProcess {
    // Konstruktor
    public MasterProcessBasicParallel(AbstractPheromone pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * Logik des Masterprozeess-Basic
     * Parallele Ausführung der Ameisen
     */
    @Override
    public void start() {
        this.pheromoneStructure.initPheromones();

        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

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

            this.pheromoneStructure.evaporatePheromones();

            for (AbstractAnt ant : this.ants) {
                float value = ant.evaluateSolution();
                this.statistics.setValue(iteration, value, ant.getSolution());
                ant.markPheromone();
                ant.resetAnt();
            }

            System.out.println("MasterProcessBasic ... Interation: " + iteration);
        }
    }
}
