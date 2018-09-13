package eu.andredick.aco.pheromoneevaporation;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>Abstrakte Komponente der Pheromon-Evaporation</b><br>
 * Kapitel 3.3.10, S. 45, Evaporation des Pheromons<br>
 * <br>
 * Durch diese Komponente wird der Prozess der stetigen Verdampfung des Pheromons nachgebildet.<br>
 * Sie stellt einen rekursiven Zusammenhang zwischen den Konzentrationen des Pheromons zweier benachbarter Iterationsschritte her.<br>
 * <br>
 * Die Pheromon-Evaporation wird durch den Masterprozess ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) koordiniert.<br>
 * Dies geschieht, indem die Methode {@link AbstractPheromoneAssociation#evaporatePheromones()} der Komponenten {@link AbstractPheromoneAssociation} aufgerufen wird.<br>
 * Anschließend benutzt {@link AbstractPheromoneAssociation} eine Ausprägung der Komponente Pheromon-Evaporation zur Anpassung aller Pheromon-Konzentrationen.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneEvaporation.svg" alt=""></p>
 */
public interface AbstractPheromoneEvaporation {

    /**
     * Berechnet die Pheromon-Konzentration des nachfolgenden Zeitschrittes (Iterationsschrittes).<br>
     *
     * @param pheromoneValue aktuelle Pheromon-Konzentration (Zeitpunkt t)
     * @return neue Pheromon-Konzentration (Zeitpunkt t+1)
     */
    float evaporate(float pheromoneValue);

}
