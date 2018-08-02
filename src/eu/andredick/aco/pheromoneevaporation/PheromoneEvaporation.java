package eu.andredick.aco.pheromoneevaporation;

public class PheromoneEvaporation implements AbstractPheromoneEvaporation {

    private float evaporationFactor;

    public PheromoneEvaporation(float evaporationFactor) {
        this.evaporationFactor = evaporationFactor;
    }

    @Override
    public float evaporate(float pheromoneValue) {
        return pheromoneValue * evaporationFactor;
    }
}
