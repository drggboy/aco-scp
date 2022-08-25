package eu.andredick.aco.solutionquality;

import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;

/**
 * <b>解决方案质量函数的抽象组件</b><br>
 * 第 3.3.12 章，第 46 页，解的质量函数 <br>
 * <br>
 * 评价函数由信息素标记组件 ({@link AbstractPheromoneUpdate}) 调用。<br>
 * 在标记解时，需要将信息素的量与目标函数值分离。<br>
 * 因此，可以对信息素的量进行限制，例如，在一系列值内。<br>
 * 为了确定质量，需要了解解决方案的性质 ({@link AbstractSolution})。<br>
 * 因此，必须定义抽象质量函数的特征,
 * 通过确定问题以及解决方案，可以确定质量。<br>
 *
 * <p><img src="{@docRoot}/images/SolutionQuality.svg" alt=""></p>
 *
 * @param <S> 抽象类解的表达式
 */
public abstract class AbstractSolutionQuality<S extends AbstractSolution> {

    /**
     * 提供所提交解决方案的质量
     * @param solution 待评估的解决方案
     * @return 解决方案的质量
     */
    public abstract float getQuality(S solution);

}
