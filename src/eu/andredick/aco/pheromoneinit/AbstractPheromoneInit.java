package eu.andredick.aco.pheromoneinit;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>信息素初始化的抽象</b><br>
 * <br>
 * 初始化信息素值会为 aco 算法的后续迭代创建初始状态.<br>
 * 如何初始化信息素浓度应在该组分的子类中确定.<br>
 * <br>
 * 信息素初始化由主进程执行 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 协调.<br>
 * 这是通过使用该方法完成的 {@link AbstractPheromoneAssociation#initPheromones()} 组件数量 {@link AbstractPheromoneAssociation}被调用.<br>
 * 然后使用 {@link AbstractPheromoneAssociation} 组分信息素初始化的表达式，以调整所有信息素浓度.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneInit.svg" alt=""></p>
 */
public interface AbstractPheromoneInit {

    /**
     * 提供信息素浓度的初始值
     *
     * @return 信息素浓度的初始值
     */
    public float initValue();

}
