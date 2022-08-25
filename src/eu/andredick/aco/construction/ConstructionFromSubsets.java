package eu.andredick.aco.construction;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Structure;
import eu.andredick.tools.ArrayTools;
import eu.andredick.tools.Tools;

import java.util.List;

/**
 * <b>实现启发式构造组件</b><br>
 * 第 3.3.4 章，第 30 页，启发式构造<br>
 * <br>
 * <b>从子集开始</b>进行蚂蚁 SCP 解的构建<br>
 * 启发式构造由蚂蚁类 {@link eu.andredick.aco.ant.ACOAnt}用于构建新的解.<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 */
public class ConstructionFromSubsets extends AbstractConstruction<AbstractNextStepStrategy<PheromoneOnSubsets, SCPSolution>, SCProblem, SCPSolution> {

    /**
     * 构造函数
     * @param nextStepRule 任意候选集选择组件 (参见 {@link AbstractNextStepStrategy})
     */
    public ConstructionFromSubsets(AbstractNextStepStrategy nextStepRule) {
        super(nextStepRule);
    }


    /**
     * <b>解的构建在以下步骤中迭代进行:</b><br>
     * 1. 通过 NextStep 选择其中一个子集，并将其添加到解决方案中<br>
     * 2. 将此子集添加到 Solution 中<br>
     * 3. 确定所选子集中包含的基本元素<br>
     * 4. 从仍然可用的所有子集中删除这些基元<br>
     * 5. 删除在最后一步后变为空的所有子集<br>
     * 6. 从尚未涵盖的基本元素集中删除基本元素 （3.）<br>
     * 7. 如果还有其他未覆盖的基本元素，则返回 1<br>
     *
     * @param problem 待解的 SCProblem
     * @return 允许构建的解
     */
    @Override
    public SCPSolution construct(SCProblem problem) {

        Structure structure = problem.getStructure();
        SCPSolution solution = new SCPSolution(problem);

        // 提供子集中包含的基元列表数组
        List<Integer> subsets = Tools.getIndexList(structure.subsetsSize()); //geordnet

        // 为子集和基元部署禁忌列表
        boolean[] tabuSubsets = ArrayTools.getZeroBoolArray(structure.subsetsSize());
        boolean[] tabuElements = ArrayTools.getZeroBoolArray(structure.elementsSize());

        // 提供包含子集中包含的项的总和的工作列表
        int[] subsetSizes = new int[structure.subsetsSize()];
        for (int g = 0; g < subsetSizes.length; g++) {
            subsetSizes[g] = structure.getElementsInSubset(g).size();
        }

        // 尚未涵盖的基本元素数量的计数器
        int elementsRemain = structure.elementsSize();

        // 迭代，直到没有更多基本元素要覆盖
        while (elementsRemain != 0) {

            // 从尚未在解中的子集中选择子集
            int subsetIndex = this.nextStepRule.chooseSubset(solution, subsets);

            // 将子集添加到解中
            solution.addSubset(subsetIndex);

            // 在 TabooList 中选择选定的子集
            tabuSubsets[subsetIndex] = true;

            // 确定所选子集中包含的基本元素
            List<Integer> elementsInSubset = structure.getElementsInSubset(subsetIndex);

            // 此外，必须标识本身就是所选子集的子集
            // 即覆盖相同元素的一部分的子集，而不是其他元素的子集
            for (Integer e : elementsInSubset) {
                if (!tabuElements[e]) {
                    List<Integer> subsetsWith_e = structure.getSubsetsWithElement(e);
                    for (Integer s : subsetsWith_e) {
                        if (!tabuSubsets[s]) {
                            subsetSizes[s]--;
                            if (subsetSizes[s] == 0) {
                                tabuSubsets[s] = true;
                                subsets.remove(s);
                            }
                        }
                    }
                    tabuElements[e] = true;
                    elementsRemain--;
                }
            }
        }

        return solution;
    }
}
