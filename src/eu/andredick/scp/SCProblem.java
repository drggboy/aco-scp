package eu.andredick.scp;

import eu.andredick.aco.problem.AbstractProblem;

/**
 * 该类表示 <b>集合覆盖问题 (SCP)</b>.<br>
 * 在 ACO 元启发式的背景下，SCP是抽象问题的一种表现形式。 ({@link AbstractProblem})。<br>
 * 第 2.3 章，第 17 页，集合覆盖问题 (SCP). <br>
 * <br>
 * <b>定义:</b> 给定一组由基本元素构成的集合.
 * 从中选取子集,
 * 其并集是所有基本元素的集合。求解优化问题：
 * 求集合数最小的选择，如果子集存在权值，则选择最小的加权值<br>
 * <br>
 * SCP由结构矩阵 ({@link Structure}) 和目标函数 ({@link ObjectiveFunction}) 组成。<br>
 *  (参见 {@link SCPSolution}) 定义了解决方案并将SCP决策变量的定义作为实例包括在内.<br>
 * 信息：决策变量 X_j={0，1} 确定子集 j 是否应包含在解决方案中。<br>
 *
 * <p><img src="{@docRoot}/images/SCP.svg" alt=""></p>
 */
public class SCProblem extends AbstractProblem {

    /**
     * 具体问题的名称（问题实例）
     */
    protected String name;

    /**
     * 覆盖矩阵 [a_ij]<br>
     * 行数为元素数目，列数为子集数目。
     */
    private Structure structure;

    /**
     * 目标函数
     */
    private ObjectiveFunction objectiveFunction;

    /**
     * 构造函数
     *
     * @param relations                     布尔矩阵 m x n，行数为元素数目，列数为子集数目
     * @param objectiveFunctionCoefficients 目标函数的系数 c_j
     */
    public SCProblem(boolean[][] relations, float[] objectiveFunctionCoefficients) {
        this.structure = new Structure(relations);
        this.objectiveFunction = new ObjectiveFunction(objectiveFunctionCoefficients);
        this.name = null;
    }

    /**
     * 构造函数
     *
     * @param relations                     布尔矩阵 m x n，行数为元素数目，列数为子集数目
     * @param objectiveFunctionCoefficients 目标函数c_j系数
     * @param name                          问题的名称
     */
    public SCProblem(boolean[][] relations, float[] objectiveFunctionCoefficients, String name) {
        this.structure = new Structure(relations);
        this.objectiveFunction = new ObjectiveFunction(objectiveFunctionCoefficients);
        this.name = name;
    }

    /**
     * 返回问题实例的名称
     *
     * @return 问题的名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 提供问题实例的结构矩阵
     *
     * @return 结构矩阵
     */
    public Structure getStructure() {
        return this.structure;
    }

    /**
     * 返回问题实例的目标函数
     *
     * @return 目标函数
     */
    public ObjectiveFunction getObjectiveFunction() {
        return this.objectiveFunction;
    }


    /**
     * 检查 SCP 问题实例的一致性
     *
     * @return 一致时为真
     */
    public boolean isConsistent() {
        System.out.println("SCProblem isConsistent ...");
        boolean[][] relations = this.getStructure().getRelations();
        boolean success = true;
        int sumCovers_i = 0;
        for (int i = 0; i < relations.length; i++) {
            int sum_i = 0;
            for (int j = 0; j < relations[i].length; j++) {
                if (relations[i][j]) sum_i++;
            }
            if (sum_i == 0) {
                System.out.println(" 元素 " + i + " 未被任何列覆盖");
                success = false;
            }
            if (this.structure.getSubsetsWithElement(i).size() != sum_i) {
                System.out.println(" 元素 " + i + " 不一致");
                success = false;
            }
            sumCovers_i += sum_i;
        }
        System.out.println(" 矩阵中至少覆盖1倍 - ok！");
        int sumCovers = 0;
        for (int i = 0; i < structure.elementsSize(); i++) {
            int coveredTimes = structure.getSubsetsWithElement(i).size();
            sumCovers += coveredTimes;
            if (coveredTimes < 1) System.out.println(" Element " + i + " 未涵盖");
        }
        float a = sumCovers / structure.elementsSize();
        float b = sumCovers_i / relations.length;
        System.out.println(" 平均覆盖率: " + a + " " + b);

        System.out.println("SCProblem isConsistent ..." + success);
        return success;
    }
}
