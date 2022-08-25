package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsetPairs;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.scp.SCPSolution;

import java.util.List;

/**
 * <b>信息素标记组分的表达</b><br>
 * 第 3.3.11 章，第 45 页，信息素标记<br>
 * <br>
 * SCP问题子集对上的信息素标记 {@link PheromoneOnSubsets}, 相关解决方案中的那些 {@link SCPSolution} 被包含在内.<br>
 * <br>
 * 解决方案的标记 ({@link AbstractSolution}) 问题 ({@link eu.andredick.aco.problem.AbstractProblem}) 与信息素由主过程协调 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}),
 * 为此目的，蚂蚁 ({@link eu.andredick.aco.ant.AbstractAnt}) 指南.<br>
 * 为了执行此任务，蚂蚁具有成分信息素标记的表达式.<br>
 * 对于要选择的蚂蚁溶液的实例 {@link AbstractSolution} 最初通过质量功能执行 ({@link AbstractSolutionQuality}) 确定要应用的信息素量.<br>
 * 随后，通过成分信息素关联 ({@link AbstractPheromoneAssociation}) 可以增加溶液成分上的信息素浓度.<br>
 *
 * <p><img src="{@docRoot}/images/PheromoneUpdate.svg" alt=""></p>
 */
public class PheromoneUpdateOnSubsetPairs extends
        AbstractPheromoneUpdate<PheromoneOnSubsetPairs, SCPSolution> {

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素与 SCP 子集对的关联
     * @param solutionQuality    解决方案的质量功能
     */
    public PheromoneUpdateOnSubsetPairs(PheromoneOnSubsetPairs pheromoneStructure,
                                        AbstractSolutionQuality solutionQuality) {
        super(pheromoneStructure, solutionQuality);
    }

    /**
     * 根据给定的SCP溶液，用信息素标记其中包含的所有子集对.<br>
     * 为此，解决方案的质量首先通过 {@link #solutionQuality} 确定.<br>
     * 然后根据解决方案的质量，将包含在 SCP 解决方案中的所有子集对都用信息素标记.<br>
     * 为此，分配的成分信息素关联 ({@link #pheromoneStructure}) 用过的.<br>
     * 一对 （i，j） 的标记是对称的。因此，关系 （i，j） 和 （j，i） 被平均标记.<br>
     *
     * @param solution 用信息素标记的SCP溶液或蚂蚁路径.
     */
    @Override
    public void update(SCPSolution solution) {
        float delta = this.solutionQuality.getQuality(solution);
        List<Integer> varsList = solution.getSubsets();
        int varListSize = varsList.size();
        for (int i = 0; i < varListSize; i++) {
            for (int j = i + 1; j < varListSize; j++) {
                pheromoneStructure.addPheromone(varsList.get(i), varsList.get(j), delta);
                pheromoneStructure.addPheromone(varsList.get(j), varsList.get(i), delta);
            }
        }
        //ToDO: 第一个元素的单独标记可能是多余的
        pheromoneStructure.addPheromone(varsList.get(0), varsList.get(0), delta);
    }
}
