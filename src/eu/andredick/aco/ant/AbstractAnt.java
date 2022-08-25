package eu.andredick.aco.ant;

import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>蚂蚁的抽象类.</b><br>
 * <br>
 * 定义主要接口, 可以被主进程组件 (参见 {@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 使用.<br>
 * 设计接口，其中多个类的方法在接口集中提供.<br>
 * 因为所有方法都是抽象的，所以此类用于接口定义，并且可以作为 Java 接口实现。<br>
 * <p><img src="{@docRoot}/images/ACOAnt.svg" alt=""></p>
 */
public abstract class AbstractAnt<S extends AbstractSolution> {

    /**
     * 启动蚂蚁解的构造.<br>
     * 该方法由主进程调用.<br>
     * 由 ant 创建的解应保留为对象变量.
     */
    public abstract void constructSolution();

    /**
     * 通过局部搜索开始改进构造的蚂蚁解.<br>
     * 该方法由主进程调用.<br>
     * 局部改进的解应保留为对象变量.
     */
    public abstract void localSearch();

    /**
     * 提供蚂蚁解的目标函数值.<br>
     * 该方法由主进程调用.<br>
     *
     * @return 蚂蚁解的目标函数值
     */
    public abstract Float evaluateSolution();

    /**
     * 开始在问题实例上标记 ant 解.<br>
     * 该方法由主进程调用.<br>
     */
    public abstract void markPheromone();

    /**
     * 为下一次迭代更新蚂蚁的状况.<br>
     * 该方法由主进程调用.<br>
     */
    public abstract void resetAnt();

    /**
     * 根据蚂蚁的状况，提供蚂蚁的解决方案.<br>
     * 它可以是新杰，改进或空解<br>
     * 根据实际情况，也可能提供不完整的解.
     *
     * @return 蚂蚁的解
     */
    public abstract S getSolution();

    /**
     * 为蚂蚁设置解.<br>
     * 该方法用于用一只蚂蚁的解覆盖另一只蚂蚁的解.
     * @param solution 新解
     */
    public abstract void setSolution(S solution);
}
