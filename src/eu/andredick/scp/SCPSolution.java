package eu.andredick.scp;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类表示集合覆盖问题（SCP）的解。<br>
 * 该解决方案由决策变量向量组成
 * 或一系列子集。两种表示形式保持同步.
 */
public class SCPSolution extends AbstractSolution<SCProblem> {
    /**
     * 此解决方案所属的 SCP 抽象问题
     */
    private SCProblem problem;

    /**
     * 决策变量向量
     */
    private boolean[] vars;

    /**
     * 子集的选择
     */
    private List<Integer> subsetsList;

    /**
     * 覆盖给定元素的子集数
     */
    private Integer[] coveredElementsCount;

    /**
     * 临时解决方案价值
     */
    private Float value;


    /**
     * 构造 函数
     *
     * @param problem 相关的 SCP 抽象问题
     */
    public SCPSolution(SCProblem problem) {
        this.problem = problem;
        this.vars = new boolean[problem.getStructure().subsetsSize()];
        this.subsetsList = new ArrayList<>();
        this.coveredElementsCount = new Integer[problem.getStructure().elementsSize()];
        this.initZeros();
        this.value = null;
    }


    /**
     * 返回相关的 SCP-AbstractProblem
     *
     * @return 相关 SCP 问题
     */
    @Override
    public SCProblem getProblem() {
        return this.problem;
    }

    /**
     * 返回解的目标函数值
     *
     * @return 解决方案的目标函数值
     */
    @Override
    public float getValue() {
        if (this.value == null) {
            this.value = this.problem.getObjectiveFunction().getValue(this);
        }
        return this.value;
    }

    @Override
    public SCPSolution createNew() {
        return new SCPSolution(this.problem);
    }


    /**
     * 基本元素是否被解的一个子集覆盖?
     *
     * @param element 基本元素
     * @return 如果基元被解的至少一个子集覆盖，则为 True
     */
    public boolean isElementCovered(Integer element) {
        return this.coveredElementsCount[element] != null && this.coveredElementsCount[element] > 0;
    }


    /**
     * 将子集添加到解决方案
     *
     * @param subset 要添加的子集
     * @return 如果尚未解决，则为真
     */
    public boolean addSubset(int subset) {
        if (!vars[subset]) {
            vars[subset] = true;
            subsetsList.add(subset);
            this.coverElements(subset);
            this.value = null;
            return true;
        }
        return false;
    }


    /**
     * 从解决方案中删除子集
     *
     * @param subset 要删除的子集
     * @return 如果存在于溶液中，则为真
     */
    public boolean removeSubset(int subset) {
        if (vars[subset]) {
            vars[subset] = false;
            subsetsList.remove((Integer) subset);
            this.decoverElements(subset);
            this.value = null;
            return true;
        }
        return false;
    }


    /**
     * 通过添加子集来调整基本元素覆盖情况的计数器<br>
     * 可以用于添加子集后更新元素覆盖情况
     *
     * @param subset 添加的子集
     */
    private void coverElements(int subset) {
        List<Integer> elementsInSubset = this.problem.getStructure().getElementsInSubset(subset);
        for (Integer e : elementsInSubset) {
            if (this.coveredElementsCount[e] == null) {
                this.coveredElementsCount[e] = 1;
            } else this.coveredElementsCount[e]++;
        }
    }


    /**
     * 通过删除子集来调整基本元素覆盖情况计数器<br>
     * 可以用于删除子集后更新元素覆盖情况
     *
     * @param subset 删除子集
     */
    private void decoverElements(int subset) {
        List<Integer> elementsInSubset = this.problem.getStructure().getElementsInSubset(subset);
        for (Integer e : elementsInSubset) {
            this.coveredElementsCount[e]--;
        }
    }

    /**
     * 返回决策变量（0、1组成）
     *
     * @return 具有决策变量的布尔数组（0、1组成）
     */
    public boolean[] getVars() {
        return this.vars;
    }

    /**
     * 所有被选中的子集
     *
     * @return 子集的选择
     */
    public List<Integer> getSubsets() {
        return this.subsetsList;
    }

    /**
     * 判断解决方案是否包含子集
     *
     * @param index 子集索引
     * @return      如果子集包含在解决方案中，则为 true
     */
    public boolean hasSubset(int index) {
        return this.vars[index];
    }

    /**
     * 判断解决方案是否有效
     *
     * @return 评估结果
     */
    public boolean isPermissible() {
        for (int i = 0; i < this.problem.getStructure().elementsSize(); i++) {
            int sum = 0;
            for (int j = 0; j < vars.length; j++) {
                sum += (vars[j] & this.problem.getStructure().getRelation(i, j)) ? 1 : 0;
            }
            if (sum == 0) return false;
        }
        return true;
    }


    /**
     * 在创建空解后初始化具有 0 的决策变量
     */
    private void initZeros() {
        for (int j = 0; j < this.vars.length; j++) {
            vars[j] = false;
        }
//        for (int i = 0; i < this.vars.length; i++) {
//            vars[i] = false;
//        }
    }

    /**
     * 打印解
     */
    public void print() {
        System.out.println("SCPSolution:");
        for (int j = 0; j < this.vars.length; j++) {
            String value = String.valueOf(this.vars[j]);
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
