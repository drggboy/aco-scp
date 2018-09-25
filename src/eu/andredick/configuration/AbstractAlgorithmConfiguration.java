package eu.andredick.configuration;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.scp.SCProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Die abstrakte Klasse dient als Schablone für Konfigurationsfabriken von Algorithmen mit unterschiedlichen Komponenten.<br>
 * Sie hält eine Paramterliste vor, die durch ableitende Klassen zu füllen ist.<br>
 * Über die zu implementierende Methode create() wird ein konfigurierter Algorithmus instanziiert und zurückgegeben,<br>
 * damit dieser ausserhalb dieser Klasse ausgeführt werden kann.<br>
 */
public abstract class AbstractAlgorithmConfiguration {

    protected String configName;

    protected ArrayList<ConfigurationParameter> configurationParameters;


    /**
     * Standardkonsturktor der Algorithmus-Konfiguration
     */
    public AbstractAlgorithmConfiguration() {

        configurationParameters = new ArrayList<>();

        ConfigurationParameter<Integer> antS =
                new ConfigurationParameter<>("antsize", 10);
        this.addConfigurationParameter(antS);

        this.prepareConfigParameters();

    }

    /**
     * Wichtigste Methode der Klasse.<br>
     * Erzeugt auf Basis der Konfiguration inkl. der Parameter eine Instanz des Algorithmus<br>
     *
     * @param problem SCP-Instanz
     * @return Algorithmus-Instanz
     */

    public abstract AbstractAlgorithm create(SCProblem problem);

    /**
     * Abstrakte Methode, die von ableitenden Klassen zu implementieren ist.<br>
     * Damit sollen alle notwendigen Parameter der Algorithmus-Konfiguration erzeugt und in eine Parameter-Liste hinzugefügt werden.<br>
     * Diese methode wird im Konstruktor bei Instanziierung der Konfiguration aufgerunfen.<br>
     */
    public abstract void prepareConfigParameters();

    /**
     * Fügt einen Parameter zur internen Parameter-Liste hinzu
     *
     * @param parameter Parameter
     */
    public void addConfigurationParameter(ConfigurationParameter parameter) {
        // Prüfen, ob der Parameter bereits vorhanden ist
        if (!this.configurationParameters.contains(parameter)) {
            this.configurationParameters.add(parameter);
        } else {
            //Ersetzen des Parameters, weil bereits vorhanden
            this.configurationParameters.remove(parameter);
            this.configurationParameters.add(parameter);
        }
    }

    /**
     * Liefert die Objektreferenz auf den Parameter
     *
     * @param name Name des Parameters
     * @return Objektreferenz auf den Parameter
     */
    public ConfigurationParameter getParameter(String name) {
        // Finden des Parameters unter den vorhandenen
        for (ConfigurationParameter parameter : this.configurationParameters) {
            if (parameter.getName().equalsIgnoreCase(name)) return parameter;
        }
        throw new NoSuchElementException();
    }

    /**
     * Liefert die komplette Parameterliste
     *
     * @return Parameterliste
     */
    public List<ConfigurationParameter> getParameters() {
        return this.configurationParameters;
    }

    public String getConfigName() {
        return this.configName;
    }

    @Override
    public String toString(){
        return this.getConfigName();
    }

}
