package eu.andredick.aco.pheromoneevaporation;

import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;

/**
 * <b>信息素蒸发的抽象类</b><br>
 * 第3.3.10章，第45页，信息素的蒸发<br>
 * <br>
 * 该组件模拟信息素的连续蒸发过程。<br>
 * 它在两个相邻迭代步骤的信息素浓度之间建立递归关系。<br>
 * <br>
 * 信息素蒸发由主进程 ({@link eu.andredick.aco.masterprocess.AbstractMasterProcess}) 调用。<br>
 * 这是通过调用组件 {@link AbstractPheromoneAssociation} 中的方法 {@link AbstractPheromoneAssociation#evaporatePheromones()}完成的。<br>
 * 然后使用信息素蒸发组件 {@link AbstractPheromoneAssociation} 调节所有的信息素浓度。<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneEvaporation.svg" alt=""></p>
 */
public interface AbstractPheromoneEvaporation {

    /**
     * 计算后续时间步长（迭代步长）的信息素浓度。<br>
     *
     * @param pheromoneValue 当前信息素浓度（时间 t）
     * @return 新信息素浓度（时间 t+1）
     */
    float evaporate(float pheromoneValue);

}
