package eu.andredick.aco.masterprocess;

import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.aco.ant.AbstractAnt;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.termination.AbstractTerminationCriterion;

/**
 * <b>主流程的抽象组件</b><br>
 * <br>
 * 主进程负责协调信息素的初始化和蒸发 (参见 {@link AbstractPheromoneAssociation})<br>
 * <br>
 * 以及蚂蚁种群 (参见 {@link AbstractAnt})来反映ACO元启发式的总体序列.<br>
 * 为此，需在方法 {@link #start()} 内实现迭代循环,<br>
 * 这取决于抽象终止标准 {@link AbstractTerminationCriterion} 来执行.<br>
 * 主进程在 {@link eu.andredick.aco.algorithm.ACOAlgorithm} 中使用并从那里开始.
 * <p><img src="{@docRoot}/images/Masterprocess-a.svg" alt=""></p>
 * <hr>
 * <p><img src="{@docRoot}/images/Masterprocess-b.svg" alt=""></p>
 */
public abstract class AbstractMasterProcess {

    /**
     * 需要协调的蚂蚁种群
     */
    protected AbstractAnt[] ants;

    /**
     * 与要解决的抽象问题相关联的信息素
     */
    protected AbstractPheromoneAssociation pheromoneStructure;

    /**
     * 迭代的取消标准
     */
    protected AbstractTerminationCriterion termCriterion;

    /**
     * 算法过程的统计
     */
    protected Statistics statistics;

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素与要解决的抽象问题相关联
     * @param ants               蚂蚁种群
     * @param termCriterion      迭代的取消标准
     */
    public AbstractMasterProcess(AbstractPheromoneAssociation pheromoneStructure, AbstractAnt[] ants, AbstractTerminationCriterion termCriterion) {
        this.pheromoneStructure = pheromoneStructure;
        this.ants = ants;
        this.termCriterion = termCriterion;
        this.statistics = new Statistics();
    }


    /**
     * 类的接口 {@link eu.andredick.aco.algorithm.ACOAlgorithm}.<br>
     * 该方法启动主进程.
     */
    public abstract void start();

    /**
     * 提供有关算法流的统计信息.
     *
     * @return 算法过程的统计
     */
    public Statistics getStatistics() {
        return statistics;
    }
}
