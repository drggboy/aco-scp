package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.aco.problem.AbstractProblem;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.masterprocess.AbstractMasterProcess;

/**
 * <b>信息素关联的抽象类</b><br>
 * <br>
 * 与特定问题实例相关的信息素可以作为一个层,<br>
 * 此处定义了信息素蒸发和初始化的规则.<br>
 * 信息素关联将信息素分配给特定问题的实体.<br>
 * 因此，信息素关联的每种实现都对应特定的问题.<br>
 * 这种依赖关系由泛型编程 {@code <P extends AbstractProblem>} 实现.<br>
 * 因此，有必要确定信息素关联的具体问题。.<br>
 * <br>
 * 以下组件中需要信息素关联:
 * <ul>
 * <li>主流程 {@link AbstractMasterProcess}</li>
 * <li>信息素起始 {@link AbstractPheromoneInit}</li>
 * <li>信息素蒸发 {@link AbstractPheromoneEvaporation}</li>
 * <li>候选方案的选择 {@link AbstractNextStepStrategy}</li>
 * <li>信息素标记  {@link AbstractPheromoneUpdate}</li>
 * </ul>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneAssociation-a.svg" alt=""></p>
 * <br>
 *
 * @param <P> 问题的具体化类
 */
public abstract class AbstractPheromoneAssociation<P extends AbstractProblem> {

    /**
     * 与信息素相关的的特定问题的实现
     */
    protected P problem;

    /**
     * 信息素蒸发的表达
     */
    protected AbstractPheromoneEvaporation evaporationRule;

    /**
     * 信息素初始化
     */
    protected AbstractPheromoneInit pheromoneInitRule;

    /**
     * 构造函数
     *
     * @param problem 特定的问题实例
     */
    public AbstractPheromoneAssociation(P problem) {
        this.problem = problem;
    }

    /**
     * 启动整个信息素蒸发的时间步长。<br>
     * 所有信息素浓度应符合蒸发规则 {@link #evaporationRule}。<br>
     */
    public abstract void evaporatePheromones();

    /**
     * 使用启动规则为问题的所有实体启动信息素浓度 {@link #pheromoneInitRule}.<br>
     * 此方法建立整个信息素的初始状态，该状态在ACO算法启动时应存在.
     */
    public abstract void initPheromones();

    /**
     * 提供与问题实体j相关的信息素浓度
     *
     * @param j     问题实体的索引
     * @return      与问题实体j相关的信息素浓度
     */
    public abstract float getPheromone(int j);


    /**
     * 向现有信息素添加额外的信息素。<br>
     * 该方法由信息素标记组件 ({@link AbstractPheromoneUpdate}) 使用.
     *
     * @param j        问题实体的索引
     * @param ph_delta 增加的信息素浓度
     */
    public abstract void addPheromone(int j, float ph_delta);

    /**
     * 用于实现信息素蒸发的方法
     * @param evaporationRule 信息素蒸发
     */
    public void setEvaporationRule(AbstractPheromoneEvaporation evaporationRule) {
        this.evaporationRule = evaporationRule;
    }

    /**
     * 用于实现信息素起始的方法
     * @param pheromoneInitRule 信息素起始
     */
    public void setPheromoneInitRule(AbstractPheromoneInit pheromoneInitRule) {
        this.pheromoneInitRule = pheromoneInitRule;
    }

}