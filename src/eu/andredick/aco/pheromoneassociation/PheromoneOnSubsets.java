package eu.andredick.aco.pheromoneassociation;

import eu.andredick.scp.SCProblem;

public class PheromoneOnSubsets extends AbstractPheromoneAssociation<SCProblem> {

    private float[] pheromoneValues;

    public PheromoneOnSubsets(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()];
    }

    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.evaporationRule.evaporate(pheromoneValues[i]);
        }
    }

    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.pheromoneInitRule.initValue();
        }
    }

    @Override
    public float getPheromone(int i) {
        return this.pheromoneValues[i];
    }


    @Override
    public void addPheromone(int i, float ph_delta) {
        this.pheromoneValues[i] += ph_delta;
    }

}
