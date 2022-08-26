package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>确定性备选方案选择</b> - 候选方案选择的实现<br>
 * <br>
 * 侯选方案的确定性选择始终提供评价最高的备选方案。<br>
 * 此组件没有参数。<br>
 * <br>
 * 备选选择组件已替换为设计启发式 {@link AbstractConstruction}
 * 用于从给定的候选集中选择分量.<br>
 * 替代方案的选择基于为备选方案计算的启发式信息 {@link HeuristicInfoSet}
 * 或分配给备选方案所感知的信息素浓度 {@link AbstractPheromonePerception}。<br>
 * 替代方案的值通过组合功能 {@link CombinationRule}由启发式信息和感知的信息素浓度形成。<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsetsDeterministic extends
        AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution> {

    /**
     * 构造函数
     *
     * @param pheromonesStructure 信息素关联
     * @param perceptionRule      信息素感知
     * @param heuristics          启发式信息
     * @param combinationRule     组合功能
     */
    public NextStepStrategyOnSubsetsDeterministic(PheromoneOnSubsets pheromonesStructure,
                                                  AbstractPheromonePerception perceptionRule,
                                                  HeuristicInfoSet heuristics,
                                                  CombinationRule combinationRule) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * 确定性地选择，选择具有最高值的替代方案。<br>
     * 每个替代项的价值是根据相关的感知信息素浓度和启发式信息确定的。<br>
     * 组合功能 ({@link CombinationRule}) 用于结合两个影响变量。<br>
     *
     * @param solution         蚂蚁的部分解
     * @param availableSubsets 可用的替代方案
     * @return 选择结果
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        float maxValue = 0f;
        int indexWithMaxValue = -1;

        // 遍历所有可用的备选项以确定具有最高值的备选项
        for (int k = 0; k < availableSubsets.size(); k++) {
            int subset = availableSubsets.get(k);
            float ph_k = this.perceptionRule.getPerceptionValue(this.pheromoneStructure.getPheromone(subset));
            float hi_k = this.heuristics.getValue(solution, availableSubsets, subset);
            float value = this.combinationRule.combine(ph_k, hi_k);
            if (value > maxValue) {
                maxValue = value;
                indexWithMaxValue = k;
            }
        }
        return availableSubsets.get(indexWithMaxValue);
    }

}
