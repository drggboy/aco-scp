package eu.andredick.configuration;

import eu.andredick.aco.algorithm.ACOAlgorithm;
import eu.andredick.aco.ant.ACOAnt;
import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.combination.CombinationFactor;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.construction.ConstructionFromSubsets;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.heuristic.HeuristicRuleBestSubset;
import eu.andredick.aco.heuristic.HeuristicRuleWeights;
import eu.andredick.aco.localsearch.AbstractLocalSearch;
import eu.andredick.aco.localsearch.LocalSearchAntCover;
import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.masterprocess.MasterProcessBasicParallel;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.nextstep.NextStepStrategyOnSubsetsStochastic;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneevaporation.PheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneinit.PheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneperception.PerceptionSimple;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.pheromoneupdate.PheromoneUpdateOnSubsets;
import eu.andredick.aco.solutionquality.SolutionQualityMin;
import eu.andredick.aco.termination.AbstractTerminationCriterion;
import eu.andredick.aco.termination.TerminationCriterionNew;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;

public class AlgorithmConfiguration_StigmergyHeuristLocal extends AbstractAlgorithmConfiguration {


    @Override
    public void prepareConfigParameters() {

        this.configName = "AlgorithmConfiguration_StigmergyHeurist";

        ConfigurationParameter<Float> phInitValue =
                new ConfigurationParameter<>("pheromonInitValue", 100f);
        this.addConfigurationParameter(phInitValue);
        //phInitValue.setExpressions(new Float[]{1f,10f,20f,30f,40f,50f,60f,70f,80f,90f});
        phInitValue.setExpressions(new Float[]{50f, 100f, 150f, 200f});

        ConfigurationParameter<Float> evapFactor =
                new ConfigurationParameter<>("evaporationFactor", 0.97f);
        this.addConfigurationParameter(evapFactor);
        //evapFactor.setExpressions(new Float[]{0.0f,0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f});
        //evapFactor.setExpressions(new Float[]{0.1f,0.3f,0.5f,0.7f,0.9f});

        ConfigurationParameter<Integer> maxIterations =
                new ConfigurationParameter<>("maxIterations", 100);
        this.addConfigurationParameter(maxIterations);

        this.getParameter("antsize").setCurrentValue(10);

    }

    @Override
    public AbstractAlgorithm create(SCProblem problem) {

        PheromoneOnSubsets pheromoneStructure =
                new PheromoneOnSubsets(problem);

        float phInitValue = this.getParameter("pheromonInitValue").getCurrentValue().floatValue();
        AbstractPheromoneInit pheromoneInitRule =
                new PheromoneInit(phInitValue);

        float evapFactor = this.getParameter("evaporationFactor").getCurrentValue().floatValue();
        AbstractPheromoneEvaporation evaporationRule =
                new PheromoneEvaporation(evapFactor);

        pheromoneStructure.setEvaporationRule(evaporationRule);
        pheromoneStructure.setPheromoneInitRule(pheromoneInitRule);

        AbstractPheromoneUpdate updateRule =
                new PheromoneUpdateOnSubsets(pheromoneStructure, new SolutionQualityMin());

        float alpha = 1f;
        float beta = 4f;

        HeuristicInfoSet heuristicInfoSet = new HeuristicInfoSet();
        heuristicInfoSet.addRule(new HeuristicRuleWeights());
        heuristicInfoSet.addRule(new HeuristicRuleBestSubset());

        AbstractPheromonePerception perceptionRule = new PerceptionSimple();

        AbstractNextStepStrategy nextStepRule =
                new NextStepStrategyOnSubsetsStochastic(pheromoneStructure, perceptionRule, heuristicInfoSet, new CombinationFactor(alpha, beta));

        AbstractConstruction constructionStrategy =
                new ConstructionFromSubsets(nextStepRule);

        AbstractLocalSearch localSearchStrategy =
                new LocalSearchAntCover();

        int antSize = this.getParameter("antsize").getCurrentValue().intValue();
        AbstractAnt[] ants = new AbstractAnt[antSize];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new ACOAnt<SCPSolution, SCProblem>(problem, updateRule, constructionStrategy, localSearchStrategy);
        }

        AbstractTerminationCriterion termCriterion =
                new TerminationCriterionNew(this.getParameter("maxIterations").getCurrentValue().intValue(), 50);

        AbstractMasterProcess masterProcess = new MasterProcessBasicParallel(pheromoneStructure, ants, termCriterion);

        return new ACOAlgorithm(masterProcess);
    }

}
