package eu.andredick.aco.pheromoneperception;

/**
 * Kapitel 3.2.6	Pheromon-Wahrnehmung
 * Abstrakte Komponente der Pheromon-Wahrnehmung
 * Wird von Alternativenauswahl verwendet, um den Wert der Alternative zu bestimmen
 */
public abstract class AbstractPheromonePerception {

    /**
     * Berechnet den wahrgenommenen Wert
     *
     * @param pheromoneConcentration tatsächliche Konzentration des Pheromons
     * @return wahrgenommenen Konzentration des Pheromons
     */
    public abstract float getValue(float pheromoneConcentration);

}
