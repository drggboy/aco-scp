package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>Masterprozess-Basic parallelisiert</b> - Grundausprägung der Komponente des Masterprozesses mit Parallelausführung<br>
 * Kapitel 3.3.2, S. 26, Masterprozess<br>
 * <br>
 * Die Implementierung des Masterprozess bildet den übergeordneten Ablauf der ACO-Metaheuristik ab,<br>
 * indem die Initiirung und Evaporation des Pheromons (siehe {@link AbstractPheromoneAssociation})<br>
 * und die Population der Ameisen (siehe {@link AbstractAnt}) koordiniert wird.<br>
 * <br>
 * <b>Ablauf:</b><br>
 * 1 - Initiierung des Pheromons<br>
 * 2 - Konstruktion der Lösungen aller Ameisen - parallel<br>
 * 3 - Lokale Suche auf konstruierten Lösungen aller Ameisen - parallel<br>
 * 4 - Evaporation des Pheromons<br>
 * 5 - Markierung des Pheromons durch <b>alle</b> Ameisen<br>
 * 6 - Zurücksetzen der Ameisengedächtnisse<br>
 * 7 - Zurück zu 2., wenn Abbruchbedingungen nicht erfüllt.<br>
 * <br>
 * Ein Masterprozess wird im {@link eu.andredick.aco.algorithm.ACOAlgorithm} verwendet und dort gestartet.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 */
public class MasterProcessBasicParallel extends AbstractMasterProcess {

    /**
     * Konstruktor
     *
     * @param pheromoneStructure Pheromonassoziation mit dem zu lösnden AbstractProblem
     * @param ants               Population der Ameisen
     * @param termCriterion      Abbruchkriterium für die Iteration
     */
    public MasterProcessBasicParallel(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }


    /**
     * <b>Logik des Masterprozeess-Basic</b><br>
     * <br>
     * <b>Ablauf:</b><br>
     * 1 - Initiierung des Pheromons<br>
     * 2 - Konstruktion der Lösungen aller Ameisen - parallel<br>
     * 3 - Lokale Suche auf konstruierten Lösungen aller Ameisen - parallel<br>
     * 4 - Evaporation des Pheromons<br>
     * 5 - Markierung des Pheromons durch alle Ameisen<br>
     * 6 - Zurücksetzen der Ameisengedächtnisse<br>
     * 7 - Zurück zu 2., wenn Abbruchbedingungen nicht erfüllt.<br>
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

            System.out.println("MasterProcessBasicParallel... Interation: " + iteration);
        }
    }
}
