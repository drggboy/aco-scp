package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>随机候选集选择</b> - 候选集选择的实现。<br>
 * <br>
 * 随机选择通过随机数{@code 0 <= z <= 1}确定候选集中的一个选择。<br>
 * 间隔 {@code [0.0, 1.0]} 被划分为与替代方案一样多的领域。
 * 每个范围的大小由其值确定。<br>
 * 组件没有参数。<br>
 * <br>
 * 备选选择组件已替换为设计启发式 {@link AbstractConstruction}
 * 从给定备选集（解决方案组件）中选择一个备选项.<br>
 * 替代方案的选择基于启发式信息 {@link HeuristicInfoSet}和<br>
 * 感知的信息素浓度 {@link AbstractPheromonePerception}。<br>
 * 替代方案的值由启发式信息和感知的信息素浓度通过组合功能 {@link CombinationRule}形成.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 * */
public class NextStepStrategyOnSubsetsStochastic extends
        AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution> {

    /**
     * 构造函数
     *
     * @param pheromonesStructure 信息素关联
     * @param perceptionRule      信息素感知
     * @param heuristics          启发式信息
     * @param combinationRule     组合功能
     */
    public NextStepStrategyOnSubsetsStochastic(PheromoneOnSubsets pheromonesStructure,
                                               AbstractPheromonePerception perceptionRule,
                                               HeuristicInfoSet heuristics,
                                               CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * 随机选择通过随机数{@code 0 <= z <= 1}确定候选集中的一个选择。
     * 间隔 {@code [0.0, 1.0]} 被划分为与替代方案一样多的领域.
     * 每个范围的大小由其替代值确定.
     *
     * @param solution         蚂蚁的部分解
     * @param availableSubsets 可用的替代方案
     * @return 选择结果
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        // 具有不同大小部分的轮盘
        float[] summands = new float[availableSubsets.size()];
        float sumSummands = 0f;
        for (int k = 0; k < summands.length; k++) {
            int subset = availableSubsets.get(k);
            float ph_k = this.perceptionRule.getPerceptionValue(this.pheromoneStructure.getPheromone(subset));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset);
            float summand = this.combinationRule.combine(ph_k, hi_k);
            sumSummands += summand;
            summands[k] = summand;
        }

        // 所有备选值 = null => 随机选择
        if (sumSummands == 0f)
            return availableSubsets.get(ThreadLocalRandom.current().nextInt(availableSubsets.size()));

        // 轮盘转动
        float z = ThreadLocalRandom.current().nextFloat() * sumSummands;
        float sumCounter = 0f;
        for (int k = 0; k < summands.length; k++) {
            sumCounter += summands[k];
            if (z < sumCounter) {
                return availableSubsets.get(k);
            }
        }
        return null;
    }

}
