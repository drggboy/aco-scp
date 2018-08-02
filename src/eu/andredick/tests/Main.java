package eu.andredick.tests;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.configuration.AlgorithmConfiguration_Stigmergy;
import eu.andredick.configuration.AlgorithmConfiguration_SubsetPairs;
import eu.andredick.orlib.OrlibConverter;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ChartTools;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        // Holen einer Probleminstanz
        SCProblem problem = OrlibConverter.getProblem("scpd1");

        problem.isConsistent();

        // Es können mehrere Konfigurationen des Algorithmus gegeneinander getestet werden
        // Konfig A
        AbstractAlgorithmConfiguration config1 = new AlgorithmConfiguration_Stigmergy();
        AbstractAlgorithm abstractAlgorithm1 = config1.create(problem);
        Thread thread1 = new Thread(abstractAlgorithm1);

        // Konfig B
        AbstractAlgorithmConfiguration config2 = new AlgorithmConfiguration_SubsetPairs();
        AbstractAlgorithm abstractAlgorithm2 = config2.create(problem);
        Thread thread2 = new Thread(abstractAlgorithm2);

        // Erzeugen von Threads für parallele Ausführung
        thread1.start();
        thread2.start();


        try {

            // Warten bis Threads beendet sind
            thread1.join();
            thread2.join();

            System.out.println("thread ende");

            ChartTools chart = new ChartTools("Benchmark", "Interation", "z_best");

            chart.addDataSeries(config1.getConfigName(), abstractAlgorithm1.getStatistics().getIterationMinValuesArray());
            chart.addDataSeries(config2.getConfigName(), abstractAlgorithm2.getStatistics().getIterationMinValuesArray());

            chart.saveChartAsSVG(new File("chart.svg"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
