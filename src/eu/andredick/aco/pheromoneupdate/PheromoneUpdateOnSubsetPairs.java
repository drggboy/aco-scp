package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>Ausprägung der Komponente der Pheromon-Markierung</b><br>
 * Kapitel 3.3.11, S. 45, Pheromon-Markierung<br>
 * <br>
 * Pheromon-Markierung auf Teilmengen-Paaren des SCP-Problems {@link PheromoneOnSubsets}, die in den zugehörigen Lösungen {@link SCPSolution} enthalten sind.<br>
 * <br>
 * Die Markierung der Lösungen ({@link AbstractSolution}) eines Problems ({@link eu.andredick.aco.problem.AbstractProblem}) mit Pheromon erfolgt koordiniert durch den Masterprozess ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}),
 * welcher hierzu die Ameisen ({@link eu.andredick.aco.ant.AbstractAnt}) anleitet.<br>
 * Um diese Aufgabe auszuführen, besitzen die Ameisen eine Ausprägung der Komponente Pheromon-Markierung.<br>
 * Für die zu markierende Instanz der Ameisen-Lösung {@link AbstractSolution} wird zunächst mittels der Qualitätsfunktion ({@link AbstractSolutionQuality}) die Menge des aufzutragenden Pheromons bestimmt.<br>
 * Anschließend kann mittels der Komponente Pheromon-Assoziation ({@link AbstractPheromoneAssociation}) die Konzentration des Pheromons auf den Lösungskomponenten erhöht werden.<br>
 *
 * <p><img src="{@docRoot}/images/PheromoneUpdate.svg" alt=""></p>
 */
public class PheromoneUpdateOnSubsetPairs extends
        AbstractPheromoneUpdate<PheromoneOnSubsetPairs, SCPSolution> {

    /**
     * Konstruktor
     *
     * @param pheromoneStructure Pheromon-Assoziation mit Teilmengen-Paaren des SCP
     * @param solutionQuality    Gütefunktion der Lösung
     */
    public PheromoneUpdateOnSubsetPairs(PheromoneOnSubsetPairs pheromoneStructure,
                                        AbstractSolutionQuality solutionQuality) {
        super(pheromoneStructure, solutionQuality);
    }

    /**
     * Markiert in Abhängigkeit der gegebenen SCP-Lösung alle in ihr enthaltenen Teilmengen-Paare mit Pheromon.<br>
     * Hierzu wird zunächst die Güte der Lösung mittels {@link #solutionQuality} bestimmt.<br>
     * Anschließend werden alle in der SCP-Lösung enthaltenen Teilmengen-Paare in Abhängigkeit der Lösungsgüte mit Pheromon markiert.<br>
     * Dazu wird die zugewiesene Komponente Pheromone-Assoziation ({@link #pheromoneStructure}) verwendet.<br>
     * Die Markierung für ein Paar (i,j) erfolgt symetrisch. Demnach werden die Relationen (i,j) und (j,i) gleichermaßen markiert.<br>
     *
     * @param solution SCP-Lösung bzw. Ameisen-Pfad, die/der mit Pheromon markiert werden soll.
     */
    @Override
    public void update(SCPSolution solution) {
        float delta = this.solutionQuality.getQuality(solution);
        List<Integer> varsList = solution.getSubsets();
        int varListSize = varsList.size();
        for (int i = 0; i < varListSize; i++) {
            for (int j = i + 1; j < varListSize; j++) {
                pheromoneStructure.addPheromone(varsList.get(i), varsList.get(j), delta);
                pheromoneStructure.addPheromone(varsList.get(j), varsList.get(i), delta);
            }
        }
        //ToDO: Gesonderte Markierung des ersten Elements evtl überfüssig
        pheromoneStructure.addPheromone(varsList.get(0), varsList.get(0), delta);
    }
}
