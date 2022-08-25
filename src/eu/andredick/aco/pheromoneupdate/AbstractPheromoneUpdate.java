package eu.andredick.aco.pheromoneupdate;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.solutionquality.AbstractSolutionQuality;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>信息素标记的抽象部分</b><br>
 * 第3.3.11章，第45页，信息素标记<br>
 * <br>
 * 为解决方案中包含的实体进行信息素标记。<br>
 * <br>
 * 标记相关问题 ({@link eu.andredick.aco.problem.AbstractProblem})的解决方案 ({@link AbstractSolution}) 由主进程 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess})调用，<br>
 * 此过程与蚂蚁 ({@link eu.andredick.aco.ant.AbstractAnt})相关。<br>
 * 为了执行此任务，蚂蚁具有对解的组成成分进行信息素标记的功能。<br>
 * 对于要标记的蚂蚁解的实例 {@link AbstractSolution} 最初通过质量组件 ({@link AbstractSolutionQuality}) 确定信息素的量。<br>
 * 随后，通过信息素关联组件 ({@link AbstractPheromoneAssociation})增加解组分上的信息素浓度。<br>
 * <br>
 * 必须根据信息素关联和问题解决的表现来确定该组分的派生类。<br>
 * 这样做的原因是标记实体（解决方案组件）需要了解问题的结构和相关的信息素关联。<br>
 * 这种依赖关系是通过泛型编程实现的。<br>
 *
 * <p><img src="{@docRoot}/images/PheromoneUpdate.svg" alt=""></p>
 *
 * @param <E> 信息素关联的表达
 * @param <S> 与问题相关的解决方案的规范
 */
public abstract class AbstractPheromoneUpdate<E extends AbstractPheromoneAssociation, S extends AbstractSolution> {

    /**
     * 信息素关联作为通用引用
     */
    protected E pheromoneStructure;

    /**
     * 解决方案的质量组件
     */
    protected AbstractSolutionQuality solutionQuality;

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素关联作为通用引用
     * @param solutionQuality    解决方案的质量组件
     */
    public AbstractPheromoneUpdate(E pheromoneStructure, AbstractSolutionQuality solutionQuality) {
        this.pheromoneStructure = pheromoneStructure;
        this.solutionQuality = solutionQuality;
    }

    /**
     * 根据解决方案用信息素标记问题的相关实体。
     *
     * @param solution  用信息素标记的溶液或蚂蚁路径
     */
    public abstract void update(S solution);
}
