package eu.andredick.aco.combination;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;

/**
 * <b>Abstrakte Kombinationsfunktion.</b><br>
 * Kapitel 3.3.8, S. 36, Kombinationsfunktion<br>
 * <br>
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen.<br>
 * Die Kombinationsfunktion wird durch die Komponente Alternativenauswahl verwendet (siehe {@link AbstractNextStepStrategy}).<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public abstract class CombinationRule {


    /**
     * Abstrakte Methode zur Berechnung des kombinierten Wertes aus dem wahrgenommenen Pheromon und dem Wert der heuristischen Informationen.<br>
     * In den ableitende Klassen wird der funktionale Zusammenhang durch diese Methode implementiert.
     *
     * @param pheromone wahrgenommenes Pheromon
     * @param heurist   Wert der heuristischen Informationen
     * @return Ergebnis der Kombination
     */
    public abstract float combine(float pheromone, float heurist);

}
