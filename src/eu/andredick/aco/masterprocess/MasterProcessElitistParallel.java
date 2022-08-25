package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>精英主程序 并行</b> - 规范具有并行执行的主进程的组件，其中只有迭代最佳蚂蚁才能用信息素标记其解决方案.<br>
 * 第 3.3.2 章，第 26 页，主流程<br>
 * <br>
 * 主流程的实现反映了 ACO 元启发式的上级序列,<br>
 * 通过启动和蒸发信息素 (see {@link AbstractPheromoneAssociation})<br>
 * 和蚂蚁的数量 (see {@link AbstractAnt}) 协调.<br>
 * <br>
 * <b>Ablauf:</b><br>
 * 1 - 信息素的起始<br>
 * 2 - 构建所有蚂蚁的解决方案 - 并行<br>
 * 3 - 对所有蚂蚁的构造解进行局部搜索 - 并行<br>
 * 4 - 信息素的蒸发<br>
 * 5 - 信息素的标记 <b>迭代</b> 蚂蚁<br>
 * 6 - 重置蚂蚁记忆<br>
 * 7 - 返回 2. 如果不满足取消条件.<br>
 * <br>
 * 主过程在 {@link eu.andredick.aco.algorithm.ACOAlgorithm} 并从那里开始.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 * 精英策略 - 主过程的实现
 */
public class MasterProcessElitistParallel extends AbstractMasterProcess {

    // 构造函数
    public MasterProcessElitistParallel(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }


    /**
     * <b>精英程序的逻辑</b><br>
     * <br>
     * <b>Ablauf:</b><br>
     * 1 - 信息素的起始<br>
     * 2 - 构建所有蚂蚁的解决方案 - 并行<br>
     * 3 - 对所有蚂蚁的构造解进行局部搜索 - 并行<br>
     * 4 - 信息素的蒸发<br>
     * 5 - 信息素的标记 <b>迭代</b> 蚂蚁<br>
     * 6 - 重置蚂蚁记忆<br>
     * 7 - 返回 2. 如果不满足取消条件.<br>
     */
    @Override
    public void start() {
        this.pheromoneStructure.initPheromones();

        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

            AbstractAnt bestIterAnt = null;
            Float bestIterValue = null;


            List<Thread> threads = new LinkedList<>();
            for (AbstractAnt ant : this.ants) {

                threads.add(new Thread(
                        new Runnable() {

                            @Override
                            public void run() {

                                ant.constructSolution();

                                ant.localSearch();

                            }
                        }
                ));
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (AbstractAnt ant : this.ants) {

                float value = ant.evaluateSolution();

                if (bestIterValue == null || value < bestIterValue) {
                    bestIterValue = value;
                    bestIterAnt = ant;
                }

                this.statistics.setValue(iteration, value, ant.getSolution());
            }

            this.pheromoneStructure.evaporatePheromones();

            bestIterAnt.markPheromone();

            for (AbstractAnt a : this.ants) {
                a.resetAnt();
            }

            System.out.println("MasterProcessElitistParallel... Interation: " + iteration);
        }
    }
}
