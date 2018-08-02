package eu.andredick.tools;

import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.configuration.ConfigurationParameter;
import eu.andredick.scp.SCProblem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse für Parameter-Scan
 * Es werden Kombinationen der Parameter einer Alogrithmus-Konfiguration getestet.
 * Jeder Parameter hat eine endliche Anzahl an Ausprägungen.
 * Der Mechanismus entspricht dem rekursiven Traversieren eines Baumes.
 * Bei jeder Kombination wird ein oder mehrere Instanzen eines konfigurierten Algorithmus erzeugt und ausgeführt.
 * Der beste Zielfunktionswert entspricht der besten Parameter-Kombination.
 */
public class ParameterTraverser {

    AbstractAlgorithmConfiguration configuration;
    SCProblem problem;
    List<ConfigurationParameter> parameters;


    private Float currentBestValue;
    private Map<String, Number> currentBestParameters;

    public ParameterTraverser(AbstractAlgorithmConfiguration configuration, SCProblem problem) {
        this.configuration = configuration;
        this.problem = problem;

        parameters = configuration.getParameters();
        this.currentBestValue = null;


    }

    public void scan() {
        System.out.println("ParameterTraverser.scan... ");
        if (this.parameters.size() > 0) {
            traverseParameter(0);
        }
    }

    /**
     * Rekursive Methode zu Traversieren der Parameterliste mit ihren Ausprägungen
     * Ein Endkonten ist der letzte Parameter der Parameterliste. Wenn der Endknoten erreicht ist,
     * ist eine Parameterkombination festgelegt. Im Endknoten kann der Algorithmus mit der Parameterkombination
     * erzeugt und ausgeführt werden.
     *
     * @param parameterListIndex: Index des Parameters in der Parameterliste
     */
    private void traverseParameter(int parameterListIndex) {

        ConfigurationParameter parameter = parameters.get(parameterListIndex);

        int expressionsSize = parameter.getExpressionsSize();

        for (int i = 0; i < expressionsSize; i++) {
            parameter.setCurrentValueOnIndex(i);

            if (parameterListIndex == (parameters.size() - 1)) {
                Statistics[] statistics = MultipleRunner.run(configuration, problem, 10);
                float bestValue = ArrayTools.getMinValue(ArrayTools.getAverageValuesOfArrays(Criterions.getArraysOfIterationBestValues(statistics)));

                if (currentBestValue == null || bestValue < currentBestValue) {
                    currentBestValue = bestValue;
                    this.createParameterMap();
                    this.currentBestParameters.put("z_best_average", this.currentBestValue);
                }
            } else {
                traverseParameter(parameterListIndex + 1);
            }
        }

    }

    private void createParameterMap() {
        currentBestParameters = new HashMap<>();
        for (ConfigurationParameter p : parameters) {
            currentBestParameters.put(p.getName(), p.getCurrentValue());
        }
    }

    public Map<String, Number> getBestParameters() {
        return this.currentBestParameters;
    }

    public float getBestValue() {
        return this.currentBestValue;
    }

}
