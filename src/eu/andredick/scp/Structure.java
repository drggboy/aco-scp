package eu.andredick.scp;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类表示集合覆盖问题的结构矩阵 A=(a_ij).
 */
public class Structure {

    /**
     * 作为二维布尔字段的结构矩阵
     */
    //行数为元素数目，列数为子集数
    private boolean[][] relations;

    /**
     * 其余行和列列表:
     * 对于每个子集，所包含的元素列表.
     */
    private List<Integer>[] elementsInSubset;

    /**
     * 其余行和列列表:
     * 对于每个基本元素，覆盖它的子集列表
     */
    private List<Integer>[] subsetsWithElement;


    /**
     * 构造函数
     *
     * @param relations 作为二维布尔字段的结构矩阵
     */
    public Structure(boolean[][] relations) {
        this.relations = relations;
        this.prepare();
    }

    /**
     * 准备其余列表
     */
    private void prepare() {
        System.out.println("Structure prepare ...");
        this.subsetsWithElement = new ArrayList[this.relations.length];
        this.elementsInSubset = new ArrayList[this.relations[0].length];
        System.out.println("Structure prepare ... extractSubsetsWithElement");
        for (int i = 0; i < this.relations.length; i++) {
            this.subsetsWithElement[i] = this.extractSubsetsWithElement(i);
        }
        System.out.println("Structure prepare ... extractElementsInSubset");
        for (int j = 0; j < this.relations[0].length; j++) {
            this.elementsInSubset[j] = this.extractElementsInSubset(j);
        }
        System.out.println("Structure prepare ... end!");
    }


    /**
     * 为覆盖基元 i 的子集创建列表
     *
     * @param i 基本元素 i
     * @return 涵盖基本元素 i 的子集列表
     */
    private List<Integer> extractSubsetsWithElement(int i) {
        List<Integer> cols = new ArrayList<>();
        for (int j = 0; j < this.subsetsSize(); j++) {
            if (relations[i][j]) cols.add(j);
        }
        return cols;
    }


    /**
     * 创建子集 j 中包含的基元列表
     *
     * @param j 覆盖原语 i 的子集列表
     * @return 子集 j 中包含的基元列表
     */
    private List<Integer> extractElementsInSubset(int j) {
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < this.elementsSize(); i++) {
            if (relations[i][j]) {
                rows.add(i);
            }
        }
        return rows;
    }

    /**
     * 返回结构矩阵的行数或基元的数目
     *
     * @return 结构矩阵的行数
     */
    public int elementsSize() {
        return relations.length;
    }

    /**
     * 返回结构矩阵或子集中的列数
     *
     * @return 结构矩阵或子集的列数
     */
    public int subsetsSize() {
        return this.relations[0].length;
    }

    /**
     * 返回结构矩阵的密度
     *
     * @return 结构矩阵
     */
    public float getDensity() {
        int matrixElements = elementsSize() * subsetsSize();
        int entries = 0;
        for (List<Integer> subsetsWith_i : subsetsWithElement) {
            entries += subsetsWith_i.size();
        }
        return (float) entries / (float) matrixElements * 100;
    }

    /**
     * 返回结构矩阵的元素 a_ij 或子集 j 是否包含基本元素 i
     *
     * @param i 基本元素 i
     * @param j 子集 j
     * @return 如果子集 j 包含原始 i，则为真
     */
    public boolean getRelation(int i, int j) {
        return this.relations[i][j];
    }


    /**
     * 结构矩阵
     *
     * @return 作为二维布尔字段的结构矩阵
     */
    public boolean[][] getRelations() {
        return this.relations;
    }


    /**
     * 提供涵盖基本元素 i 的子集列表
     *
     * @param i 的基本要素
     * @return 涵盖基本元素 i 的子集列表
     */
    public List<Integer> getSubsetsWithElement(int i) {
        return subsetsWithElement[i];
    }


    /**
     * 返回子集 j 中包含的元素列表
     *
     * @param j 子集 j
     * @return 子集 j 中包含的 elment 列表
     */
    public List<Integer> getElementsInSubset(int j) {
        return elementsInSubset[j];
    }

    /**
     * 打印
     */
    public void print() {
        for (boolean[] relation : relations) {
            for (boolean aRelation : relation) {
                String s = (aRelation) ? "1" : " ";
                String value = "" + s;
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

}
