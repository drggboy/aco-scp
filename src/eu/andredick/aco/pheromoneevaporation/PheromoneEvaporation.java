package eu.andredick.aco.pheromoneevaporation;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>Ausprägung der Komponente der Pheromon-Evaporation</b><br>
 * Kapitel 3.3.10, S. 45 und S.14, Evaporation des Pheromons<br>
 * <br>
 * Die Evaporation ist hier mittels der Funktion einer exponentiellen Abnahme modelliert.<br>
 * Durch diese Komponente wird der Prozess der stetigen Verdampfung des Pheromons nachgebildet.<br>
 * Sie stellt einen rekursiven Zusammenhang zwischen den Konzentrationen des Pheromons zweier benachbarter Iterationsschritte her.<br>
 * <br>
 * Die Komponente besitzt einen Parameter ({@link #evaporationFactor}).<br>
 * <br>
 * Die Pheromon-Evaporation wird durch den Masterprozess ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) koordiniert.<br>
 * Dies geschieht, indem die Methode {@link AbstractPheromoneAssociation#evaporatePheromones()} der Komponenten {@link AbstractPheromoneAssociation} aufgerufen wird.<br>
 * Anschließend benutzt {@link AbstractPheromoneAssociation} eine Ausprägung der Komponente Pheromon-Evaporation zur Anpassung aller Pheromon-Konzentrationen.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneEvaporation.svg" alt=""></p>
 */
public class PheromoneEvaporation implements AbstractPheromoneEvaporation {

    /**
     * Abnahmefaktor (Evaporations-Faktor), mit welchem die Pheromon-Konzentrationen miltipliziert werden
     */
    private float evaporationFactor;

    /**
     * Konstruktor
     *
     * @param evaporationFactor Evaporations-Faktor als Parameter der Komponente
     */
    public PheromoneEvaporation(float evaporationFactor) {
        this.evaporationFactor = evaporationFactor;
    }

    /**
     * Berechnet die Pheromon-Konzentration des nachfolgenden Zeitschrittes (Iterationsschrittes),<br>
     * indem der übergebenen Wert der Pheromon-Konzentration mit dem Abnahmefaktor (Evaporations-Faktor) multipliziert wird.
     *
     * @param pheromoneValue aktuelle Pheromon-Konzentration (Zeitpunkt t)
     * @return neue Pheromon-Konzentration (Zeitpunkt t+1)
     */
    @Override
    public float evaporate(float pheromoneValue) {
        return pheromoneValue * evaporationFactor;
    }
}
