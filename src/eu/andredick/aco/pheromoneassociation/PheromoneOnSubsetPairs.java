package eu.andredick.aco.pheromoneassociation;

import eu.andredick.scp.SCProblem;

public class PheromoneOnSubsetPairs extends AbstractPheromoneAssociation<SCProblem> {

    private float[][] pheromoneValues;

    public PheromoneOnSubsetPairs(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()][problem.getStructure().subsetsSize()];
    }

    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.evaporationRule.evaporate(pheromoneValues[i][j]);
            }
        }
    }

    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.pheromoneInitRule.initValue();
            }
        }
    }

    @Override
    public float getPheromone(int i) {
        System.out.println("PheromoneOnSubsetPairs.getPheromone / nicht erlaubt!");
        return 0;
    }


    public float getPheromone(int i, int j) {
        return this.pheromoneValues[i][j];
    }

    @Override
    public void addPheromone(int i, float ph_delta) {
        System.out.println("PheromoneOnSubsetPairs.addPheromone / nicht erlaubt!");
    }


    public void addPheromone(int i, int j, float ph_delta) {
        this.pheromoneValues[i][j] += ph_delta;
    }

}
