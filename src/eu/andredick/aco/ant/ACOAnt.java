package eu.andredick.aco.ant;

import eu.andredick.aco.construct.AbstractConstructionStrategy;
import eu.andredick.aco.localsearch.AbstractLocalSearchStrategy;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;

/**
 * <b>Realisierung der Komponente Ameise</b> durch die Implementierung der abstrakten Methoden der Klasse {@link AbstractAnt}.<br>
 * Stellt Methoden bereit, die von der Komponente MasterProzess (siehe {@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) verwendet werden.<br>
 * Entwurfsmuster Fassade, in dem Methoden mehrerer Klassen zentralisiert durch eine Schnittstelle angeboten werden.<br>
 * Die Ameise besitzt darüber hinaus Objektvariablen, die ihren Zustand definieren.<br>
 * <p><img src="{@docRoot}/images/ACOAnt.svg" alt=""></p>
 */
public class ACOAnt extends AbstractAnt {

    /**
     * Das Problem, auf welchem die Lösung (Pfad) gefunden werden soll.
     */
    protected SCProblem problem;

    /**
     * Aktuelle Lösung (Pfad) der Ameise.<br>
     * Die Lösunge kann unvollständig sein.
     */
    protected Solution solution;

    /**
     * Beliebige Realisierung der Komponente für Pheromon-Markierung
     */
    protected AbstractPheromoneUpdate updateRule;

    /**
     * Beliebige Realisierung der Komponente für Konsturkionsheuristik von Lösungen
     */
    protected AbstractConstructionStrategy constructionStrategy;

    /**
     * Beliebige Realisierung der Komponente für Lokale Suche
     */
    protected AbstractLocalSearchStrategy localSearchStrategy;

    /**
     * Konstruktor
     *
     * @param problem              Das Problem, auf welchem die Lösung bzw. Pfad gefunden werden soll.
     * @param updateRule           Beliebige Realisierung der Komponente für Konsturkionsheuristik von Lösungen
     * @param constructionStrategy Beliebige Realisierung der Komponente für Konsturkionsheuristik von Lösungen
     * @param localSearchStrategy  Beliebige Realisierung der Komponente für Lokale Suche
     */
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

    /**
     * Startet die Konstrukton einer Ameisenlösung.<br>
     * Dazu wird die der Ameise zugewiesene Komponente der Konstruktionsheuristik genutzt.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     * Die von der Ameise erstellte Lösung soll als Objektvariable vorgehalten werden.
     *
     * @see AbstractConstructionStrategy#construct(SCProblem)
     */
    @Override
    public void constructSolution() {
        this.solution = this.constructionStrategy.construct(this.problem);
    }

    /**
     * Liefert den Zielfunktionswert der Ameisenlösung.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     * Der Zustand der Ameise kann eine unvollständige Lösung beinhalten!<br>
     *
     * @return Zielfunktionswert der Ameisenlösung
     */
    @Override
    public Float evaluateSolution() {
        return this.solution.getValue();
    }

    /**
     * Startet die Markierung der Ameisen-Lösung auf den Entitäten des Problems.<br>
     * Dazu wird die der Ameise zugewiesene Komponente der Pheromon-Markierung genutzt.<br>
     * Die Methode wird von dem Masterprozess aufgerufen.<br>
     *
     * @see AbstractPheromoneUpdate#update(Solution)
     */
    @Override
    public void markPheromone() {
        this.updateRule.update(this.solution);
    }

    /**
     * Startet die Verbesserung der konstruierten Ameisenlösung durch Lokale Suche.<br>
     * Dazu wird die der Ameise zugewiesene Komponente der Lokalen Suche genutzt. <br>
     * Die Methode wird von dem Masterprozess aufgerufen. <br>
     *
     * @see AbstractLocalSearchStrategy#search(Solution)
     */
    @Override
    public void localSearch() {
        this.solution = this.localSearchStrategy.search(this.solution);
    }

    /**
     * Erneuert den Zustand der Ameise für die nächste Iteration. <br>
     * Dazu wird die bisherige Ameisenlösung durch eine leere Lösung ersetzt. <br>
     * Die Methode wird von dem Masterprozess aufgerufen.
     */
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
