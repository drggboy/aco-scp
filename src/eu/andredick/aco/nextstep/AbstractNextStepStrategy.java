package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.problem.AbstractSolution;

import java.util.List;

/**
 * <b>Abstrakte Komponente der Alternativen-Auswahl</b><br>
 * Kapitel 3.3.5, S. 32, Alternativenauswahl<br>
 * <br>
 * Die Komponente Alternativenauswahl wird von Konstruktionsheuristik {@link eu.andredick.aco.construct.AbstractConstructionStrategy} verwendet,<br>
 * um aus der Menge gegebener Alternativen (Lösungskomponenten) eine Alternative auszuwählen.<br>
 * Die Auswahl der Alternative stützt sich auf den heuristischen Informationen {@link HeuristicInfoSet} und<br>
 * den wahrgenommenen Pheromonkonzentrationen {@link AbstractPheromonePerception}, die den Alternativen zugeordnet sind oder für diese berechnet werden.<br>
 * Mittels der Kombinationsfunktion {@link CombinationRule} wird aus heuristischen Informationen und der wahrgenommenen Pheromonkonzentration ein Wert der Alternative gebildet.<br>
 * <br>
 * Eine weitere Abhängigkeit besteht von der Komponente Pheromonassoziation (siehe {@link AbstractPheromoneAssociation}.<br>
 * Um den wahrgenonommenen Pheromonwert bestimmen zu können, wird hierdurch der tatsächliche Pheromonwert ermittelt.
 * <br>
 * Realisierungen der abstrakten Komponente müssen die Methode {@link #chooseSubset(AbstractSolution, List)} implementieren.
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public abstract class AbstractNextStepStrategy<E extends AbstractPheromoneAssociation, S extends AbstractSolution> {

    /**
     * Pheromonassoziation mit dem zu lösenden AbstractProblem
     */
    protected E pheromoneStructure;
    /**
     * Pheromon-Wahrnehmung
     */
    protected AbstractPheromonePerception perceptionRule;
    /**
     * Menge Heuristischer Informationen
     */
    protected HeuristicInfoSet heuristics;
    /**
     * Kombinationsfunktion (kombiniert die Werte der Pheromon-Wahrnehmung und der Heuristischen Informationen)
     */
    protected CombinationRule combinationRule;


    /**
     * Konsturktor
     *
     * @param pheromoneStructure Pheromonassoziation
     * @param perceptionRule     Pheromon-Wahrnehmung
     * @param heuristics         heuristische Informationen
     * @param combinationRule    Kombinationsfunktion
     */
    public AbstractNextStepStrategy(E pheromoneStructure,
                                    AbstractPheromonePerception perceptionRule,
                                    HeuristicInfoSet heuristics,
                                    CombinationRule combinationRule) {

        this.pheromoneStructure = pheromoneStructure;
        this.perceptionRule = perceptionRule;
        this.heuristics = heuristics;
        this.combinationRule = combinationRule;
    }

    /**
     * Abstrakte Methode zur Auswahl einer Alternative aus einer gegebenen Menge.<br>
     * Schnittstelle zur Konstruktionsheuristik ({@link eu.andredick.aco.construct.AbstractConstructionStrategy}).<br>
     *
     * @param solution         partiale Lösung der Ameise
     * @param availableSubsets verfügbare Alternativen
     * @return gewählte Alternative
     */
    public abstract Integer chooseSubset(S solution, List<Integer> availableSubsets);
}
