package eu.andredick.scp;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * <b>集合覆盖问题的目标函数</b><br>
 * SCP 的目标函数是所有加权决策变量的总和.<br>
 * 第2.3章，第17页，设置覆盖问题（SCP）. <br>
 * <br>
 * c_j权重（也是成本、系数）被明确分配给SCP的子集j.<br>
 * 决策变量在 SCP 的解中 ({@link SCPSolution}) 明确定义.<br>
 * 因此，目标函数由权重 ({@link #weights}) 和规则 ({@link #getValue(SCPSolution)})组成,<br>
 * 它通过权重 ({@link #weights}) 和变量特征 ({@link SCPSolution}) 计算目标函数的值.<br>
 *
 * <p><img src="{@docRoot}/images/SCP.svg" alt=""></p>
 */
public class ObjectiveFunction {

    /**
     * 权重（包括成本、系数）<br>
     * 权重c_j的索引j对应于决策变量x_j的索引<br>
     */
    private float[] weights;

    /**
     * 具有随机权重启动的构造函数
     *
     * @param size 决策变量数 = SCP 的子集数
     */
    public ObjectiveFunction(int size) {
        this.initRandomWeights(size);
    }

    /**
     * 构造函数<br>
     * 取给定的权重
     *
     * @param weights 权重
     */
    public ObjectiveFunction(float[] weights) {
        this.weights = weights;
    }


    /**
     * 返回 SCP 解的目标函数值 ({@link SCPSolution})<br>
     * 目标函数值定义为加权决策变量的总和><br>
     *
     * @param solution     SCP 解的实例
     * @return             目标函数值
     */
    public float getValue(SCPSolution solution) {
        float value = 0f;
        boolean[] vars = solution.getVars();
        for (int j = 0; j < vars.length; j++) {
            value += (vars[j]) ? this.weights[j] : 0f;
        }
        return value;
    }

    /**
     * 返回权重数组
     *
     * @return 权重数组
     */
    public float[] getWeights() {
        return weights;
    }


    /**
     * 启动随机系数
     *
     * @param size 决策变量数
     */
    private void initRandomWeights(int size) {
        Random r = new Random();
        this.weights = new float[size];
        for (int j = 0; j < size; j++) {
            this.weights[j] = r.nextFloat();
        }
    }


    /**
     * 打印
     */
    public void print() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Weights:");
        for (int j = 0; j < this.weights.length; j++) {
            String value = df.format(this.weights[j]);
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
