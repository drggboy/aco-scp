package eu.andredick.aco.problem;

/**
 * <b>Abstrakte Klasse von Problemen</b>, welche durch die ACO-Metaheuristik zu lösen sind<br>
 * <br>
 * Diese Abstrakte Komponente wird auf der generischen Ebene der ACO-Metaheuristik verwendet,<br>
 * auf welcher noch keine Abhängigkeiten zu einer bestimmten Problem-Klasse bestehen.<br>
 * Genauso können auch spezialisierte Komponenten der ACO-Metaheuristik das Abstrakte Problem referenzieren, wenn sie von keiner bestimmten Problem-Klasse abhängig sind.<br>
 * <br>
 * Um eine Problem-Klasse zu definieren, muss die Abstraktes Problem und die zugehörige Abstakte Lösung abgeleitet werden.<br>
 *
 * <p><img src="{@docRoot}/images/Problem.svg" alt=""></p>
 */
public abstract class AbstractProblem {

    /**
     * Liefert den Namen der Instanz eines Problems
     *
     * @return Name des Problems
     */
    public abstract String getName();
}
