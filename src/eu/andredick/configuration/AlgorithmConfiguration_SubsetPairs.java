package eu.andredick.configuration;

import eu.andredick.aco.algorithm.ACOAlgorithm;
import eu.andredick.aco.algorithm.ACOAnt;
import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.algorithm.AbstractAnt;
import eu.andredick.aco.combination.CombinationFactor;
import eu.andredick.aco.construct.AbstractConstructionStrategy;
import eu.andredick.aco.construct.ConstructionFromSubsets;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.localsearch.AbstractLocalSearchStrategy;
import eu.andredick.aco.localsearch.LocalSearchStrategyNone;
import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.masterprocess.MasterProcessBasic;
import eu.andredick.aco.nextstep.AbstractNextStepRule;
import eu.andredick.aco.nextstep.NextStepRuleOnSubsetPairs;
import eu.andredick.aco.pheromonassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneevaporation.PheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneinit.PheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneperception.PerceptionSimple;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.pheromoneupdate.PheromoneUpdateOnSubsetPairs;
import eu.andredick.aco.solutionquality.SolutionQualityMin;
import eu.andredick.aco.termination.TermCriterion;
import eu.andredick.scp.SCProblem;

public class AlgorithmConfiguration_SubsetPairs extends AbstractAlgorithmConfiguration {


    @Override
    public void prepareConfigParameters() {

        this.configName = "Alg_Konfig_Pairs";

        ConfigurationParameter<Float> phInitValue =
                new ConfigurationParameter<>("pheromonInitValue", 1f);
        this.addConfigurationParameter(phInitValue);

        ConfigurationParameter<Float> evapFactor =
                new ConfigurationParameter<>("evaporationFactor", 0.7f);
        this.addConfigurationParameter(evapFactor);
        evapFactor.setExpressions(new Float[]{0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f});

        this.getParameter("antsize").setCurrentValue(10);

    }

    @Override
    public AbstractAlgorithm create(SCProblem problem) {

        PheromoneOnSubsetPairs pheromoneStructure =
                new PheromoneOnSubsetPairs(problem.getStructure());

        float phInitValue = this.getParameter("pheromonInitValue").getCurrentValue().floatValue();
        AbstractPheromoneInit pheromoneInitRule =
                new PheromoneInit(phInitValue);

        float evapFactor = this.getParameter("evaporationFactor").getCurrentValue().floatValue();
        AbstractPheromoneEvaporation evaporationRule =
                new PheromoneEvaporation(evapFactor);

        pheromoneStructure.setEvaporationRule(evaporationRule);
        pheromoneStructure.setPheromoneInitRule(pheromoneInitRule);

        AbstractPheromoneUpdate updateRule =
                new PheromoneUpdateOnSubsetPairs(pheromoneStructure, new SolutionQualityMin());

        float alpha = 1f;
        float beta = 1f;

        AbstractPheromonePerception perceptionRule = new PerceptionSimple();
        HeuristicInfoSet heuristicInfoSet = new HeuristicInfoSet();
        //heuristicInfoSet.addRule(new HeuristicRuleWeights());
        //heuristicInfoSet.addRule(new HeuristicRuleBestSubset());

        AbstractNextStepRule nextStepRule =
                new NextStepRuleOnSubsetPairs(pheromoneStructure, perceptionRule, heuristicInfoSet, new CombinationFactor(alpha, beta));

        AbstractConstructionStrategy constructionStrategy = new ConstructionFromSubsets(nextStepRule);

        AbstractLocalSearchStrategy localSearchStrategy = new LocalSearchStrategyNone();

        int antSize = this.getParameter("antsize").getDefaultValue().intValue();
        AbstractAnt[] ants = new AbstractAnt[antSize];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new ACOAnt(problem, updateRule, constructionStrategy, localSearchStrategy);
        }

        TermCriterion termCriterion = new TermCriterion(100);

        AbstractMasterProcess masterProcess = new MasterProcessBasic(pheromoneStructure, ants, termCriterion);

        return new ACOAlgorithm(masterProcess);
    }

}
