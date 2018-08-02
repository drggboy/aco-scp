package eu.andredick.aco.combination;

/**
 * Subklasse der Komponente Kombination-Funktion.
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen durch Summation.
 * Beide Summanten werden durch den Parameter gamma relativ zueinander gewichtet.
 */
public class CombinationSum implements CombinationRule {

    // Parameter
    float gamma;

    // Konstruktor mit übergabe des Parameters
    public CombinationSum(float gamma) {
        this.gamma = gamma;
    }

    // Kombinations-Funktion
    // Engabe: wahrgenommenes Pheromon und der Wert der heuristischen Informationen
    // Rückgabe: Ergebnis der Kombination
    @Override
    public float combine(float pheromone, float heurist) {
        return gamma * pheromone + (1 - gamma) * heurist;
    }
}
