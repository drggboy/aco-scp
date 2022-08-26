package eu.andredick.aco.heuristic;

import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>启发式信息组件的实现</b><br>
 * <br>
 * 启发式信息的一种实现  <b>H_dyn</b><br>
 * 该组件用于选择候选方案 (参见 {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy})。<br>
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicRuleBestSubset implements HeuristicRule<SCPSolution> {

    /**
     * 通过选择子集来计算基本元素的可能新覆盖数.<br>
     * 对于每个由子集<i>subset</i>覆盖的基本元素，<br>
     * 判断其是否已经被部分解中的部分子集所覆盖，<br>
     * 暂未被覆盖的所有集合元素的总和是启发式信息的价值。
     *
     * @param solution:         蚂蚁寻优过程中的部分解
     * @param availableSubsets  所有可用子集
     * @param subset            待求的子集
     * @return 启发式信息的值
     */
    @Override
    public float getValue(SCPSolution solution, List<Integer> availableSubsets, Integer subset) {

        // 确定子集中包含的基本元素
        List<Integer> elementsInSubset = solution.getProblem().getStructure().getElementsInSubset(subset);

        // 启动求和计数器
        int sum = 0;

        // 对所有预定的基本元素进行迭代
        for (Integer e : elementsInSubset) {

            // 如果当前基本元素“e”尚未被部分解覆盖，
            // 然后递增计数器“总和”
            if (!solution.isElementCovered(e)) sum++;
        }

        // 返回总和
        return ((float) sum);
    }
}
