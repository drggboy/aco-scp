package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.SCPSolution;

/**
 *
 */
public class PheromoneUpdateOnSubsets extends
        AbstractPheromoneUpdate<PheromoneOnSubsets> {

    public PheromoneUpdateOnSubsets(PheromoneOnSubsets pheromoneStructure,
                                        AbstractSolutionQuality solutionQuality) {
        super(pheromoneStructure, solutionQuality);
    }

    @Override
    public void update(SCPSolution solution) {
        float delta = this.solutionQuality.getQuality(solution);
        for (Integer subset : solution.getSubsets()) {
            this.pheromoneStructure.addPheromone(subset, delta);
        }
    }
}
