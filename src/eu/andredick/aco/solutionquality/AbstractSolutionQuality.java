package eu.andredick.aco.solutionquality;

import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;

/**
 * <b>Abstrakte Komponente der Gütefunktion für Lösungen</b><br>
 * Kapitel 3.3.12, S. 46, Gütefunktion für Lösungen <br>
 * <br>
 * Die Gütefunktion wird von der Komponente Pheromone-Markierung ({@link AbstractPheromoneUpdate}) verwendet.<br>
 * Sie wird benötigt, um die Menge des Pheromons bei der Markierung von Lösungen vom Zielfunktionswert zu entkoppeln.<br>
 * Damit kann die Menge des Pheromons beispielsweise auf einen Wertebereich begrenzt werden.<br>
 * Um die Güte zu bestimmen, ist die Kenntnis über die Beschaffenheit der Lösung ({@link AbstractSolution}) notwendig.<br>
 * Daher müssen Ausprägungen der abstrakten Gütefunktion festlegen,
 * für welche Klasse von Lösungen und damit Problemen sie die Güte bestimmen können.<br>
 *
 * <p><img src="{@docRoot}/images/SolutionQuality.svg" alt=""></p>
 *
 * @param <S> Ausprägung der Abstrakten Klasse von Lösungen
 */
public abstract class AbstractSolutionQuality<S extends AbstractSolution> {

    /**
     * Liefert die Güte der übergebenen Lösung
     * @param solution zu bewertende Lösung
     * @return Güte der Lösung
     */
    public abstract float getQuality(S solution);

}
