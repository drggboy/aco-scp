package eu.andredick.aco.problem;

/**
 * <b>Abstrakte Lösung zur abstrakten Klasse von Problemen</b>, welche durch die ACO-Metaheuristik zu lösen sind<br>
 * <br>
 * Eine Lösung im Sinne einer Instanz ist eindeutig einer Instanz eines Problems zugeordnet ({@link #getProblem()}).<br>
 * Einem Problem dagegen können mehrere Lösungen zugeordnet werden.
 * <br>
 * Auf der Klassen-Ebene besteht zwischen Lösung und Problem besteht eine Abhängigkeit der Lösung von der Klasse des Problems.<br>
 * Bei Ableitung der Abstrakten Lösung muss im Rahmen der generischen Programmierung festgelegt werden, für welche Klasse von Problemen die verwendet wird.<br>
 * Im Regelfall muss für jede Problem-Klasse eine zugehörige Klasse der Lösung implementiert werden.<br>
 *
 * <p><img src="{@docRoot}/images/Problem.svg" alt=""></p>
 *
 * @param <P> zugehörige Ausprägung der Klasse von Abstrakten Problemen - eine Klasse von Speziellen Problemmen
 */
public abstract class AbstractSolution<P extends AbstractProblem> {

    /**
     * Liefert die eindeutig zugeordnete Instanz des Problems
     *
     * @return Instanz des Problems
     */
    public abstract P getProblem();

    /**
     * Liefert den Zielfunktionswert der Lösung
     *
     * @return Zielfunktionswert der Lösung
     */
    public abstract float getValue();

    /**
     * Erzeugt eine leere Lösung der entsprechenden Subklasse<br>
     * Die Notwendigkeit dieser Methode ergibt sich,
     * weil auf der generischen Ebene der Definition der ACO-Metaheuristik keine Abhängigkeiten zu Ausprägungen von Komponenten bestehen soll.
     *
     * @return Instanz einer leeren Lösung
     */
    public abstract AbstractSolution createNew();
}
