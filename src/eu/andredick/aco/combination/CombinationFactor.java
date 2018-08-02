package eu.andredick.aco.combination;

/**
 * Subklasse der Komponente Kombination-Funktion.
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen durch Multiplikation.
 * Beide Faktoren werden durch zugehörige Exponenten alpha und beta relativ zueinander gewichtet.
 */
public class CombinationFactor implements CombinationRule {

    // Exponenten der Funktion
    float alpha;
    float beta;

    // Konstruktor mit übergabe der zwei Parameter
    public CombinationFactor(float alpha, float beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    // Kombinations-Funktion
    // Engabe: wahrgenommenes Pheromon und der Wert der heuristischen Informationen
    // Rückgabe: Ergebnis der Kombination
    @Override
    public float combine(float pheromone, float heurist) {
        return (float) (Math.pow(pheromone, alpha) * Math.pow(heurist, beta));
    }
}
