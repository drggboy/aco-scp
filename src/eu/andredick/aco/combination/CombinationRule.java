package eu.andredick.aco.combination;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;

/**
 * <b>组合函数的抽象</b><br>
 * 第3.3.8章，第36页，组合函数<br>
 * <br>
 * 将感知到的信息素与启发式信息相结合。<br>
 * 组合功能由备选选择组件 (参见 {@link AbstractNextStepStrategy})使用。<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public abstract class CombinationRule {
    /**
     * 一种计算感知信息素组合值和启发式信息价值的抽象方法.<br>
     * 在派生类中，函数关系由此方法实现.
     *
     * @param pheromone 感知信息素
     * @param heurist   启发式信息的价值
     * @return 组合结果
     */
    public abstract float combine(float pheromone, float heurist);

}
