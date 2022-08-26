package eu.andredick.aco.masterprocess;

import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>主流程基本并行化</b> - 具有并行执行的主流程组件的基本设计<br>
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
 * 5 - 对信息素的标记 <b>alle</b> 蚂蚁<br>
 * 6 - 重置蚂蚁记忆<br>
 * 7 - 返回 2. 如果不满足取消条件.<br>
 * <br>
 * 主要过程在 {@link eu.andredick.aco.algorithm.ACOAlgorithm} 并从那里开始.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 */
public class MasterProcessBasicParallel extends AbstractMasterProcess {

    /**
     * 构造函数
     *
     * @param pheromoneStructure 与要解决的抽象问题相关联的信息素
     * @param ants               蚂蚁种群
     * @param termCriterion      迭代的取消标准
     */
    public MasterProcessBasicParallel(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        super(pheromoneStructure, ants, termCriterion);
    }


    /**
     * <b>主程序基本逻辑</b><br>
     * <br>
     * <b>Ablauf:</b><br>
     * 1 - 信息素的初始化<br>
     * 2 - 构建所有蚂蚁的解决方案 - 并行<br>
     * 3 - 对所有蚂蚁的构造解进行局部搜索 - 并行<br>
     * 4 - 信息素的蒸发<br>
     * 5 - 所有蚂蚁对信息素的标记<br>
     * 6 - 重置蚂蚁记忆<br>
     * 7 - 返回 2. 如果不满足取消条件.<br>
     */
    @Override
    public void start() {
        this.pheromoneStructure.initPheromones();

        for (int iteration = 0; termCriterion.checkTermination(iteration, statistics); iteration++) {

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

            this.pheromoneStructure.evaporatePheromones();

            for (AbstractAnt ant : this.ants) {
                float value = ant.evaluateSolution();
                this.statistics.setValue(iteration, value, ant.getSolution());
                ant.markPheromone();
                ant.resetAnt();
            }

            System.out.println("MasterProcessBasicParallel... Interation: " + iteration);
        }
    }
}
