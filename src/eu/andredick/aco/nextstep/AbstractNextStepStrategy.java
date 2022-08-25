package eu.andredick.aco.nextstep;

import eu.andredick.aco.combination.CombinationRule;
import eu.andredick.aco.construction.AbstractConstruction;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.pheromoneassociation.AbstractPheromoneAssociation;
import eu.andredick.aco.pheromoneperception.AbstractPheromonePerception;
import eu.andredick.aco.problem.AbstractSolution;

import java.util.List;

/**
 * <b>候选方案的抽象组成部分</b><br>
 * 第3.3.5章，第32页，候选方案的选择<br>
 * <br>
 * Die Komponente Alternativenauswahl wird von Konstruktionsheuristik {@link AbstractConstruction} 使用,<br>
 * 从给定候选方案（解决方案组件）中选择一项。<br>
 * 替代方案的选择基于启发式信息 {@link HeuristicInfoSet} <br>
 * 以及感知的信息素浓度 {@link AbstractPheromonePerception}。<br>
 * 通过组合功能 {@link CombinationRule}替代方案的值由启发式信息和感知的信息素浓度形成。<br>
 * <br>
 * 另一个依赖是信息素关联组件 (参见 {@link AbstractPheromoneAssociation}。<br>
 * 能够确定感知到的信息素值以及实际的信息素值。
 * <br>
 * 抽象组件的实现必须实现方法 {@link #chooseSubset(AbstractSolution, List)} 。
 * <p><img src="{@docRoot}/images/Nextstep.svg" alt=""></p>
 */
public abstract class AbstractNextStepStrategy<E extends AbstractPheromoneAssociation, S extends AbstractSolution> {

    /**
     * 特定问题的信息素关联组件
     */
    protected E pheromoneStructure;
    /**
     * 信息素感知
     */
    protected AbstractPheromonePerception perceptionRule;
    /**
     * 启发式信息
     */
    protected HeuristicInfoSet heuristics;
    /**
     * 组合功能（结合信息素感知值和启发式信息）
     */
    protected CombinationRule combinationRule;

    /**
     * 构造函数
     *
     * @param pheromoneStructure 信息素关联
     * @param perceptionRule     信息素感知
     * @param heuristics         启发式信息
     * @param combinationRule    组合功能
     */
    public AbstractNextStepStrategy(E pheromoneStructure,
                                    AbstractPheromonePerception perceptionRule,
                                    HeuristicInfoSet heuristics,
                                    CombinationRule combinationRule) {

        this.pheromoneStructure = pheromoneStructure;
        this.perceptionRule = perceptionRule;
        this.heuristics = heuristics;
        this.combinationRule = combinationRule;
    }

    /**
     * 从给定集合中选择候选解的抽象方法。<br>
     * 与构造启发式方法的接口 ({@link AbstractConstruction})。<br>
     *
     * @param solution         蚂蚁的部分解
     * @param availableSubsets 可用的替代方案
     * @return      选定的替代方案
     */
    public abstract Integer chooseSubset(S solution, List<Integer> availableSubsets);
}
