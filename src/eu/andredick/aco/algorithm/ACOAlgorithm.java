package eu.andredick.aco.algorithm;

import eu.andredick.aco.masterprocess.AbstractMasterProcess;

/**
 * <b>该类表示 ACO 算法流，实现了模板 {@link AbstractAlgorithm}。</b><br>
 * <br>
 * 每个 ACO 算法都有一个主进程 ({@link AbstractMasterProcess})。<br>
 * 一旦算法启动，主进程就会启动。<br>
 * ACO 算法从父类继承并行执行的能力。<br>
 * <p><img src="{@docRoot}/images/ACOAlgorithm.svg" alt=""></p>
 */
public class ACOAlgorithm extends AbstractAlgorithm {

    /**
     * 组件主进程 (参见 {@link AbstractMasterProcess})
     */
    private AbstractMasterProcess masterProcess;

    /**
     * 构造函数
     *
     * @param masterProcess 主进程组件
     */
    public ACOAlgorithm(AbstractMasterProcess masterProcess) {
        this.masterProcess = masterProcess;
    }

    /**
     * 启动 ACO 算法流的方法。<br>
     * ACO 算法是使用主进程的以下方法 {@link AbstractMasterProcess#start()}
     */
    @Override
    public void go() {
        this.masterProcess.start();
    }

    /**
     * 提供有关算法的统计信息。<br>
     * 为此，从主进程中获取统计信息对象, 管理位置 (参见 {@link AbstractMasterProcess#getStatistics()})。<br>
     * @return 算法过程的统计
     */
    @Override
    public Statistics getStatistics() {
        return this.masterProcess.getStatistics();
    }

}
