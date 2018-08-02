package eu.andredick.tests;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.configuration.AlgorithmConfiguration_Stigmergy;
import eu.andredick.orlib.OrlibConverter;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ChartTools;

import java.io.File;

public class Main2 {

    public static void main(String[] args) {

        SCProblem problem = OrlibConverter.getProblem("scp41");
        problem.isConsistent();


        AbstractAlgorithmConfiguration config = new AlgorithmConfiguration_Stigmergy();
        AbstractAlgorithm alg = config.create(problem);
        alg.run();
        Statistics statistics = alg.getStatistics();

        float[] floatArray = statistics.getIterationMinValuesArray();

        ChartTools chart = new ChartTools(null, "Interation t", "z");
        chart.addDataSeries("z_best(t)", floatArray);
        chart.saveChartAsSVG(new File("testchart"));

    }
}
