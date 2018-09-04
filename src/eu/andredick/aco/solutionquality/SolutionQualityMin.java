package eu.andredick.aco.solutionquality;

import eu.andredick.scp.SCPSolution;

/**
 * Kapitel 3.3.12 <br>
 * Einfache Gütefunktion für Lösungen des SCP als Minimierungsproblem <br>
 * Die Komponente Gütefunktion wird von der Komponente PheromoneUpdate (Pheromon-Markierung) verwendet. <br>
 */
public class SolutionQualityMin extends AbstractSolutionQuality<SCPSolution> {

    /**
     * Liefert die Güte Q der Lösung. <br>
     * Q = n / z <br>
     * mit n = Anzahl der Teilmengen, z = Zielfunktionswert der Lösung <br>
     * @param solution: Lösung des SCP
     * @return Güte der Lösung
     */
    @Override
    public float getQuality(SCPSolution solution) {

        return (float) solution.getProblem().getStructure().subsetsSize() / solution.getValue();

    }
}
