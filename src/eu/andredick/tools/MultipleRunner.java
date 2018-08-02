package eu.andredick.tools;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.scp.SCProblem;


public class MultipleRunner {

    public static Statistics[] run(AbstractAlgorithmConfiguration configuration, SCProblem problem, int times) {

        System.out.println("MultipleRunner: " + times);

        Statistics[] statistics = new Statistics[times];
        AbstractAlgorithm[] algorithms = new AbstractAlgorithm[times];
        Thread[] threads = new Thread[times];

        for (int i = 0; i < times; i++) {
            algorithms[i] = configuration.create(problem);
            threads[i] = new Thread(algorithms[i]);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {

            // Warten bis Threads beendet sind
            for (Thread thread : threads) {
                thread.join();
            }

            for (int i = 0; i < algorithms.length; i++) {
                statistics[i] = algorithms[i].getStatistics();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return statistics;
    }


}
