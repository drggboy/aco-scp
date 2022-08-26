package eu.andredick.aco.localsearch;

import eu.andredick.scp.SCPSolution;

/**
 * <b>局部搜索组件的抽象 - 返回初始解决方案</b><br>
 * <br>
 * 在不想执行局部搜索时使用。<br>
 * 局部搜索由类{@link eu.andredick.aco.ant.ACOAnt}中的蚂蚁执行使用。<br>
 * 局部搜索基于先前构建的解决方案，并在必要时提供改进的解决方案。<br>
 * <p><img src="{@docRoot}/images/LocalSearch.svg" alt=""></p>
 */
public class LocalSearchNone extends AbstractLocalSearch<SCPSolution> {


    /**
     * 使用原始解决方案启动局部搜索，立即返回收到的解决方案
     *
     * @param solution 原始解
     * @return 相同的原始解
     */
    public SCPSolution search(SCPSolution solution) {
        return solution;
    }
}
