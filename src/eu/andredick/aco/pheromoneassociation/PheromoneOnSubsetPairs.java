package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;

/**
 * <b>信息素关联成分的表达</b>j, 与 SCP 子集对的关联 ({@link SCProblem}).<br>
 * 与 SCP 子集对的关联<br>
 * <br>
 * 在这种形式中，信息素与SCP（问题的实体）的子集对相关联。<br>
 * SCP的一对子集可以用信息素标记，<br>
 * 如果两个子集都包含在蚂蚁的构造解中。<br>
 * <br>
 * 与特定问题实体相关的信息素可以作为一个层，<br>
 * 此处设计了一些规则，包括信息素蒸发和信息素初始化。<br>
 * 信息素关联将信息素浓度分配给特定问题的实体。<br>
 * 因此，信息素关联的每种实现都对应到问题的特定实现。<br>
 * 这种依赖关系由泛型编程确定 {@code <P extends AbstractProblem>} 实现。<br>
 * 因此，有必要确定信息素关联的具体问题。<br>
 * <br>
 * 以下组件需要信息素关联：
 * <ul>
 * <li>主进程 {@link AbstractMasterProcess}</li>
 * <li>信息素初始化 {@link AbstractPheromoneInit}</li>
 * <li>信息素蒸发 {@link AbstractPheromoneEvaporation}</li>
 * <li>候选方案的选择 {@link AbstractNextStepStrategy}</li>
 * <li>信息素标记  {@link AbstractPheromoneUpdate}</li>
 * </ul>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneAssociation-a.svg" alt=""></p>
 * <br>
 */
public class PheromoneOnSubsetPairs extends AbstractPheromoneAssociation<SCProblem> {

    /**
     * 由二维对称阵列（矩阵）表示的SCP子集对上的信息素浓度.<br>
     * 数组的索引 i， j 对应于 SCP 的两个子集的索引（唯一映射）.<br>
     */
    private float[][] pheromoneValues;

    /**
     * 构造函数
     *
     * @param problem 集合覆盖问题的实例
     */
    public PheromoneOnSubsetPairs(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()][problem.getStructure().subsetsSize()];
    }

    /**
     * 启动整个信息素蒸发的时间步长.<br>
     * 所有信息素浓度均符合蒸发规则 {@link #evaporationRule}。<br>
     */
    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.evaporationRule.evaporate(pheromoneValues[i][j]);
            }
        }
    }

    /**
     * 使用起始规则启动 SCP 所有子集的信息素浓度 {@link #pheromoneInitRule}。<br>
     * 此方法建立整个信息素的初始状态，该状态在 ACO 算法启动时应存在.
     */
    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            for (int j = 0; j < this.pheromoneValues[i].length; j++) {
                pheromoneValues[i][j] = this.pheromoneInitRule.initValue();
            }
        }
    }

    /**
     * 始终提供 0.
     * 此方法是继承的，但不能使用，因为只能传递一个参数.
     * 两个索引 i，j 需要两个参数
     *
     * @param j 问题实体的索引
     * @return 0, 独立于输入
     */
    @Override
    public float getPheromone(int j) {
        System.out.println("PheromoneOnSubsetPairs.getPheromone / 失败!");
        return 0;
    }

    /**
     * 提供与 SCP 问题的子集对 （i，j） 相关的信息素浓度
     * @param i 对的第一个子集的索引
     * @param j 对的第二个子集的索引
     * @return 与子集对（i，j）相关的信息素浓度
     */
    public float getPheromone(int i, int j) {
        return this.pheromoneValues[i][j];
    }

    /**
     * 此方法是继承的，但不能使用，因为索引仅作为输入传递给子集。<br>
     * 无论输入如何，信息素关联的状态都没有变化。
     * @param j        问题实体的索引.
     * @param ph_delta 增加信息素浓度
     */
    @Override
    public void addPheromone(int j, float ph_delta) {
        System.out.println("PheromoneOnSubsetPairs.addPheromone / 失败!");
    }


    /**
     * 通过添加新的信息素来添加额外的信息素。<br>
     * 该方法以成分信息素标记的形式使用 ({@link AbstractPheromoneUpdate}) 使用.<br>
     * 相关的信息素浓度由 SCProblem 的子集对（i，j）的指数决定.
     *
     * @param i 对的第一个子集的索引
     * @param j 对的第二个子集的索引
     * @param ph_delta 信息素浓度增量
     */
    public void addPheromone(int i, int j, float ph_delta) {
        this.pheromoneValues[i][j] += ph_delta;
    }

}
