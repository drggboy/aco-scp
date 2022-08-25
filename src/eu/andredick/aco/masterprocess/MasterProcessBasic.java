package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

/**
 * <b>Masterprozess-Basic</b> - 主进程组件的基本设计<br>
 * 第 3.3.2 章，第 26 页，主流程<br>
 * <br>
 * 主进程的实现反映了 ACO元启发式 的算法逻辑，<br>
 * 涉及信息素的初始化和蒸发 (参见 {@link AbstractPheromoneAssociation})<br>
 * 以及蚂蚁种群 (参见 {@link AbstractAnt}).<br>
 * <br>
 * <b>流程:</b><br>
 * 1 - 信息素的初始化<br>
 * 2 - 构建所有蚂蚁的解<br>
 * 3 - 所有蚂蚁构造解的局部搜索<br>
 * 4 - 信息素的蒸发<br>
 * 5 - <b>每个</b> 蚂蚁信息素的标记方式 <br>
 * 6 - 重置蚂蚁记忆<br>
 * 7 - 如果不满足取消条件，返回 2。<br>
 * <br>
 * 主进程由 {@link eu.andredick.aco.algorithm.ACOAlgorithm} 调用并从那里开始.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 */
public class MasterProcessBasic extends AbstractMasterProcess {

    /**
     * 构造函数
     *
     * @param pheromoneStructure 与要解决的抽象问题相关联的信息素
     * @param ants               蚂蚁种群
     * @param termCriterion      迭代的取消标准
     */
    public MasterProcessBasic(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }

    /**
     * <b>主程序基本逻辑</b><br>
     * <br>
     * <b>步骤:</b><br>
     * 1 - 信息素的初始化<br>
     * 2 - 构建所有蚂蚁的解<br>
     * 3 - 所有蚂蚁构造解的局部搜索<br>
     * 4 - 信息素蒸发<br>
     * 5 - 所有蚂蚁进行信息素标记<br>
     * 6 - 重置蚂蚁记忆<br>
     * 7 - 返回 2. 如果不满足取消条件。<br>
     */
    @Override
    public void start() {

        // 信息素的初始化
        this.pheromoneStructure.initPheromones();

        // 迭代到满足终止条件
        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            // 关于所有蚂蚁的迭代
            for (AbstractAnt ant : this.ants) {
                // 构建解决方案
                ant.constructSolution();
                // 对构造的解决方案进行局部搜索
                ant.localSearch();
                // 目标函数值
                float value = ant.evaluateSolution();
                // 更新统计信息
                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            // 信息素蒸发
            this.pheromoneStructure.evaporatePheromones();

            // 所有蚂蚁的信息素更新
            for (AbstractAnt a : this.ants) {
                a.markPheromone();
                a.resetAnt();
            }

            System.out.println("MasterProcessBasic... Interation: " + iteration);
        }

    }
}
