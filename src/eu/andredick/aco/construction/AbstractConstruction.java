package eu.andredick.aco.construction;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.problem.AbstractProblem;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>Abstrakte Konstruktionsheuristik.</b><br>
 * Kapitel 3.3.4, S. 30, Konstruktionsheuristik<br>
 * <br>
 * Die Ausprägungen dieser Komponente werden durch die Klasse {@link eu.andredick.aco.ant.ACOAnt} (Ameise) verwendet, um neue Lösungen zu konstruieren.<br>
 * Die Konstruktionsheuristik und die Alternativenauswahl hängen logisch stark voneinander ab, <br>
 * so dass generell nicht jede beliebige Ausprägung der Alternativenauswahl mit einer bestimmten Konstruktionsheuristik verwendet werden kann.<br>
 * Aus diesem Grund muss bei der Ableitung dieser abstrakten Klasse die generische Alternativenauswahl festgelet werden.<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 *
 * @param <E> Ausprägung der Alternativenauswahl, die bei der Konstruktion benutzt werden soll.
 */
public abstract class AbstractConstruction<E extends AbstractNextStepStrategy, P extends AbstractProblem, S extends AbstractSolution> {

    /**
     * Regel für die Alternativenauswahl, die bei der Konstruktion benutzt werden soll.
     */
    protected E nextStepRule;

    /**
     * Konstruktor
     *
     * @param nextStepRule Regel für die Alternativenauswahl, die bei der Konstruktion benutzt werden soll.
     */
    public AbstractConstruction(E nextStepRule) {
        this.nextStepRule = nextStepRule;
    }

    /**
     * Die Methode startet den Konstruktionsvorgang und liefert nach seinem Durchlauf eine vollständige Lösung des übergebenen Problems.<br>
     * Die Methode wird in der Klasse ACOAnt aufgerufen.
     *
     * @param problem Das AbstractProblem, für welches durch die Ameise eine Lösung konstruiert werden soll.
     * @return Vollständige konstruierte Lösung
     */
    public abstract S construct(P problem);
}
