package eu.andredick.aco.pheromoneperception;

/**
 * <b>信息素感知的抽象部分</b><br>
 * 第3.3.6章，第33页，信息素感知<br>
 * <br>
 * 信息素感知用于蚂蚁的解的构造。<br>
 * 在解决方案设计中，解决方案组件的候选解以迭代方式进行。<br>
 * 在选择候选解时，蚂蚁在其决策中不仅包括启发式信息，还包括与候选解相关的信息素。<br>
 * 不是直接处理信息素浓度的值，而是使用 “主观”的感知值。<br>
 * 因此，信息素感知将实际的信息素浓度转换为感知值，以选择候选解。<br>
 * <br>
 * <p><img src="{@docRoot}/images/PheromonePerception.svg" alt=""></p>
 */
public abstract class AbstractPheromonePerception {

    /**
     * 提供蚂蚁感知到的信息素浓度，<br>
     * 这是根据实际的信息素浓度确定的。<br>
     * 实际的信息素浓度作为方法的输入参数。<br>
     *
     * @param pheromoneConcentration 信息素的实际浓度
     * @return 信息素的感知浓度
     */
    public abstract float getPerceptionValue(float pheromoneConcentration);

}
