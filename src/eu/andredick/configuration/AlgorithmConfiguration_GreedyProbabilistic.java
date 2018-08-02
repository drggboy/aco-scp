package eu.andredick.configuration;

import eu.andredick.aco.algorithm.ACOAlgorithm;
import eu.andredick.aco.algorithm.ACOAnt;
import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.algorithm.AbstractAnt;
import eu.andredick.aco.combination.CombinationFactor;
import eu.andredick.aco.construct.AbstractConstructionStrategy;
import eu.andredick.aco.construct.ConstructionFromSubsets;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.heuristic.HeuristicRuleBestSubset;
import eu.andredick.aco.heuristic.HeuristicRuleWeights;
import eu.andredick.aco.localsearch.AbstractLocalSearchStrategy;
import eu.andredick.aco.localsearch.LocalSearchStrategyNone;
import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.masterprocess.MasterProcessBasic;
import eu.andredick.aco.nextstep.AbstractNextStepRule;
import eu.andredick.aco.nextstep.NextStepRuleOnSubsets;
import eu.andredick.aco.pheromonassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneevaporation.PheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneinit.PheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneperception.PerceptionSimple;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.pheromoneupdate.PheromoneUpdateOnSubsets;
import eu.andredick.aco.solutionquality.SolutionQualityMin;
import eu.andredick.aco.termination.TermCriterion;
import eu.andredick.scp.SCProblem;

public class AlgorithmConfiguration_GreedyProbabilistic extends AbstractAlgorithmConfiguration {


    @Override
    public void prepareConfigParameters() {

        this.configName = "AlgorithmConfiguration_Greedy";

        ConfigurationParameter<Float> phInitValue =
                new ConfigurationParameter<>("pheromonInitValue", 1f);
        this.addConfigurationParameter(phInitValue);

        ConfigurationParameter<Float> evapFactor =
                new ConfigurationParameter<>("evaporationFactor", 1f);
        this.addConfigurationParameter(evapFactor);

        ConfigurationParameter<Float> alpha =
                new ConfigurationParameter<>("alpha", 0f);
        this.addConfigurationParameter(alpha);

        ConfigurationParameter<Float> beta =
                new ConfigurationParameter<>("beta", 1f);
        this.addConfigurationParameter(beta);

        ConfigurationParameter<Float> q0 =
                new ConfigurationParameter<>("q0", 0.02f);
        this.addConfigurationParameter(q0);
        //q0.setExpressions(new Float[]{0.005f, 0.01f, 0.015f, 0.02f, 0.025f, 0.03f, 0.035f, 0.04f});
        q0.setExpressions(new Float[]{0.01f, 0.02f, 0.03f, 0.04f});

        ConfigurationParameter<Integer> maxIterations =
                new ConfigurationParameter<>("maxIterations", 400);
        this.addConfigurationParameter(maxIterations);

        this.getParameter("antsize").setCurrentValue(10);

    }

    @Override
    public AbstractAlgorithm create(SCProblem problem) {

        PheromoneOnSubsets pheromoneStructure =
                new PheromoneOnSubsets(problem.getStructure());

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

        float alpha = this.getParameter("alpha").getCurrentValue().floatValue();
        float beta = this.getParameter("beta").getCurrentValue().floatValue();
        float q0 = this.getParameter("q0").getCurrentValue().floatValue();

        HeuristicInfoSet heuristicInfoSet = new HeuristicInfoSet();
        heuristicInfoSet.addRule(new HeuristicRuleWeights());
        heuristicInfoSet.addRule(new HeuristicRuleBestSubset());

        AbstractPheromonePerception perceptionRule = new PerceptionSimple();

        AbstractNextStepRule nextStepRule =
                new NextStepRuleOnSubsets(
                        pheromoneStructure, perceptionRule, heuristicInfoSet, new CombinationFactor(alpha, beta), q0);

        AbstractConstructionStrategy constructionStrategy =
                new ConstructionFromSubsets(nextStepRule);

        AbstractLocalSearchStrategy localSearchStrategy =
                new LocalSearchStrategyNone();

        int antSize = this.getParameter("antsize").getCurrentValue().intValue();

        AbstractAnt[] ants = new AbstractAnt[antSize];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new ACOAnt(problem, updateRule, constructionStrategy, localSearchStrategy);
        }

        TermCriterion termCriterion = new TermCriterion(this.getParameter("maxIterations").getCurrentValue().intValue());

        AbstractMasterProcess masterProcess =
                new MasterProcessBasic(pheromoneStructure, ants, termCriterion);

        return new ACOAlgorithm(masterProcess);
    }

}
