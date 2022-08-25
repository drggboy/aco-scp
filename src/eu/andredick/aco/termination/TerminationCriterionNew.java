package eu.andredick.aco.termination;

import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.tools.ArrayTools;

/**
 * <b>终止标准</b> 用于 ACO 算法的迭代<br>
 * <br>
 * 这些特征由主进程调用，以取消迭代。<br>
 * 当迭代次数超过最大值时满足取消条件。 <br>
 * 或者自最佳解决方案以来超过的最大迭代次数。
 *
 * <p><img src="{@docRoot}/images/Termination.svg" alt=""></p>
 */
public class TerminationCriterionNew extends AbstractTerminationCriterion {

    /**
     * 最大迭代次数.
     */
    private int maxIterations;

    /**
     * 最大迭代次数，因为没有找到更好的解决方案.
     */
    private int bestValueAgo;

    /**
     * 构造函数
     *
     * @param maxIterations 最大迭代次数
     * @param bestValueAgo  最大迭代次数，因为没有找到更好的解决方案.
     */
    public TerminationCriterionNew(int maxIterations, int bestValueAgo) {
        this.maxIterations = maxIterations;
        this.bestValueAgo = bestValueAgo;
    }

    /**
     * 验证是否满足取消条件.<br>
     * 当迭代次数超过最大值时满足取消条件 <br>
     * 或自超过最佳解决方案以来的最大迭代次数.
     *
     * @param iteration  迭代计数器
     * @param statistics 迭代历史记录统计信息
     * @return 如果迭代次数不超过最大值，则为 true
     */
    @Override
    public boolean checkTermination(int iteration, Statistics statistics) {
        if (iteration == 0) return true;
        Float globMinVal = statistics.getGlobalMinValue();
        int iterationnOfBestFoundValue = ArrayTools.getIndexOfMinValue(statistics.getIterationMinValuesArray());
        return iteration - iterationnOfBestFoundValue < bestValueAgo && iteration < maxIterations;
    }
}
