package eu.andredick.aco.pheromoneevaporation;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>信息素蒸发组件的实现</b><br>
 * <br>
 * 此处通过指数递减函数对蒸发进行建模。<br>
 * 该组件模拟信息素的连续蒸发过程，通过对信息素浓度乘以递减因子，模拟蒸发过程<br>
 * 它在两个相邻迭代步骤的信息素浓度之间建立递归关系。<br>
 * <br>
 * 组件具有参数 ({@link #evaporationFactor}).<br>
 * <br>
 * 信息素蒸发由主进程 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 调用。<br>
 * 这是通过使用组件 {@link AbstractPheromoneAssociation}中的方法 {@link AbstractPheromoneAssociation#evaporatePheromones()} 完成的。<br>
 * 然后使用信息素蒸发组件 {@link AbstractPheromoneAssociation} 调节所有的信息素浓度。<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneEvaporation.svg" alt=""></p>
 */
public class PheromoneEvaporation implements AbstractPheromoneEvaporation {

    /**
     * 递减因子（蒸发因子），用其与信息素浓度相乘
     */
    private float evaporationFactor;

    /**
     * 构造函数
     *
     * @param evaporationFactor 蒸发系数（如 0.1）
     */
    public PheromoneEvaporation(float evaporationFactor) {
        this.evaporationFactor = evaporationFactor;
    }

    /**
     * 计算后续时间步长（迭代步长）的信息素浓度,<br>
     * 通过将信息素浓度乘以递减因子（蒸发因子）.
     *
     * @param pheromoneValue 当前信息素浓度（时间 t）
     * @return 新信息素浓度（时间 t+1）
     */
    @Override
    public float evaporate(float pheromoneValue) {
        return pheromoneValue * evaporationFactor;
    }
}
