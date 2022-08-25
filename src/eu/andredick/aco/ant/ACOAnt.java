package eu.andredick.aco.ant;

import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.localsearch.AbstractLocalSearch;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.problem.AbstractProblem;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>实现蚂蚁组件</b> 通过实现类 {@link AbstractAnt} 的抽象方法.<br>
 * <br>
 * 提供从 MasterProcess 组件继承的方法。可被(参见 {@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 调用。<br>
 * 设计接口，其中多个类的方法在接口集中提供。<br>
 * 蚂蚁对象还具有定义其状态的变量。<br>
 * <p><img src="{@docRoot}/images/ACOAnt.svg" alt=""></p>
 */
public class ACOAnt <S extends AbstractSolution<P>, P extends AbstractProblem> extends AbstractAnt<S> {

    /**
     * 要找的解（路径）的抽象问题
     */
    protected P problem;

    /**
     * 蚂蚁的当前解（路径）。<br>
     * 解可能不完整。
     */
    protected S solution;

    /**
     * 实现信息素标记的任意组件
     */
    protected AbstractPheromoneUpdate updateRule;

    /**
     * 实现解的启发式构造的任意组件
     */
    protected AbstractConstruction constructionStrategy;

    /**
     * 实现局部搜索的任意组件
     */
    protected AbstractLocalSearch localSearchStrategy;

    /**
     * 构造函数
     *
     * @param problem              要找到解决方案或路径的抽象问题.
     * @param updateRule           实现信息素标记的任意组件
     * @param constructionStrategy 实现解的启发式构造的任意组件
     * @param localSearchStrategy  实现局部搜索的任意组件
     */
    public ACOAnt(P problem,
                  AbstractPheromoneUpdate updateRule,
                  AbstractConstruction constructionStrategy,
                  AbstractLocalSearch localSearchStrategy) {

        this.problem = problem;
        this.solution = null;
        this.updateRule = updateRule;
        this.constructionStrategy = constructionStrategy;
        this.localSearchStrategy = localSearchStrategy;
    }

    /**
     * 启动蚂蚁解的构造。<br>
     * 为此，使用分配给蚂蚁的启发式构造组件。<br>
     * 该方法由主进程调用。<br>
     * 由 ant 创建的解应保留为对象变量。
     *
     * @see AbstractConstruction#construct(AbstractProblem)
     */
    @Override
    public void constructSolution() {
        this.solution = (S) this.constructionStrategy.construct(this.problem);
    }

    /**
     * 提供蚂蚁解的目标函数值。<br>
     * 该方法由主进程调用。<br>
     * 蚂蚁的状况可能包括不完整的解!<br>
     *
     * @return 蚂蚁解的目标函数值
     */
    @Override
    public Float evaluateSolution() {
        return this.solution.getValue();
    }

    /**
     * 开始在问题实例上标记信息素。<br>
     * 为此，使用为蚂蚁分配的信息素标记组件。<br>
     * 该方法由主进程调用。<br>
     *
     * @see AbstractPheromoneUpdate#update(AbstractSolution)
     */
    @Override
    public void markPheromone() {
        this.updateRule.update(this.solution);
    }

    /**
     * 通过局部搜索开始改进构造好的蚂蚁解。<br>
     * 为此，使用分配给 ant 的局部搜索组件。<br>
     * 该方法由主进程调用。<br>
     *
     * @see AbstractLocalSearch#search(AbstractSolution)
     */
    @Override
    public void localSearch() {
        this.solution = (S) this.localSearchStrategy.search(this.solution);
    }

    /**
     * 为下一次迭代更新蚂蚁的状况。 <br>
     * 为此，先前的蚂蚁解被空解取代。 <br>
     * 该方法由主进程调用。
     */
    @Override
    public void resetAnt() {
        this.solution = (S) this.solution.createNew();
    }

    // 提供蚂蚁的解
    @Override
    public S getSolution() {
        return this.solution;
    }

    // 记住解决方案
    @Override
    public void setSolution(S solution) {
        this.solution = solution;
    }


}
