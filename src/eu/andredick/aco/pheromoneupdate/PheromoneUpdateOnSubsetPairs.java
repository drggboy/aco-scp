package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromonassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.Solution;

import java.util.List;

public class PheromoneUpdateOnSubsetPairs extends
        AbstractPheromoneUpdate<PheromoneOnSubsetPairs> {

    public PheromoneUpdateOnSubsetPairs(PheromoneOnSubsetPairs pheromoneStructure,
                                            AbstractSolutionQuality solutionQuality) {
        super(pheromoneStructure, solutionQuality);
    }

    @Override
    public void update(Solution solution) {
        float delta = this.solutionQuality.getQuality(solution);
        List<Integer> varsList = solution.getSubsets();
        int varListSize = varsList.size();
        for (int i = 0; i < varListSize; i++) {
            for (int j = i + 1; j < varListSize; j++) {
                pheromoneStructure.addPheromone(varsList.get(i), varsList.get(j), delta);
                pheromoneStructure.addPheromone(varsList.get(j), varsList.get(i), delta);
            }
        }
        pheromoneStructure.addPheromone(varsList.get(0), varsList.get(0), delta);
    }
}