package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * 用于 ACO 算法的迭代终止<br>
 * <br>
 * 在主进程中使用，根据制定的条件取消迭代.<br>
 *
 * <p><img src="{@docRoot}/images/Termination.svg" alt=""></p>
 */
public abstract class AbstractTerminationCriterion {

    /**
     * 验证是否满足取消条件<br>
     *
     * @param iteration  迭代计数器
     * @param statistics 迭代过程统计
     * @return 如果不满足取消条件，则为 true
     */
    public abstract boolean checkTermination(int iteration, Statistics statistics);
}
