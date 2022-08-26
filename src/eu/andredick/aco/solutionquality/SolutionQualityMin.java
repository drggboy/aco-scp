package eu.andredick.aco.solutionquality;

import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.scp.SCPSolution;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;

/**
 * <b>SCP解决方案的简单质量函数</b><br>
 * <br>
 * 简单质量函数与目标函数值的倒数成正比.<br>
 * 为了增加值范围，将目标函数值的倒数乘以子集数.<br>
 * <br>
 * 质量功能由信息素标记组件 ({@link AbstractPheromoneUpdate}) 调用。<br>
 * 在标记解时，需要将信息素的量与目标函数值分离。<br>
 * 因此，信息素的量可以限制，例如，在一系列值内。<br>
 * 为了确定质量，需要了解解决方案的性质 ({@link AbstractSolution})。<br>
 * 因此，必须定义抽象质量函数的特征,
 * 通过确定问题以及解决方案，可以确定质量。<br>
 *
 * <p><img src="{@docRoot}/images/SolutionQuality.svg" alt=""></p>
 */
public class SolutionQualityMin extends AbstractSolutionQuality<SCPSolution> {

    /**
     * 提供所提交解决方案的质量 Q。<br>
     * Q = n / z<br>
     * 其中 n = 子集数, z = 解的目标函数值<br>
     * @param solution: SCP的解决方案
     * @return 解决方案的质量
     */
    @Override
    public float getQuality(SCPSolution solution) {

        return (float) solution.getProblem().getStructure().subsetsSize() / solution.getValue();

    }
}
