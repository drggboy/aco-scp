package eu.andredick.aco.pheromoneperception;

/**
 * <b>Identische Wahrnehmung des Pheromons - Ausprägung der Komponente der Pheromon-Wahrnehmung</b><br>
 * Kapitel 3.3.6, S. 33, Pheromon-Wahrnehmung<br>
 * <br>
 * Bei dieser Ausprägung der Komponente ist die wahrgenommene Pheromon-Konzentration gleich der Tatsächlichen.<br>
 * <br>
 * Die Pheromon-Wahrnehmung ist wird bei der Lösungskonstrukton der Ameisen verwendet.<br>
 * Bei der Lösungskonstruktion findet iterativ die Alternativen-Auswahl der Lösungskomponenten statt.<br>
 * Bei der Alternativen-Auswahl bezieht die Ameise neben den Heuristischen Informationen auch  das mit den Alternativen assoziierte Pheromon in ihre Entscheidung ein.<br>
 * Anstatt der Werte der Pheromon-Konzentrationen direkt zu verarbeiten, werden "subjektiv" wahrgenommene Werte verwendet.<br>
 * Die Pheromon-Wahrnehmung setzt damit für die Alternativen-Auswahl die tatsächliche Pheromon-Konzentration in wahrgenomene Werte um.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromonePerception.svg" alt=""></p>
 */
public class PerceptionSimple extends AbstractPheromonePerception {

    /**
     * Liefert den wahrgenommenen Wert der tatsächlichen Pheromon-Konzentration.<br>
     * Hierbei ist eine identische Wahrnehmung des Pheromons mit der tatsächlichen Konzentration realisiert.<br>
     * Ausgabewert = Eingabewert<br>
     *
     * @param pheromoneConcentration tatsächliche Konzentration des Pheromons
     * @return tatsächliche Konzentration des Pheromons
     */
    @Override
    public float getPerceptionValue(float pheromoneConcentration) {
        return pheromoneConcentration;
    }
}
