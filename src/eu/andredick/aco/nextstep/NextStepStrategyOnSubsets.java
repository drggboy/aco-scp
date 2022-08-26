package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.scp.SCPSolution;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>混合选择替代方案</b> 从随机和确定性替代选择，信息素与子集的关联<br>
 * <br>
 * 混合备选方案提供随机振荡的结果
 * (see {@link NextStepStrategyOnSubsetsStochastic}) 或确定性
 * 替代选择 (see {@link NextStepStrategyOnSubsetsDeterministic}).<br>
 * 这是通过实现随机数来实现的 {@code 0.0 <= q <= 1.0} 确定哪个与
 * 指定参数 {@code q0} 以下任一 {@code (q < q0)} 或以上 {@code (q0 <= q)} 谎言.<br>
 * 所以组件有一个参数.<br>
 * <br>
 * 备选选择组件已替换为设计启发式 {@link AbstractConstruction}
 * 用于从给定的备选项集中提取（解决方案组件）选择替代方案.<br>
 * 替代方案的选择基于启发式信息 {@link HeuristicInfoSet} 和
 * 感知的信息素浓度 {@link AbstractPheromonePerception}, 分配给备选方案或为备选方案计算.<br>
 * 通过组合功能 {@link CombinationRule} 替代方案的值由启发式信息和感知的信息素浓度形成.<br>
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public class NextStepStrategyOnSubsets extends
        AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution> {

    /**
     * 确定性选择（引用您自己的对象）
     */
    private NextStepStrategyOnSubsetsDeterministic nextStepDeterministic;

    /**
     * 随机选择（参考您自己的对象）
     */
    private NextStepStrategyOnSubsetsStochastic nextStepStochastic;

    /**
     * 确定随机和确定性选择影响的参数
     */
    private float q0_parameter;

    /**
     * 构造函数
     *
     * @param pheromonesStructure 信息素关联
     * @param perceptionRule      信息素感知
     * @param heuristics          启发式信息
     * @param combinationRule     组合功能
     * @param q0_parameter        调节随机和确定性分量影响的参数
     */
    public NextStepStrategyOnSubsets(PheromoneOnSubsets pheromonesStructure,
                                     AbstractPheromonePerception perceptionRule,
                                     HeuristicInfoSet heuristics,
                                     CombinationRule combinationRule,
                                     float q0_parameter) {

        super(pheromonesStructure, perceptionRule, heuristics, combinationRule);

        this.q0_parameter = q0_parameter;

        this.nextStepDeterministic =
                new NextStepStrategyOnSubsetsDeterministic(pheromonesStructure, perceptionRule, heuristics, combinationRule);

        this.nextStepStochastic =
                new NextStepStrategyOnSubsetsStochastic(pheromonesStructure, perceptionRule, heuristics, combinationRule);
    }

    /**
     * 作为随机数的已实现值的函数返回 {@code q} 和参数 {@link #q0_parameter}
     * 备择选择的概率或确定性结果.
     * 要确定备选方案，请 {@link #nextStepStochastic} 或者 {@link #nextStepDeterministic} 使用.
     *
     * @param solution         蚂蚁的部分解
     * @param availableSubsets 可用的替代方案
     * @return 选择结果
     */
    @Override
    public Integer chooseSubset(SCPSolution solution, List<Integer> availableSubsets) {

        // 创建随机数 0.0 <= q <= 1.0
        float q = ThreadLocalRandom.current().nextFloat();

        /* 取决于随机数和参数值q0的实现
         * 替代选择是随机的或确定性的
         */
        if (q < q0_parameter) {
            return this.nextStepStochastic.chooseSubset(solution, availableSubsets);
        } else {
            return this.nextStepDeterministic.chooseSubset(solution, availableSubsets);
        }
    }

}
