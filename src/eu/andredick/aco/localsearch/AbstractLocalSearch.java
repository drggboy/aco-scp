package eu.andredick.aco.localsearch;

import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>局部搜索的抽象组件</b><br>
 * 第 3.3.9 章，第 37 页，局部搜索<br>
 * <br>
 * 局部搜索由类中的蚂蚁执行 {@link eu.andredick.aco.ant.ACOAnt} 使用.<br>
 * 局部搜索基于先前构建的解，并在必要时提供改进的解.
 * <p><img src="{@docRoot}/images/LocalSearch.svg" alt=""></p>
 */
public abstract class AbstractLocalSearch<S extends AbstractSolution> {

    /**
     * 根据原始解启动局部搜索，并返回改进的或相同的解.<br>
     * 类{@link eu.andredick.aco.ant.ACOAnt}蚂蚁组件的接口
     *
     * @param solution 原始解
     * @return  改进的解
     */
    public abstract S search(S solution);

}
