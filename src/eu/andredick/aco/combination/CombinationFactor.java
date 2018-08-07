package eu.andredick.aco.combination;

/**
 * <b>Realisierung der Komponente Kombinationsfunktion mittels Multiplikation.</b><br>
 * Kapitel 3.3.8, S. 36, Kombinationsfunktion<br>
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen <b>durch Multiplikation</b>.<br>
 * Beide Faktoren werden durch zugehörige Exponenten alpha und beta relativ zueinander gewichtet.<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public class CombinationFactor extends CombinationRule {

    /**
     * Exponent <b>alpha</b> zur relativen Gewichtung des wahrgenommenen Pheromons
      */
    float alpha;

    /**
     * Exponent <b>beta</b> zur relativen Gewichtung des Wertes der heuristischen Informationen
     */
    float beta;

    /** Konstruktor mit übergabe der zwei Parameter
     *
     * @param alpha Exponent <b>alpha</b> zur relativen Gewichtung des wahrgenommenen Pheromons
     * @param beta Exponent <b>beta</b> zur relativen Gewichtung des Wertes der heuristischen Informationen
     */
    public CombinationFactor(float alpha, float beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    /** <b>Kombinationsfunktion mittels Multiplikation</b><br>
     * <code>Math.pow(pheromone, alpha) * Math.pow(heurist, beta)</code>
     * @param pheromone wahrgenommenes Pheromon
     * @param heurist   Wert der heuristischen Informationen
     * @return Ergebnis der Kombination
     */
    @Override
    public float combine(float pheromone, float heurist) {
        return (float) (Math.pow(pheromone, alpha) * Math.pow(heurist, beta));
    }
}
