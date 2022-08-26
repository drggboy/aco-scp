package eu.andredick.aco.algorithm;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.ArrayList;

/**
 * <b>算法序列的统计信息。</b><br>
 * <br>
 * 在每个主进程中，将创建统计信息类的对象，<br>
 * 它收集有关迭代过程中的计算结果的数据。<br>
 */
public class Statistics {

    /**
     * 每次迭代中的最小目标函数值列表
     */
    private ArrayList<Float> iterationMinValues;

    /**
     * 每次迭代中的最大目标函数值列表
     */
    private ArrayList<Float> iterationMaxValues;

    /**
     * 所有迭代的最低目标函数值
     */
    private Float globalMinValue;

    /**
     * 所有迭代的最高目标函数值
     */
    private Float globalMaxValue;

    /**
     * 所有迭代的最低目标函数值的解
     */
    private AbstractSolution globalMinSolution;

    /**
     * 构造函数
     */
    public Statistics() {
        this.iterationMinValues = new ArrayList<>(100);
        this.iterationMaxValues = new ArrayList<>(100);
    }

    /**
     * 返回所有迭代的最低目标函数值.
     *
     * @return 所有迭代的最低目标函数值
     */
    public Float getGlobalMinValue() {
        return globalMinValue;
    }

    /**
     * 提供所有迭代的最低目标函数值的对应的解
     *
     * @return 所有迭代的最低目标函数值的对应的解
     */
    public AbstractSolution getGlobalMinSolution() {
        return globalMinSolution;
    }

    /**
     * 提供每次迭代中的最小目标函数值列表
     *
     * @return 每次迭代中的最小目标函数值列表
     */
    public float[] getIterationMinValuesArray() {
        return listToArray(this.iterationMinValues);
    }

    /**
     * 提供每次迭代中最大目标函数值的列表
     *
     * @return 每次迭代中的最大目标函数值列表
     */
    public float[] getIterationMaxValuesArray() {
        return listToArray(this.iterationMaxValues);
    }

    /**
     * 记录目标函数值的方法，以及沿迭代的关联解决方案（如果适用）.<br>
     * 对于统计信息，应传递在每次迭代中找到的与相关解找到的每个目标函数值.<br>
     * 此方法更新最大和最小目标函数值的列表.<br>
     * 该方法由主进程调用.<br>
     *
     * @param iteration 迭代索引
     * @param value     目标函数值
     * @param solution  相关解
     */
    public void setValue(int iteration, float value, AbstractSolution solution) {
        if (this.setIterationMinValue(iteration, value, solution)) {
            this.setGlobalMinValue(value, solution);
        }
        if (this.setIterationMaxValue(iteration, value, solution)) {
            this.setGlobalMaxValue(value, solution);
        }
    }

    /**
     * 设置 <b>最低</b> 迭代的目标函数值和相关的解.
     *
     * @param iteration 迭代所有
     * @param value     目标函数值
     * @param solution  相关的解
     * @return 如果设置成功，则为 True
     */
    private boolean setIterationMinValue(int iteration, float value, AbstractSolution solution) {
        Float oldValue = null;
        try {
            oldValue = iterationMinValues.get(iteration);
        } catch (IndexOutOfBoundsException e) {
            iterationMinValues.add(iteration, value);
            return true;
        }

        if (oldValue == null || value < oldValue) {
            iterationMinValues.set(iteration, value);
            return true;
        }
        return false;
    }

    /**
     * 设置每次迭代 <b>最大</b> 的目标函数值和相关的解。
     *
     * @param iteration 迭代索引
     * @param value     目标函数值
     * @param solution  相关的解
     * @return 如果设置成功，则为 True
     */
    private boolean setIterationMaxValue(int iteration, float value, AbstractSolution solution) {
        Float oldValue = null;
        try {
            oldValue = iterationMaxValues.get(iteration);
        } catch (IndexOutOfBoundsException e) {
            iterationMaxValues.add(iteration, value);
            return true;
        }

        if (oldValue == null || value > oldValue) {
            iterationMaxValues.set(iteration, value);
            return true;
        }
        return false;
    }

    /**
     * 设置所有迭代 <b>最大</b> 的目标函数值
     *
     * @param value 目标函数值
     * @param solution 相关的解
     * @return 如果设置成功，则为 True
     */
    private boolean setGlobalMaxValue(float value, AbstractSolution solution) {
        if (this.globalMaxValue == null || value > this.globalMaxValue) {
            this.globalMaxValue = value;
            return true;
        }
        return false;
    }

    /**
     * 设置所有迭代 <b>最小</b> 的目标函数值
     *
     * @param value 目标函数值
     * @param solution 相关的解
     * @return 如果设置成功，则为 True
     */
    private boolean setGlobalMinValue(float value, AbstractSolution solution) {
        if (this.globalMinValue == null || value < this.globalMinValue) {
            this.globalMinValue = value;
            this.globalMinSolution = solution;
            return true;
        }
        return false;
    }

    /**
     * 将数据结构列表转换为数组
     *
     * @param list 浮点元素的列表
     * @return Array float[]
     */
    private float[] listToArray(ArrayList<Float> list) {
        float[] values = new float[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = list.get(i);
        }
        return values;
    }
}
