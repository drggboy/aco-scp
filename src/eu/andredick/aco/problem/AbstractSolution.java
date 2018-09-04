package eu.andredick.aco.problem;

public abstract class AbstractSolution<P extends AbstractProblem> {

    public abstract P getProblem();

    public abstract float getValue();

    public abstract AbstractSolution createNew();
}
