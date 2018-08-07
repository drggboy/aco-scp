package eu.andredick.aco.algorithm;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;

/**
 * <b>Die Klasse repräsentiert einen ACO-Algorithmus und implementiert die Schablone {@link AbstractAlgorithm}.</b><br>
 * Jeder ACO-Algorithmus besitzt einen Masterprozess ({@link AbstractMasterProcess}).<br>
 * Der Masterprozess wird angestossen, sobald der Algorithmus gestartet wird.<br>
 * ACO-Algorithmen erben die Fähigkeit der parallelen Ausführen von der Superklasse.
 * <p><img src="{@docRoot}/images/ACOAlgorithm.svg" alt=""></p>
 */
public class ACOAlgorithm extends AbstractAlgorithm {

    /**
     * Komponente MasterProzess (siehe {@link AbstractMasterProcess})
     */
    private AbstractMasterProcess masterProcess;

    /**
     * Kostruktor
     *
     * @param masterProcess
     */
    public ACOAlgorithm(AbstractMasterProcess masterProcess) {
        this.masterProcess = masterProcess;
    }

    /**
     * Methode zum Start des Ablaufes des ACO-Algorithmus.<br>
     * Der Start des ACO-Algorithmus erfolgt, indem die Methode {@link AbstractMasterProcess#start()} des MasterProcess aufgerufen wird.
     */
    @Override
    public void go() {
        this.masterProcess.start();
    }

    /**
     * Liefert Statistiken zum Ablauf des Algorithmus.<br>
     * Dazu wird das Statistics-Object vom MasterProcess geholt, wo es verwaltet wird (siehe {@link AbstractMasterProcess#getStatistics()}).<br>
     * @return Statistiken zum Ablauf des Algorithmus
     */
    @Override
    public Statistics getStatistics() {
        return this.masterProcess.getStatistics();
    }

}
