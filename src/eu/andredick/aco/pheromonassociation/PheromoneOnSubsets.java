package eu.andredick.aco.pheromonassociation;

import eu.andredick.scp.Structure;

public class PheromoneOnSubsets extends AbstractPheromoneAssociation {

    private float[] pheromoneValues;

    public PheromoneOnSubsets(Structure structure) {
        super(structure);
        pheromoneValues = new float[structure.subsetsSize()];
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
