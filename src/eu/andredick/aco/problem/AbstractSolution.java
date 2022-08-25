package eu.andredick.aco.problem;

/**
 * <b>抽象类问题的抽象解</b>, 这将由 ACO 元启发式解决<br>
 * <br>
 * 实例意义上的解是唯一分配给问题实例的 ({@link #getProblem()}).<br>
 * 另一方面，一个问题可以与多个解相关联.
 * <br>
 * 在解决方案和问题之间，存在解决方案对问题类的依赖关系.<br>
 * 如果抽象解决方案是派生的，则泛型编程必须确定它用于哪一类问题.<br>
 * 通常，必须为每个问题类实现解决方案的关联类.<br>
 *
 * <p><img src="{@docRoot}/images/Problem.svg" alt=""></p>
 *
 * @param <P> 抽象问题类的相关表达式 - 一类特殊问题
 */
public abstract class AbstractSolution<P extends AbstractProblem> {

    /**
     * 返回问题的唯一关联实例
     *
     * @return 问题的实例
     */
    public abstract P getProblem();

    /**
     * 返回解的目标函数值
     *
     * @return 解决方案的目标函数值
     */
    public abstract float getValue();

    /**
     * 创建相应子类的空解<br>
     * 这种方法的必要性出现了,
     * 因为在 ACO 元启发式定义的通用级别上，不应该依赖于组件特性.
     *
     * @return 空解决方案的实例
     */
    public abstract AbstractSolution createNew();
}
