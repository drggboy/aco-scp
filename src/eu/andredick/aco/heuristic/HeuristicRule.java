package eu.andredick.aco.heuristic;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.List;

/**
 * <b>启发式信息类的接口</b><br>
 * 第 3.3.7 章，第 34 页，启发式信息<br>
 * <br>
 * 启发式信息组件的特征必须实现此接口.<br>
 * 该组件用于选择候选解 (参见 {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy})。<br>
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public interface HeuristicRule<S extends AbstractSolution> {

    /**
     * 提供启发式信息的值
     *
     * @param solution         蚂蚁工作过程中的部分解
     * @param availableSubsets 所有可用的候选方案
     * @param subset           要为其提供启发式信息的替代方法
     * @return 启发式信息的值
     */
    public float getValue(S solution, List<Integer> availableSubsets, Integer subset);

}
