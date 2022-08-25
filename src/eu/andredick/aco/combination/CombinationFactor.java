package eu.andredick.aco.combination;

/**
 * <b>通过乘法实现组合功能.</b><br>
 * 第3.3.8章，第36页，组合函数<br>
 * <br>
 * <b>通过乘法</b>将感知到的信息素与启发式信息相结合。<br>
 * 以上两个值都通过相关的指数 alpha 和 beta 相对于彼此进行加权.<br>
 * <p><img src="{@docRoot}/images/Combination.svg" alt=""></p>
 */
public class CombinationFactor extends CombinationRule {

    /**
     *  <b>alpha</b>说明：关于感知信息素的相对权重
      */
    float alpha;

    /**
     *  <b>beta</b>说明：关于启发式信息的相对权重
     */
    float beta;

    /** 传递两个参数的构造函数
     *
     * @param alpha 说明 <b>alpha</b> 关于感知信息素的相对权重
     * @param beta 说明 <b>beta</b> 关于启发式信息价值的相对权重
     */
    public CombinationFactor(float alpha, float beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    /** <b>乘法组合函数</b><br>
     * <code>Math.pow(pheromone, alpha) * Math.pow(heurist, beta)</code>
     * @param pheromone 感知信息素
     * @param heurist   启发式信息的价值
     * @return 组合结果
     */
    @Override
    public float combine(float pheromone, float heurist) {
        return (float) (Math.pow(pheromone, alpha) * Math.pow(heurist, beta));
    }
}
