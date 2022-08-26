package eu.andredick.aco.pheromoneassociation;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;
import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneevaporation.PheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneinit.PheromoneInit;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.pheromoneupdate.AbstractPheromoneUpdate;
import eu.andredick.scp.SCProblem;

/**
 * <b>信息素关联部分的实现</b>, 与 SCP 问题的关联 ({@link SCProblem})。<br>
 * <br>
 * SCP的每个子集被分配一个信息素浓度值.<br>
 * 蚂蚁用信息素标记其解中包含的子集.<br>
 * <br>
 * 与特定问题实体相关的信息素可以作为一个层,<br>
 * 信息素关联设计了一些规则（信息素蒸发和信息素起始）.<br>
 * 定义了到特定问题实体的信息素浓度。<br>
 * 因此，信息素关联的每种实现都对应特定的问题<br>
 * 这种依赖关系由泛型编程 {@code <P extends AbstractProblem>} 确定实现.<br>
 * 因此，在实现过程中需要确定信息素关联的具体问题。<br>
 * <br>
 * 以下组件中需要信息素关联:
 * <ul>
 * <li>主进程 {@link AbstractMasterProcess}</li>
 * <li>信息素起始 {@link AbstractPheromoneInit}</li>
 * <li>信息素蒸发 {@link AbstractPheromoneEvaporation}</li>
 * <li>候选方案的选择 {@link AbstractNextStepStrategy}</li>
 * <li>信息素标记  {@link AbstractPheromoneUpdate}</li>
 * </ul>
 * <br>
 * <p><img src="{@docRoot}/images/PheromoneAssociation-a.svg" alt=""></p>
 * <br>
 */
public class PheromoneOnSubsets extends AbstractPheromoneAssociation<SCProblem> {

    /**
     * 用数组表示的问题实体上的信息素浓度。<br>
     * 数组的索引对应于问题实体的索引（唯一映射）。<br>
     */
    private float[] pheromoneValues;

//    /**
//     * 信息素蒸发因子
//     */
//    private float rho;

    /**
     * 构造函数
     *
     * @param problem 集合覆盖问题的实例
     */
    public PheromoneOnSubsets(SCProblem problem) {
        super(problem);
        pheromoneValues = new float[problem.getStructure().subsetsSize()];
    }

    /**
     * 启动整个信息素蒸发的时间步长。<br>
     * 所有信息素浓度应符合蒸发规则 {@link #evaporationRule}。<br>
     */
    @Override
    public void evaporatePheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.evaporationRule.evaporate(pheromoneValues[i]);
        }
    }


    /**
     * 使用起始规则启动 SCP 子集的信息素浓度 {@link #pheromoneInitRule}.<br>
     * 此方法建立整个信息素的初始状态，该状态在 ACO 算法启动时应存在.
     */
    @Override
    public void initPheromones() {
        for (int i = 0; i < this.pheromoneValues.length; i++) {
            pheromoneValues[i] = this.pheromoneInitRule.initValue();
        }
    }


    /**
     * 提供与问题的 j 子集相关的信息素浓度
     * @param j 问题子集的索引
     * @return 信息素浓度与问题的子集j相关
     */
    @Override
    public float getPheromone(int j) {
        return this.pheromoneValues[j];
    }

    /**
     * 在当前基础上添加额外的信息素.<br>
     * 该方法由信息素标记组件 ({@link AbstractPheromoneUpdate}) 使用.
     * @param j        问题实体的索引.
     * @param ph_delta 增加的信息素浓度
     */
    @Override
    public void addPheromone(int j, float ph_delta) {
        this.pheromoneValues[j] += ph_delta;
    }

}
