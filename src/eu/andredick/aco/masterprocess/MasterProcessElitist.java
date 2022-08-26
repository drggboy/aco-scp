package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

/**
 * <b>主流程-精英策略</b> - 主过程的组件的实现，只允许迭代最佳蚂蚁进行信息素标记。<br>
 * <br>
 * 主流程的实现反映了 ACO 元启发式的算法逻辑,<br>
 * 通过初始化和蒸发信息素 (参见 {@link AbstractPheromoneAssociation})<br>
 * 以及蚂蚁种群 (参见 {@link AbstractAnt}) 进行协调。<br>
 * <br>
 * <b>流程:</b><br>
 * 1 - 信息素初始化<br>
 * 2 - 构建所有蚂蚁的解决方案<br>
 * 3 - 所有蚂蚁的构造解的局部搜索<br>
 * 4 - 信息素蒸发<br>
 * 5 - 蚂蚁<b>迭代</b>进行信息素标记<br>
 * 6 - 重置蚂蚁记忆<br>
 * 7 - 返回 2. 如果不满足取消条件.<br>
 * <br>
 * 主流程在 {@link eu.andredick.aco.algorithm.ACOAlgorithm} 处被执行调用。
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 * 精英策略 - 主流程组件的实现
 */
public class MasterProcessElitist extends AbstractMasterProcess {

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素关联
     * @param ants               蚂蚁种群
     * @param termCriterion      迭代的取消标准
     */
    public MasterProcessElitist(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * <b>精英策略的逻辑</b><br>
     * <br>
     * <b>流程:</b><br>
     * 1 - 信息素初始化<br>
     * 2 - 构建所有蚂蚁的解决方案<br>
     * 3 - 所有蚂蚁构造解的局部搜索<br>
     * 4 - 信息素蒸发<br>
     * 5 - 蚂蚁<b>迭代</b>进行信息素的标记 <br>
     * 6 - 重置蚂蚁记忆<br>
     * 7 - 返回 2. 如果不满足取消条件.<br>
     */
    @Override
    public void start() {

        // 信息素的启动
        this.pheromoneStructure.initPheromones();

        // 迭代到满足终止条件
        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            // 迭代最优值
            AbstractAnt bestIterAnt = null;
            Float bestIterValue = null;

            // 关于所有蚂蚁的迭代
            for (AbstractAnt ant : this.ants) {
                // 构建解决方案
                ant.constructSolution();
                // 对构造的解决方案进行局部搜索
                ant.localSearch();
                // 目标函数值
                float value = ant.evaluateSolution();

                // 确定最优蚂蚁
                if (bestIterValue == null || value < bestIterValue) {
                    bestIterValue = value;
                    bestIterAnt = ant;
                }
                // 更新统计信息
                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            // 信息素蒸发
            this.pheromoneStructure.evaporatePheromones();

            // 信息素仅通过迭代最佳蚂蚁更新
            bestIterAnt.markPheromone();

            for (AbstractAnt a : this.ants) {
                a.resetAnt();
            }

            System.out.println("MasterProcessElitist... Interation: " + iteration);
        }

    }
}
