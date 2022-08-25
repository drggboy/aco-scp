package eu.andredick.aco.heuristic;

import eu.andredick.scp.ObjectiveFunction;
import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>启发式信息组件的实现</b><br>
 * 第 3.3.7 章，第 34 页，启发式信息<br>
 * <br>
 * 启发式信息的一种实现 <b>H_stat</b><br>
 * 该组件用于选择候选方案 (参见 {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy})。<br>
 * 通过权重，确定启发式信息的值。
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicRuleWeights implements HeuristicRule<SCPSolution> {

    /**
     * 确定子集<i>subset</i>的成本。<br>
     * 子集的成本由 SCP 的目标函数的权重向量定义。
     * @see ObjectiveFunction#getWeights()
     *
     * @param solution         蚂蚁设计过程中的部分解
     * @param availableSubsets 所有可用子集
     * @param subset           待求的子集
     * @return 启发式信息的值
     */
    @Override
    public float getValue(SCPSolution solution, List<Integer> availableSubsets, Integer subset) {
        // 权重越小，启发式值越大，越容易被选中
        return 1f / solution.getProblem().getObjectiveFunction().getWeights()[subset];

    }
}
