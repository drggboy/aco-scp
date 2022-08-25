package eu.andredick.aco.combination;

/**
 * <b>通过加法实现组件组合功能.</b><br>
 * 第3.3.8章，第36页，组合函数<br>
 * <br>
 * <b>通过加法</b>将感知到的信息素与启发式信息相结合。<br>
 * 两个值通过加权因子 <b>gamma</b> 相对于彼此加权.<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public class CombinationSum extends CombinationRule {

    /**
     * 加权系数 <b>gamma</b>, 它相对于彼此加权求和（感知信息素和启发式信息）.
     */
    float gamma;

    /**
     *  构造函数<br>
     *  传递加权系数
     * @param gamma 加权系数 <b>gamma</b>, 它相对于彼此加权求和（感知信息素和启发式信息）。
     */
    public CombinationSum(float gamma) {
        this.gamma = gamma;
    }

    /** <b>通过加法实现组合功能</b><br>
     * <code>gamma * pheromone + (1 - gamma) * heurist</code>
     * @param pheromone 感知信息素
     * @param heurist   启发式信息的价值
     * @return 组合结果
     */
    @Override
    public float combine(float pheromone, float heurist) {
        return gamma * pheromone + (1 - gamma) * heurist;
    }
}
