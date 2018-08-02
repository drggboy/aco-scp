package eu.andredick.aco.pheromoneperception;

/**
 * Kapitel 3.2.6	Pheromon-Wahrnehmung
 * triviale Wahrnehmung - Ausprägung der Komponente der Pheromon-Wahrnehmung
 * Wird von Alternativenauswahl verwendet, um den Wert der Alternative zu bestimmen
 */
public class PerceptionSimple extends AbstractPheromonePerception {

    /**
     * Identische Wahrnehmung des Pheromons mit der tatsächlichen Konzentration
     *
     * @param pheromoneConcentration: tatsächliche Konzentration des Pheromons
     * @return: tatsächliche Konzentration des Pheromons
     */
    @Override
    public float getValue(float pheromoneConcentration) {
        return pheromoneConcentration;
    }
}
