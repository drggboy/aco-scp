package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;

/**
 * <b>终止标准的特征</b> 用于 ACO 算法的迭代<br>
 * <br>
 * 这些特征用于主进程以取消迭代.<br>
 * 当迭代次数超过最大值时满足取消条件。
 *
 * <p><img src="{@docRoot}/images/Termination.svg" alt=""></p>
 */
public class TerminationCriterion extends AbstractTerminationCriterion {

    /**
     * 最大迭代次数
     */
    private int maxIterations;

    /**
     * 构造函数
     *
     * @param maxIterations 最大迭代次数
     */
    public TerminationCriterion(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * 验证是否满足取消条件。<br>
     * 当迭代次数超过最大值时满足取消条件.
     *
     * @param iteration:  迭代计数器
     * @param statistics: 迭代历史记录统计信息
     * @return 如果迭代次数不超过最大值，则为 true
     */
    @Override
    public boolean checkTermination(int iteration, Statistics statistics) {
        return iteration < maxIterations;
    }
}
