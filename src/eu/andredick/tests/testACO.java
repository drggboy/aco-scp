package eu.andredick.tests;

import eu.andredick.orlib.OrlibConverter;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ChartTools;
import eu.andredick.tools.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class testACO {

    public static void main(String[] args) {

        SCProblem problem = OrlibConverter.getProblem("scp46");
        float[] weights = problem.getObjectiveFunction().getWeights();
        double[] pheromones = new double[problem.getStructure().subsetsSize()];

        int maxIterations = 100;
        double evapFactor = 0.2;
        double phInitValue = 1.;
        int antSize = 10;


        ArrayList<Integer>[] solution = new ArrayList[antSize];

        for (int s = 0; s < solution.length; s++) {
            solution[s] = new ArrayList<>();
        }

        Double[] objValue = new Double[antSize];

        for (int p = 0; p < pheromones.length; p++) {
            pheromones[p] = phInitValue;
        }

        float[] bestIterValues = new float[maxIterations];

        for (int i = 0; i < maxIterations; i++) {

            // Für alle Ameisen der Population
            for (int k = 0; k < antSize; k++) {

                // Konstruiere Lösung
                List<Integer> elements = Tools.getIndexList(problem.getStructure().elementsSize());
                List<Integer> subsets = Tools.getIndexList(problem.getStructure().subsetsSize());

                // Solange es noch unüberdeckten Elemente gibt
                while (!elements.isEmpty()) {

                    // Subset auswählen
                    Integer changedSubset = null;
                    double[] summand = new double[subsets.size()];
                    double sumAllSummands = 0.;
                    for (int s = 0; s < summand.length; s++) {
                        summand[s] = pheromones[subsets.get(s)] * 1. / weights[subsets.get(s)];
                        sumAllSummands = sumAllSummands + summand[s];
                    }
                    double z = ThreadLocalRandom.current().nextDouble() * sumAllSummands;
                    double counter = 0.;
                    for (int s = 0; s < summand.length; s++) {
                        counter = counter + summand[s];
                        if (z < counter) {
                            changedSubset = subsets.get(s);
                            break;
                        }
                    }

                    // Subset zur Lösung hinzufügen
                    solution[k].add(changedSubset);

                    // Subset aus den verbleibenden Subsets entnehmen
                    subsets.remove((Integer) changedSubset);

                    // Alle durch das Subset überdeckten Element aus der Menge der noch nicht überdeckten Elemente löschen
                    elements.removeAll(problem.getStructure().getElementsInSubset(changedSubset));

                }
            }

            // Evaluiere die Lösungen aller ameisen
            for (int k = 0; k < antSize; k++) {
                double ov = 0;
                for (int s = 0; s < solution[k].size(); s++) {
                    ov = ov + weights[solution[k].get(s)];
                }
                objValue[k] = ov;
            }

            // Evaporation des Pheromons
            for (int p = 0; p < pheromones.length; p++) {
                pheromones[p] = pheromones[p] * evapFactor;
            }

            // Update des Pheromones
            for (int k = 0; k < antSize; k++) {
                for (Integer subset : solution[k]) {
                    pheromones[subset] = pheromones[subset] + 1. / objValue[k];
                }
            }

            // Statistik
            bestIterValues[i] = objValue[0].floatValue();
            for (int k = 1; k < antSize; k++) {
                if (objValue[k] < bestIterValues[i]) bestIterValues[i] = objValue[0].floatValue();
            }

            // Reset Solutions
            for (int k = 0; k < antSize; k++) {
                solution[k].clear();
            }

        }

        // Print
        for (Double o : objValue) {
            System.out.print(" " + o);
        }

        ChartTools chart = new ChartTools("Prozedurale Implementation", "Interation", "z");
        chart.addDataSeries("best_Iter_values", bestIterValues);
        chart.saveChartAsSVG(new File("testACO.pdf"));

    }
}
