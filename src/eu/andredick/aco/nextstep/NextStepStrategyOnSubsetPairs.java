package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>随机 / 概率备选</b> - 替代选择的成分表达，信息素与子集对的关联<br>
 * <br>
 * 随机备选方案通过随机数确定给定备择方案集中的一个选择 {@code 0 <= z <= 1}.
 * 间隔 {@code [0.0, 1.0]} 分为尽可能多的区域，只要有替代方案。
 * 每个范围的大小由其替代值确定.<br>
 * 组件没有参数.<br>
 * <br>
 * 备选选择组件已替换为设计启发式 {@link AbstractConstruction}
 * 从给定备选项集（解决方案组件）中选择一个备选项.<br>
 * 替代方案的选择基于启发式信息 {@link HeuristicInfoSet} 和
 * 感知的信息素浓度 {@link AbstractPheromonePerception}, 分配给备选方案或为备选方案计算.<br>
 * 通过组合功能 {@link CombinationRule} 替代方案的值由启发式信息和感知的信息素浓度形成.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsetPairs extends
        AbstractNextStepStrategy<PheromoneOnSubsetPairs, SCPSolution> {

    /**
     * 构造函数
     *
     * @param pheromonesStructure 信息素与子集对的关联
     * @param perceptionRule      信息素感知
     * @param heuristics          启发式信息
     * @param combinationRule     组合功能
     */
    public NextStepStrategyOnSubsetPairs(PheromoneOnSubsetPairs pheromonesStructure,
                                         AbstractPheromonePerception perceptionRule,
                                         HeuristicInfoSet heuristics,
                                         CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * 随机备选方案通过随机数确定给定备择方案集中的一个选择 {@code 0 <= z <= 1}.
     * 间隔 {@code [0.0, 1.0]} 被划分为与有替代方案一样多的领域.
     * 每个范围的大小由其替代值确定.<br>
     * <br>
     * 当信息素与成对的子集相关联时 (see {@link PheromoneOnSubsetPairs})
     *
     * @param solution         蚂蚁的部分解
     * @param availableSubsets 可用的替代方案
     * @return 选择结果
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        // 如果
        List<Integer> varsList = solution.getSubsets();
        boolean firstSubsetInSolution = varsList.isEmpty();
        Integer lastSubset = firstSubsetInSolution ? null : solution.getSubsets().get(solution.getSubsets().size() - 1);

        //具有不同大小部分的幸运之轮（在地上意义上）
        float[] summands = new float[availableSubsets.size()];
        float sumSummands = 0f;
        for (int k = 0; k < summands.length; k++) {

            int subset_j = availableSubsets.get(k);
            int subset_i = firstSubsetInSolution ? subset_j : lastSubset;

            float ph_k = this.perceptionRule.getPerceptionValue(this.pheromoneStructure.getPheromone(subset_i, subset_j));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset_j);
            float summand = this.combinationRule.combine(ph_k, hi_k);
            sumSummands += summand;
            summands[k] = summand;
        }

        // 所有备选值 = 空  => 随机选择
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
