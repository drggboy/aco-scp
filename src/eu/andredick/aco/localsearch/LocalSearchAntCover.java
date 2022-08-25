package eu.andredick.aco.localsearch;

import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * <b>使用 Ant Cover 搜索策略实现局部搜索.</b><br>
 * 第 3.3.9 章，第 37 页，局部搜索<br>
 * <br>
 * 局部搜索由 {@link eu.andredick.aco.ant.ACOAnt} 类中的蚂蚁执行使用.<br>
 * 局部搜索基于先前构建的解，并在必要时提供改进的解.
 * <p><img src="{@docRoot}/images/LocalSearch.svg" alt=""></p>
 */
public class LocalSearchAntCover extends AbstractLocalSearch<SCPSolution> {

    /**
     * 静态变量，包含结构矩阵的所有列
     * 按照降序排序。将在第一次调用时使用
     * 的局部搜索，然后对所有搜索保持不变
     * .
     */
    private static TreeSet<WeightedColumn> sortedColumns;

    /**
     * 静态类变量，对于每行 i 都有“最佳”
     * 覆盖列 j = 最佳覆盖列[i].
     * 在第一次调用本地搜索时初始化，并且是
     * 则对于所有进一步的调用，可用时保持不变.
     */
    private static WeightedColumn[] bestCoveringColumns;

    /**
     * 静态类变量，用于确定 的初始化状态
     * 静态变量排序列和最佳覆盖率列.
     */
    private static boolean staticFieldsReady = false;

