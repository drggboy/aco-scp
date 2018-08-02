package eu.andredick.aco.pheromoneinit;

public class PheromoneInit implements AbstractPheromoneInit {

    private float pheromoneInitValue;

    public PheromoneInit(float pheromoneInitValue) {
        this.pheromoneInitValue = pheromoneInitValue;
    }


    @Override
    public float initValue() {
        return this.pheromoneInitValue;
    }
}
