package eu.andredick.aco.combination;

/**
 * Abstrakte Komponente Kombination-Funktion.
 * Kombiniert den wahrgenommenen Pheromon mit heuristischen Informationen.
 */
public interface CombinationRule {

    // Kombinations-Funktion
    // Engabe: wahrgenommenes Pheromon und der Wert der heuristischen Informationen
    // Rückgabe: Ergebnis der Kombination
    float combine(float pheromone, float heurist);

}
