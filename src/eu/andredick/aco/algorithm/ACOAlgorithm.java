package eu.andredick.aco.algorithm;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;

/**
 * Die Klasse repräsentiert einen ACO-Algorithmus.
 * Jeder ACO-Algorithmus besitzt einen Masterprozess.
 * Der Masterprozess wird angestossen, sobald der Algorithmus gestartet wird.
 * ACO-Algorithmen erben die Fähigkeit der parallelen Ausführen von der Superklasse.
 */
public class ACOAlgorithm extends AbstractAlgorithm {

    // Komponente MasterProzess
    private AbstractMasterProcess masterProcess;

    // Kostruktor
    public ACOAlgorithm(AbstractMasterProcess masterProcess) {
        this.masterProcess = masterProcess;
    }

    // Schablonen-Methode, in welcher definiert wird, was der ACO-Algorithmus verrichten soll.
    @Override
    public void go() {
        this.masterProcess.start();
    }

    // Liefert die Statistik weiter
    @Override
    public Statistics getStatistics() {
        return this.masterProcess.getStatistics();
    }

}
