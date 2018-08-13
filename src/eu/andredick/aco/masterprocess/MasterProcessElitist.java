package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromonassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTermCriterion;

/**
 * <b>Masterprozess-Elitist</b> - Ausprägung der Komponente des Masterprozesses, bei der nur die iterationsbeste Ameise ihre Lösung mit Pheromon markieren darf.<br>
 * Kapitel 3.2.2 Masterprozess<br>
 * <br>
 * Die Implementierung des Masterprozess bildet den übergeordneten Ablauf der ACO-Metaheuristik ab,<br>
 * indem die Initiirung und Evaporation des Pheromons (siehe {@link AbstractPheromoneAssociation})<br>
 * und die Population der Ameisen (siehe {@link AbstractAnt}) koordiniert wird.<br>
 * <br>
 * <b>Ablauf:</b><br>
 * 1 - Initiierung des Pheromons<br>
 * 2 - Konstruktion der Lösungen aller Ameisen<br>
 * 3 - Lokale Suche auf konstruierten Lösungen aller Ameisen<br>
 * 4 - Evaporation des Pheromons<br>
 * 5 - Markierung des Pheromons durch die <b>iterationsbeste</b> Ameise<br>
 * 6 - Zurücksetzen der Ameisengedächtnisse<br>
 * 7 - Zurück zu 2., wenn Abbruchbedingungen nicht erfüllt.<br>
 * <br>
 * Ein Masterprozess wird im {@link eu.andredick.aco.algorithm.ACOAlgorithm} verwendet und dort gestartet.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 * Elitist-Strategie - Ausprägung der Komponente des Masterprozesses
 */
public class MasterProcessElitist extends AbstractMasterProcess {

    /**
     * Konstruktor
     *
     * @param pheromoneStructure Pheromonassoziation mit dem zu lösnden Problem
     * @param ants               Population der Ameisen
     * @param termCriterion      Abbruchkriterium für die Iteration
     */
    public MasterProcessElitist(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTermCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * <b>Logik des Masterprozeess-Elitist</b><br>
     * <br>
     * <b>Ablauf:</b><br>
     * 1 - Initiierung des Pheromons<br>
     * 2 - Konstruktion der Lösungen aller Ameisen<br>
     * 3 - Lokale Suche auf konstruierten Lösungen aller Ameisen<br>
     * 4 - Evaporation des Pheromons<br>
     * 5 - Markierung des Pheromons durch die <b>iterationsbeste</b> Ameise<br>
     * 6 - Zurücksetzen der Ameisengedächtnisse<br>
     * 7 - Zurück zu 2., wenn Abbruchbedingungen nicht erfüllt.<br>
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

            System.out.println("MasterProcessElitist... Interation: " + iteration);
        }

    }
}
