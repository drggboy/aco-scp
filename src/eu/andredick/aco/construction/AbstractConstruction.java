package eu.andredick.aco.construction;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.aco.problem.AbstractProblem;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * <b>抽象构造启发式.</b><br>
 * 第 3.3.4 章，第 30 页，构造启发式<br>
 * <br>
 * 此组件的特征由蚂蚁类 {@link eu.andredick.aco.ant.ACOAnt} 确定，用于构建新的解。<br>
 * 设计启发式和解的选择在逻辑上是相互依赖的。<br>
 * 通常不是每个任意变体的候选项都可以与某种启发式构造一起使用。<br>
 * 因此，在派生此抽象类时，必须确定泛型候选集选择.<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 *
 * @param <E> 设计中使用的候选集选择策略.
 */
public abstract class AbstractConstruction<E extends AbstractNextStepStrategy, P extends AbstractProblem, S extends AbstractSolution> {

    /**
     * 选择在设计中使用的备选方案的规则.
     */
    protected E nextStepRule;

    /**
     * 构造函数
     *
     * @param nextStepRule 选择在设计中使用的备选方案的规则
     */
    public AbstractConstruction(E nextStepRule) {
        this.nextStepRule = nextStepRule;
    }

    /**
     * 该方法启动设计过程，并在运行后为提交的问题提供完整的解决方案.<br>
     * 该方法在 ACOAnt 类中调用.
     *
     * @param problem   由蚂蚁构建解决方案的抽象问题.
     * @return          完整设计的解决方案
     */
    public abstract S construct(P problem);
}
