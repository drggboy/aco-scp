package eu.andredick.aco.pheromoneperception;

/**
 * <b>Abstrakte Komponente der Pheromon-Wahrnehmung</b><br>
 * Kapitel 3.3.6, S. 33, Pheromon-Wahrnehmung<br>
 * <br>
 * Die Pheromon-Wahrnehmung ist wird bei der Lösungskonstrukton der Ameisen verwendet.<br>
 * Bei der Lösungskonstruktion findet iterativ die Alternativen-Auswahl der Lösungskomponenten statt.<br>
 * Bei der Alternativen-Auswahl bezieht die Ameise neben den Heuristischen Informationen auch  das mit den Alternativen assoziierte Pheromon in ihre Entscheidung ein.<br>
 * Anstatt der Werte der Pheromon-Konzentrationen direkt zu verarbeiten, werden "subjektiv" wahrgenommene Werte verwendet.<br>
 * Die Pheromon-Wahrnehmung setzt damit für die Alternativen-Auswahl die tatsächliche Pheromon-Konzentration in wahrgenomene Werte um.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromonePerception.svg" alt=""></p>
 */
public abstract class AbstractPheromonePerception {

    /**
     * Liefert den durch die Ameise wahrgenommenen Wert des Pheromons,<br>
     * welcher aus der tatsächlichen Pheromon-Konzentration ermittelt wird.<br>
     * Die tatsächliche Pheromon-Konzentration geht als Eingabe der Methode in Berechnung ein.<br>
     *
     * @param pheromoneConcentration tatsächliche Konzentration des Pheromons
     * @return wahrgenommenen Konzentration des Pheromons
     */
    public abstract float getPerceptionValue(float pheromoneConcentration);

}