    /**
     * 提供基于蚂蚁覆盖策略的局部搜索方法
     * 类的所有功能都已准备就绪。从蚂蚁那里得到一个
     * 并尝试通过构建的、允许的初始解决方案
     * 逐列。为此，传递的解决方案的列为
     * 依次按权重降序排列，必要时删除
     * 或替换为更好的列。解的可受理性仍然适用
     * 在任何时候，并且只更改为
     * 提高其目标函数值的解，或
     * 至少不会恶化。在初始解的每一列之后
     * 一旦考虑，如果可能的话，优化，
     * 改进的解。
     *
     * @param solution 局部搜索的初始解需要由蚂蚁改进
     * @return 通过局部搜索改进的解
     */
    @Override
    public SCPSolution search(SCPSolution solution) {

        // 所提交解的基础SCP问题
        SCProblem problem = solution.getProblem();

        /* 在局部搜索的第一次调用时，静态
         * 类变量根据正在考虑的 SCP 进行初始化
         */
        if (!staticFieldsReady) initStaticFields(problem);

        /* 按质量对初始解决方案的列进行排序和保存
         * 插入到树集中.
         */
        TreeSet<WeightedColumn> sortedSolution = sortSolution(problem, solution);

        /* 通过解决方案列的树集降序迭代器，因为它们
         * 按质量降序处理
         */
        Iterator<WeightedColumn> decIt = sortedSolution.descendingIterator();

        /* 设置一个数组，其中对于每行 i 的当前数
         * 覆盖解;基于起始解决方案
         * 初始化并更新每个更改
         */
        int[] nrOfCoveringColumns = getNrOfCoveringColumns(problem, solution);

        /* 当前视为可比较的解决方案列的变量
         * 加权列对象
         */
        WeightedColumn aktColumn;

        /* 查看时排他性来自的行的列表
         * 的当前考虑的解决方案列可以覆盖
         */
        ArrayList<Integer> exclusivelyCoveredRows = new ArrayList<Integer>();

        // 通过降序迭代器以降序质量遍历所有解决方案列
        while (decIt.hasNext()) {
            aktColumn = decIt.next();

            // 收集由 aktColumn 独家覆盖的行
            exclusivelyCoveredRows.clear();
            for (Integer i : problem.getStructure().getElementsInSubset(aktColumn.getIndex()))
                if (nrOfCoveringColumns[i] == 1) exclusivelyCoveredRows.add(i);

            // 进一步的顺序取决于 aktColumn 专门覆盖的行数
            switch (exclusivelyCoveredRows.size()) {

                case 0:
                    /* 没有一行完全由aktColumn覆盖;
                     * aktColumn 是冗余的，可以从解决方案中删除 */
                    nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                    break;

                case 1: /* 正好有一行 i 完全由 aktColumn 覆盖;
                 * 将 aktColumn 与最佳 i 覆盖列进行比较，并且
                 * 如有必要，请将解决方案中的 aktColumn 替换为 */
                    WeightedColumn bestColumn = bestCoveringColumns[exclusivelyCoveredRows.get(0)];
                    if (aktColumn.compareTo(bestColumn) != 0) {
                        nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                        nrOfCoveringColumns = addAndUpdate(solution, bestColumn.getIndex(), nrOfCoveringColumns);
                    }
                    break;

                case 2:
                    /* 正好有两行 i1， i2 由 aktColumn 独家覆盖;
                     * 将 aktColumn 与覆盖列的最佳 i1、i2 和
                     * 如有必要，请将解决方案中的 aktColumn 替换为 */
                    WeightedColumn bestColumn1 = bestCoveringColumns[exclusivelyCoveredRows.get(0)];
                    WeightedColumn bestColumn2 = bestCoveringColumns[exclusivelyCoveredRows.get(1)];

                    if (bestColumn1.compareTo(bestColumn2) == 0) {
                        if (aktColumn.compareTo(bestColumn1) != 0) {
                            /* i1 和 i2 具有相同的最佳重叠列,
                             * 这与aktColumn不同;
                             * 用最好的覆盖柱代替aktColumn
                             */
                            nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn1.getIndex(), nrOfCoveringColumns);
                        }
                    } else // 两行具有不同的最佳列
                        if (bestColumn1.getWeight() + bestColumn2.getWeight() <= aktColumn.getWeight()) { // die Summe der Kosten der beiden besten Spalten ist immer noch mindestens so gut wie die von aktSet
                            /* 两个最佳列的成本之和小于等于
                             * daktColumn的成本;将 aktColumn 替换为两个最佳列
                             */
                            nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn1.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn2.getIndex(), nrOfCoveringColumns);
                        }
                    break;

                default:
                    /* 超过两条线由aktColumn独家覆盖;
                     * 在解决方案中保持 aktColumn 不变，并使用下一个
                     * 解决方案柱堡 */
                    break;
            }
        }
        return solution;
    }

    /**
     * 初始化静态类变量 sortedColumns 的方法
     * 和最佳列。针对并行 ACO 算法进行同步.
     *
     * @param problem 基础 SCP
     */
    private synchronized void initStaticFields(SCProblem problem) {
        if (!staticFieldsReady) {
            initSortedColumns(problem);
            initBestColumns(problem);
            staticFieldsReady = true;
        }
    }

    /**
     * 初始化已排序的列静态类变量的方法.
     * 由 initStaticFields 调用.
     *
     * @param problem 基础 SCP
     */
    private synchronized void initSortedColumns(SCProblem problem) {
        if (sortedColumns == null) {
            sortedColumns = new TreeSet<WeightedColumn>();
            WeightedColumn aktColumn;
            float[] weights = problem.getObjectiveFunction().getWeights();
            for (int j = 0; j < problem.getStructure().subsetsSize(); j++) {
                aktColumn = new WeightedColumn(j, weights[j], problem.getStructure().getElementsInSubset(j).size());
                sortedColumns.add(aktColumn);
            }
        }
    }

    /**
     * 初始化最佳列静态类变量的方法.
     * 由 initStaticFields 调用.
     *
     * @param problem 基础 SCP
     */
    private synchronized void initBestColumns(SCProblem problem) {
        if (bestCoveringColumns == null) {
            int nrOfRows = problem.getStructure().elementsSize();
            bestCoveringColumns = new WeightedColumn[nrOfRows];
            for (int i = 0; i < nrOfRows; i++) setBestColumn(i, problem);
        }
    }

    /**
     * initBestColumns 的帮助器方法。搜索最好的一行 i
     * 排序列中的覆盖列并将其另存为可比较列
     * 最佳覆盖列中的加权列对象[i].
     *
     * @param i       结构矩阵中要为其设置最佳重叠列的行的索引
     * @param problem 基础 SCP
     */
    private synchronized void setBestColumn(int i, SCProblem problem) {
        WeightedColumn column;

        /* 由于排序列是按降序质量排序的，因此仅
         * 连续搜索第一个 i 覆盖列。这些
         * 然后自动成为最好的.
         */
        Iterator<WeightedColumn> it = sortedColumns.iterator();
        boolean covered = false;
        while (!covered && it.hasNext()) {
            column = it.next();
            if (problem.getStructure().getRelation(i, column.getIndex())) {
                bestCoveringColumns[i] = column;
                covered = true;
            }
        }
    }

    /**
     * 按质量对初始解决方案的列进行排序的方法.
     * 它们作为加权列对象进行排序并插入到树集中.
     *
     * @param problem  基础 SCP
     * @param solution 要分类的初始解决方案
     * @return 初始解决方案作为按质量排序的加权列树集
     */
    private TreeSet<WeightedColumn> sortSolution(SCProblem problem, SCPSolution solution) {
        TreeSet<WeightedColumn> sortedSolution = new TreeSet<WeightedColumn>();
        float[] weights = problem.getObjectiveFunction().getWeights();
        List<Integer> columnsInSolution = solution.getSubsets();
        for (int column : columnsInSolution)
            sortedSolution.add(new WeightedColumn(column, weights[column], problem.getStructure().getElementsInSubset(column).size()));
        return sortedSolution;
    }

    /**
     * 对于结构矩阵的每一行超过重叠数的方法
     * 从通过的解决方案中确定的解决方案列.
     *
     * @param problem  基础 SCP
     * @param solution 考虑的解决方案
     * @return int 数组，用于在索引 i 下保存覆盖行 i 的解决方案列数
     */
    private int[] getNrOfCoveringColumns(SCProblem problem, SCPSolution solution) {
        int nrOfRows = problem.getStructure().elementsSize();
        List<Integer> columnsInSolution = solution.getSubsets();
        int[] nrOfCoveringColumns = new int[nrOfRows];
        for (int i = 0; i < nrOfRows; i++) {
            nrOfCoveringColumns[i] = 0;
            for (int column : columnsInSolution)
                if (problem.getStructure().getRelation(i, column)) nrOfCoveringColumns[i]++;
        }
        return nrOfCoveringColumns;
    }

    /**
     * 包含具有从传递的解决方案传递的索引的列的方法
     * 解，并且在传递的数组中所有
     * 列覆盖的行减少了 1.
     *
     * @param solution              从中删除列的解决方案
     * @param indexOfColumnToDelete 要删除的列的索引。
     * @param nrOfCoveringColumns   数组，它保存所有行的已传递解决方案中覆盖这些解决方案的解决方案列数
     * @return 更新后的数组，用于保存删除列后覆盖每行的解决方案列数。
     */
    private int[] removeAndUpdate(SCPSolution solution, int indexOfColumnToDelete, int[] nrOfCoveringColumns) {

        // 从解决方案中删除列
        solution.removeSubset(indexOfColumnToDelete);

        // 更新覆盖集的编号
        for (int i : solution.getProblem().getStructure().getElementsInSubset(indexOfColumnToDelete))
            nrOfCoveringColumns[i]--;

        return nrOfCoveringColumns;
    }

    /**
     * 给通过的解决方案提供具有通过的列的方法
     * 索引，并在传递的数组中将所有加法的值
     * 列覆盖的行增加 1
     *
     * @param solution            要向其添加列的解决方案
     * @param indexOfColumnToAdd  要添加的列的索引。
     * @param nrOfCoveringColumns 数组，它保存所有行的已传递解中覆盖这些解的解列数
     * @return Den aktualisierten 包含添加新列后覆盖所有行的解决方案列数的数组
     */
    private int[] addAndUpdate(SCPSolution solution, int indexOfColumnToAdd, int[] nrOfCoveringColumns) {

        // 向解添加列
        solution.addSubset(indexOfColumnToAdd);

        // nrOfCoveringSets aktualisieren
        for (int i : solution.getProblem().getStructure().getElementsInSubset(indexOfColumnToAdd))
            nrOfCoveringColumns[i]++;
        return nrOfCoveringColumns;
    }


    /**
     * 在结构矩阵的列上引入顺序的类
     * 在一审中按成本，在第二审中按承保数量
     * 行和最后一个实例（用于等于（） 一致性）按列索引
     * 判断。“更好”=更低的成本;以相同的成本：更高
     * 覆盖的行数;对于相同数量的已覆盖行：较小
     * 指数。例如，用于将列集自动排序到树集中
     * 插入.
     */
    class WeightedColumn implements Comparable<WeightedColumn> {

        /**
         * 结构矩阵中列的索引
         */
        private int index;

        /**
         * 与列关联的重量或成本
         */
        private float weight;

        /**
         * 列覆盖的结构矩阵的行数
         */
        private int nrOfCoveredRows;

        WeightedColumn(int i, float w, int nocr) {
            this.index = i;
            this.weight = w;
            this.nrOfCoveredRows = nocr;
        }

        /**
         * 比较方法。在加权列对象上调用 thisColumn。
         *
         * @param otherColumn 用于比较调用列的列
         * @return -1, 如果此列优于其他列&lt;br&gt;0，如果此列 = 其他列&lt;br&gt;1，如果此列比其他列差
         */
        public int compareTo(WeightedColumn otherColumn) {
            int result;
            if (this.weight < otherColumn.weight) result = -1;
            else if (this.weight > otherColumn.weight) result = 1;
            else { //权重相同
                result = otherColumn.nrOfCoveredRows - this.nrOfCoveredRows;
                if (result == 0) // 覆盖的元素数量也相同 -&gt;按索引排序以保持相等的一致性
                    result = this.index - otherColumn.index;
            }
            return result;
        }

        int getIndex() {
            return index;
        }

        float getWeight() {
            return weight;
        }

        int getNrOfCoveredRows() {
            return nrOfCoveredRows;
        }
    }

}