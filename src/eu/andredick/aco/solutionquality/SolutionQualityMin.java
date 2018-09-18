package eu.andredick.aco.solutionquality;

import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.scp.SCPSolution;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;

/**
 * <b>Einfache Gütefunktion für SCP-Lösungen</b><br>
 * Kapitel 3.3.12, S. 46, Gütefunktion für Lösungen <br>
 * <br>
 * Die einfache Gütefunktion ist proportional zum Kehrwert des Zielfunktionswertes.<br>
 * Um den Wertebereich anzuheben wird der Kehrwert des Zielfunktionswertes mit der Anzahl der Teilmengen multipliziert.<br>
 * <br>
 * Die Gütefunktion wird von der Komponente Pheromone-Markierung ({@link AbstractPheromoneUpdate}) verwendet.<br>
 * Sie wird benötigt, um die Menge des Pheromons bei der Markierung von Lösungen vom Zielfunktionswert zu entkoppeln.<br>
 * Damit kann die Menge des Pheromons beispielsweise auf einen Wertebereich begrenzt werden.<br>
 * Um die Güte zu bestimmen, ist die Kenntnis über die Beschaffenheit der Lösung ({@link AbstractSolution}) notwendig.<br>
 * Daher müssen Ausprägungen der abstrakten Gütefunktion festlegen,
 * für welche Klasse von Lösungen und damit Problemen sie die Güte bestimmen können.<br>
 *
 * <p><img src="{@docRoot}/images/SolutionQuality.svg" alt=""></p>
 */
public class SolutionQualityMin extends AbstractSolutionQuality<SCPSolution> {

    /**
     * Liefert die Güte Q der übergebenen Lösung.<br>
     * Q = n / z<br>
     * mit n = Anzahl der Teilmengen, z = Zielfunktionswert der Lösung<br>
     * @param solution: Lösung des SCP
     * @return Güte der Lösung
     */
    @Override
    public float getQuality(SCPSolution solution) {

        return (float) solution.getProblem().getStructure().subsetsSize() / solution.getValue();

    }
}
