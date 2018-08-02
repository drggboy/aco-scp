package eu.andredick.aco.algorithm;

import eu.andredick.aco.construct.AbstractConstructionStrategy;
import eu.andredick.aco.localsearch.AbstractLocalSearchStrategy;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;

/**
 * Die Klasse ist eine Fassade, welche für den Masterprozess die Funktionen einer Ameise bereitstellt.
 */
public class ACOAnt extends AbstractAnt {

    // gemeinsames SCP-Problem und der Ameise zugehörige Lösung
    protected SCProblem problem;
    protected Solution solution;

    // Beliebige Komponente für Pheromon-update
    protected AbstractPheromoneUpdate updateRule;

    // Beliebige Komponente für Lösungskonstruktion
    protected AbstractConstructionStrategy constructionStrategy;

    // Beliebige Komponente für Lokale Suche
    protected AbstractLocalSearchStrategy localSearchStrategy;

    // Konstruktor
    public ACOAnt(SCProblem problem,
                  AbstractPheromoneUpdate updateRule,
                  AbstractConstructionStrategy constructionStrategy,
                  AbstractLocalSearchStrategy localSearchStrategy) {

        this.problem = problem;
        this.solution = null;
        this.updateRule = updateRule;
        this.constructionStrategy = constructionStrategy;
        this.localSearchStrategy = localSearchStrategy;
    }

    // Lösungskonstruktion
    @Override
    public void constructSolution() {
        this.solution = this.constructionStrategy.construct(this.problem);
    }

    // Evaluation der Lösung
    @Override
    public float evaluateSolution() {
        return this.solution.getValue();
    }

    // Pheromon-Update
    @Override
    public void markPheromone() {
        this.updateRule.update(this.solution);
    }

    // Lokale Suche
    @Override
    public void localSearch() {
        this.solution = this.localSearchStrategy.search(this.solution);
    }

    // Zurücksetzen des Zustandes der Ameise
    @Override
    public void resetAnt() {
        this.solution = new Solution(problem);
    }

    // Liefert die Lösung der Ameise
    @Override
    public Solution getSolution() {
        return this.solution;
    }

    // Schreibt eine Lösung ins Gedächtnis
    @Override
    public void setSolution(Solution solution) {
        this.solution = solution;
    }


}
