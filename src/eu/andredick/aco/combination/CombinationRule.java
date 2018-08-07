package eu.andredick.aco.combination;

/**
 * <b>Abstrakte Kombination-Funktion.</b><br>
 * Kapitel 3.3.8, S. 36, Kombinationsfunktion<br>
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen.
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
