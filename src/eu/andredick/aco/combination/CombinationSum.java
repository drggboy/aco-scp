package eu.andredick.aco.combination;

/**
 * <b>Realisierung der Komponente Kombinationsfunktion mittels Addition.</b><br>
 * Kapitel 3.3.8, S. 36, Kombinationsfunktion<br>
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen <b>durch Addition</b>.<br>
 * Beide Summanden werden durch einen Gewichtungsfaktor <b>gamma</b> relativ zueinander gewichtet.<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public class CombinationSum extends CombinationRule {

    /**
     * Gewichtungsfaktor <b>gamma</b>, der die Summanden (Phermonon und heuristischen Informationen) relativ zueinander gewichtet.
     */
    float gamma;

    /** Konstruktor mit übergabe des Parameters
     *
     * @param gamma Gewichtungsfaktor <b>gamma</b>, der die Summanden (Phermonon und heuristischen Informationen) relativ zueinander gewichtet.
     */
    public CombinationSum(float gamma) {
        this.gamma = gamma;
    }

    /** <b>Kombinationsfunktion mittels Addition.</b><br>
     * <code>gamma * pheromone + (1 - gamma) * heurist</code>
     * @param pheromone wahrgenommenes Pheromon
     * @param heurist   Wert der heuristischen Informationen
     * @return Ergebnis der Kombination
     */
    @Override
    public float combine(float pheromone, float heurist) {
        return gamma * pheromone + (1 - gamma) * heurist;
    }
}
