package eu.andredick.aco.construct;

import eu.andredick.aco.nextstep.AbstractNextStepRule;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;

/**
 * Abstrakte Komponente der Konstruktionsstrategie.
 * Die Subklassen dieser Komponente werden durch die Klasse ACOAnt (Ameise) verwendet,
 * um Lösungen zu konstruieren.
 *
 * @param <E>
 */
public abstract class AbstractConstructionStrategy<E extends AbstractNextStepRule<?>> {

    // Regel für die Alternativen-Auswahl, die bei der Konstruktion benutzt werden soll.
    protected E nextStepRule;

    // Konstruktor
    public AbstractConstructionStrategy(E nextStepRule) {
        this.nextStepRule = nextStepRule;
    }

    // Schnittstelle zur Klasse ACOAnt, zur Konstruktion einer Lösung für das übergebene SCP-Problem
    public abstract Solution construct(SCProblem problem);
}
