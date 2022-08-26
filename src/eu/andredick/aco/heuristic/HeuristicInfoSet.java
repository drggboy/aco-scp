package eu.andredick.aco.heuristic;

import eu.andredick.aco.problem.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>启发式信息</b><br>
 * <br>
 * 该类是不同启发式信息的容器,<br>
 * 蚂蚁在构建解时使用。<br>
 * 该组件用于选择候选方案 (参见 {@link eu.andredick.aco.nextstep.AbstractNextStepStrategy})。<br>
 *
 * <p><img src="{@docRoot}/images/Heuristics.svg" alt=""></p>
 */
public class HeuristicInfoSet<S extends AbstractSolution> implements HeuristicRule<S> {

    /**
     * 启发式信息组件的特征集
     */
    private ArrayList<HeuristicRule> rules;

    /**
     * 构造 函数
     */
    public HeuristicInfoSet() {
        rules = new ArrayList<>();
    }

    /**
     * 添加一种形式的启发式信息
     *
     * @param rule 启发式信息的表达
     */
    public void addRule(HeuristicRule rule) {
        rules.add(rule);
    }

    /**
     * 将丰富值的请求转发给所有包含的启发式算法,<br>
     * 形成所获得值的乘积并返回它们.
     *
     * @param solution         蚂蚁设计过程中的部分解
     * @param availableSubsets 所有可用的候选方案
     * @param subset           待求的子集
     * @return 启发式信息的价值
     */
    public float getValue(S solution, List<Integer> availableSubsets, Integer subset) {
        float product = 1;
        for (HeuristicRule rule : rules) {
            product *= rule.getValue(solution, availableSubsets, subset);
        }
        return product;
    }
}
