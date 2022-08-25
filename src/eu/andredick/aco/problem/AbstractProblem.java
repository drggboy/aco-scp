package eu.andredick.aco.problem;

/**
 * <b>抽象类问题</b>, 这将由 ACO 元启发式解决<br>
 * <br>
 * 此抽象组件用于 ACO 元启发式的通用级别,<br>
 * 尚未对特定问题类具有依赖关系.<br>
 * 同样，如果 ACO 元启发式的特殊组件不依赖于任何特定问题类，它们也可以引用抽象问题.<br>
 * <br>
 * 要定义问题类，必须派生抽象问题及其相关的抽象解决方案.<br>
 *
 * <p><img src="{@docRoot}/images/Problem.svg" alt=""></p>
 */
public abstract class AbstractProblem {

    /**
     * 返回问题实例的名称
     *
     * @return 问题的名称
     */
    public abstract String getName();
}
