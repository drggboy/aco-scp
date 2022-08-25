package eu.andredick.aco.pheromoneinit;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>信息素初始化成分的表达</b><br>
 * 第3.3.3章，第29页，信息素的初始化<br>
 * <br>
 * 对于问题的所有相关实体，生成相同的信息素浓度起始值.<br>
 * 组件的参数表示信息素的初始值。<br>
 * <br>
 * 信息素初始化会为 ACO 算法的后续迭代创建初始状态。<br>
 * 如何初始化信息素浓度将在该组件的子类中确定。<br>
 * <br>
 * 信息素初始化由主进程 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 调用.<br>
 * 这是通过使用组件 {@link AbstractPheromoneAssociation}中的方法 {@link AbstractPheromoneAssociation#initPheromones()}完成的。<br>
 * 然后使用 {@link AbstractPheromoneAssociation} 中的信息素初始化，以调整所有信息素浓度.<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneInit.svg" alt=""></p>
 */
public class PheromoneInit implements AbstractPheromoneInit {

    /**
     * 信息素浓度的初始值作为组件的参数<br>
     */
    private float pheromoneInitValue;

    /**
     * 构造函数
     *
     * @param pheromoneInitValue 信息素浓度的初始值作为组分的参数
     */
    public PheromoneInit(float pheromoneInitValue) {
        this.pheromoneInitValue = pheromoneInitValue;
    }


    /**
     * 提供信息素浓度的初始值
     *
     * @return 信息素浓度的初始值
     */
    @Override
    public float initValue() {
        return this.pheromoneInitValue;
    }
}
