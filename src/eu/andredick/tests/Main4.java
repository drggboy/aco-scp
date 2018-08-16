package eu.andredick.tests;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.configuration.AlgorithmConfiguration_Greedy;
import eu.andredick.configuration.AlgorithmConfiguration_Stigmergy;
import eu.andredick.orlib.OrlibConverter;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ArrayTools;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.MatlabTheme;
import org.knowm.xchart.style.Styler;

import java.util.LinkedList;
import java.util.List;

public class Main4 {

    public static void main(String[] args) {

        // Holen einer Probleminstanz
        String instanceName = "scp410";

        SCProblem problem = OrlibConverter.getProblem(instanceName);
        //SCProblem problem = OrlibConverter.getProblem("scp41");

        //problem.getObjectiveFunction().initUniWeights(problem.getStructure().subsetsSize());
        problem.isConsistent();

        // Exakte Lösung finden
        Float exactValue = OrlibConverter.getExactValue(instanceName);


        AbstractAlgorithmConfiguration greedyConfiguration = new AlgorithmConfiguration_Greedy();
        AbstractAlgorithm greedyAlgorithm = greedyConfiguration.create(problem);
        greedyAlgorithm.run();
        SCPSolution greadySolution = greedyAlgorithm.getStatistics().getGlobalMinSolution();

        System.out.println("GreadySolution: " + problem.getObjectiveFunction().getValue(greadySolution));
        System.out.println("GreadySolution ist zulässig: " + greadySolution.isPermissible());


        // Es können mehrere Konfigurationen des Algorithmus gegeneinander getestet werden
        // Konfig A
        AbstractAlgorithmConfiguration config = new AlgorithmConfiguration_Stigmergy();

        List<AbstractAlgorithm> algorithms = new LinkedList<>();
        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            AbstractAlgorithm alg = config.create(problem);
            algorithms.add(alg);
            threads.add(new Thread(alg));
        }

        for (Thread thread : threads) {
            thread.start();
        }


        try {

            // Warten bis Threads beendet sind
            for (Thread thread : threads) {
                thread.join();
            }

            List<float[]> values = new LinkedList<>();
            for (AbstractAlgorithm alg : algorithms) {
                values.add(ArrayTools.getProgressiveMinValueArray(alg.getStatistics().getIterationMinValuesArray()));
            }

            System.out.println("threads ende");

            // Darstellung der Ergebnisse im Chart
            XYChart chart = new XYChart(500, 400);
            chart.setTitle("Sample Chart");
            chart.setXAxisTitle("X");
            chart.setXAxisTitle("Y");

            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
            chart.getStyler().setAxisTitlesVisible(false);
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
            chart.getStyler().setTheme(new MatlabTheme());
/*

            XYSeries series1 = chart.addSeries("Durchschnitt", null, ArrayTools.getAverageValuesOfArrays(values));
            series1.setMarker(SeriesMarkers.NONE);
            XYSeries series2 = chart.addSeries("Beste", null, ArrayTools.getMaxValuesOfArrays(values));
            series2.setMarker(SeriesMarkers.NONE);
            XYSeries series3 = chart.addSeries("Schlechteste", null, ArrayTools.getMinValuesOfArrays(values));
            series3.setMarker(SeriesMarkers.NONE);

            XYSeries series4 = chart.addSeries("Optimal", null, ArrayTools.getFloatArray( ArrayTools.getMaxValuesOfArrays(values).length, exactValue));
            series4.setMarker(SeriesMarkers.NONE);

            XYSeries series5 = chart.addSeries("Gready", null, ArrayTools.getFloatArray( ArrayTools.getMaxValuesOfArrays(values).length, problem.getObjectiveFunction().getValue(greadySolution)));
            series5.setMarker(SeriesMarkers.NONE);
*/

            // Show it
            new SwingWrapper(chart).displayChart();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
