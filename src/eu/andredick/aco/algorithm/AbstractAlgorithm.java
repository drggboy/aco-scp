package eu.andredick.aco.algorithm;

/**
 * <b>其实例可以并行执行的算法模板。</b><br>
 * 真正的算法派生自这个抽象类 <br>
 * 在方法 {@link #go()} 中开始实现.
 * <p><img src="{@docRoot}/images/ACOAlgorithm.svg" alt=""></p>
 */
public abstract class AbstractAlgorithm implements Runnable {

    /**
     * 用于记录算法运行时的开始时间
     */
    protected Long timeStart;

    /**
     * 用于记录算法运行时的结束时间
     */
    protected Long timeEnd;

    /**
     * 启动算法的方法<br>
     * 首先，准备操作由 {@link #preprocessing()} 执行。.<br>
     * 然后启动要实现的 {@link #go()} 方法。<br>
     * 最后，{@link #postprocessing()}执行后续操作.
     */
    @Override
    public void run() {
        this.preprocessing();
        this.go();
        this.postprocessing();
    }

    /**
     * 算法的一般操作准备.<br>
     * 操作不依赖于特定算法.<br>
     * 在此版本中，初始化算法的开始时间.
     */
    protected void preprocessing() {
        System.out.println("preprocessing... ");
        this.timeStart = System.currentTimeMillis();
        System.out.println("computing... ");
    }

    /**
     * 用于启动算法的抽象方法.<br>
     * 在派生抽象类时，必须实现该方法.<br>
     * 它不是从外部启动的（受保护的），而是在方法“run（）”中调用.
     */
    protected abstract void go();

    /**
     * 算法的后续一般操作.<br>
     * 操作不依赖于特定算法.<br>
     * 在此版本中，初始化算法的结束时间，并 <br>
     * 将持续时间输出到控制台.
     */
    protected void postprocessing() {
        System.out.println("postprocessing... ");
        this.timeEnd = System.currentTimeMillis();
        System.out.println("历史循环时间: " + (timeEnd - timeStart) / 1000 + " 秒.");
    }

    /**
     * 提供有关算法流的统计信息
     *
     * @return 算法过程的统计
     */
    public abstract Statistics getStatistics();
}
