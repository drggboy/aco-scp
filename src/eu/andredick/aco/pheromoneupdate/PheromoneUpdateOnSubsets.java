package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.SCPSolution;

/**
 * <b>信息素标记组件的实现</b><br>
 * 第3.3.11章，第45页，信息素标记<br>
 * <br>
 * SCP 问题中通过相关的解 {@link SCPSolution}对子集的信息素{@link PheromoneOnSubsets}进行标记。<br>
 * <br>
 * 问题 ({@link eu.andredick.aco.problem.AbstractProblem})的解决方案 ({@link AbstractSolution}) 的信息素标记对蚂蚁 ({@link eu.andredick.aco.ant.AbstractAnt})具有指导作用。<br>
 * 由主进程 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess})调用。<br>
 * 为了执行此任务，蚂蚁具有信息素标记的功能。<br>
 * 对于要选择的蚂蚁解的实例 {@link AbstractSolution} 首先使用质量函数 ({@link AbstractSolutionQuality}) 确定要应用的信息素的量，<br>
 * 随后，通过成分信息素关联 ({@link AbstractPheromoneAssociation}) 增加解成分上的信息素浓度。<br>
 *
 * <p><img src="{@docRoot}/images/PheromoneUpdate.svg" alt=""></p>
 */
public class PheromoneUpdateOnSubsets extends
        AbstractPheromoneUpdate<PheromoneOnSubsets, SCPSolution> {

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素与SCP子群的关联
     * @param solutionQuality    解决方案的质量组件
     */
    public PheromoneUpdateOnSubsets(PheromoneOnSubsets pheromoneStructure,
                                    AbstractSolutionQuality solutionQuality) {
        super(pheromoneStructure, solutionQuality);
    }

    /**
     * 根据给定的 SCP 解决方案，用信息素标记包含的子集。<br>
     * 为此，首先使用 {@link #solutionQuality} 确定解决方案的质量，<br>
     * 然后根据解决方案的质量，将包含在 SCP 解决方案中的所有子集都用信息素标记。<br>
     * 为此使用传入的信息素关联组件 ({@link #pheromoneStructure})。<br>
     *
     * @param solution 用信息素标记的SCP解或蚂蚁路径.
     */
    @Override
    public void update(SCPSolution solution) {
        float delta = this.solutionQuality.getQuality(solution);
        for (Integer subset : solution.getSubsets()) {
            this.pheromoneStructure.addPheromone(subset, delta);
        }
    }
}
