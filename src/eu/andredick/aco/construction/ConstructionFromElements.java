package eu.andredick.aco.construction;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Structure;
import eu.andredick.tools.Tools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>实现启发式构造组件。</b><br>
 * 第 3.3.4 章，第 30 页，启发式构造<br>
 * <br>
 * <b>从基本要素开始</b>进行蚂蚁 SCP 解的构建 <br>
 * 启发式构造由蚂蚁类 {@link eu.andredick.aco.ant.ACOAnt} 用于构建新的解。<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 */
public class ConstructionFromElements extends AbstractConstruction<AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution>, SCProblem, SCPSolution> {

    public ConstructionFromElements(AbstractNextStepStrategy nextStepRule) {
        super(nextStepRule);
    }

    /**
     * <b>解的构建在以下步骤中迭代进行:</b><br>
     * 1. 随机选择尚未涵盖的基本元素<br>
     * 2. 确定包含基本元素且尚未在解决方案中的子集<br>
     * 3. 通过 NextStep 选择其中一个子集<br>
     * 4. 确定所选子集中包含的基本元素<br>
     * 5. 将特定基元添加到已覆盖的基本元集中<br>
     * 6. 如果还有其他未覆盖的基本元素，则返回 1<br>
     *
     * @param problem 必须为其构建解的 SCProblem
     * @return 允许构建的解
     */
    @Override
    public SCPSolution construct(SCProblem problem) {
        Structure structure = problem.getStructure();
        SCPSolution solution = new SCPSolution(problem);

        List<Integer> elements = Tools.getIndexList(structure.elementsSize()); //有序
        List<Integer> tabuSubsets = new LinkedList<>();
        //List<Integer> subsets = Tools.getIndexList(structure.subsetsSize()); //有序

        while (!elements.isEmpty()) {
            // 从基本集合中随机选择元素
            int r = ThreadLocalRandom.current().nextInt(elements.size());           //Zufahlszahl 0 .. mE.size()
            int elementIndex = elements.get(r);

            // 所选项目的所有子集
            List<Integer> subsetsWithElement;
            subsetsWithElement = new LinkedList<>(structure.getSubsetsWithElement(elementIndex));

            // 与其余子集的交集
            subsetsWithElement.removeAll(tabuSubsets);

            // 从仍然可用的子集中选择子集
            int subsetIndex = this.nextStepRule.chooseSubset(solution, subsetsWithElement);

            // 从要考虑的元素中删除选定子集的所有元素
            for (Integer e : structure.getElementsInSubset(subsetIndex)) {
                elements.remove(e);
            }

            // 从要考虑的子集中删除选定的子集
            // subsets.remove( (Integer)subsetIndex);
            tabuSubsets.add((Integer) subsetIndex);

            // 将子集插入到解中
            solution.addSubset(subsetIndex);
        }
        return solution;
    }
}
