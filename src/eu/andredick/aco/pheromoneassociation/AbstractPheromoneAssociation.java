package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.problem.AbstractProblem;

public abstract class AbstractPheromoneAssociation<P extends AbstractProblem> {

    protected P problem;
    protected AbstractPheromoneEvaporation evaporationRule;
    protected AbstractPheromoneInit pheromoneInitRule;

    public AbstractPheromoneAssociation(P problem) {
        this.problem = problem;
    }

    public abstract void evaporatePheromones();

    public abstract void initPheromones();

    public void setEvaporationRule(AbstractPheromoneEvaporation evaporationRule) {
        this.evaporationRule = evaporationRule;
    }

    public void setPheromoneInitRule(AbstractPheromoneInit pheromoneInitRule) {
        this.pheromoneInitRule = pheromoneInitRule;
    }

    public abstract float getPheromone(int i);


    public abstract void addPheromone(int i, float ph_delta);

}