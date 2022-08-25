package eu.andredick.configuration;

import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.scp.SCProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 抽象类用作具有不同组件的算法的配置工厂的模板.<br>
 * 它包含一个要填充派生类的参数列表.<br>
 * 用于实现实例化并返回已配置算法的 create（） 方法,<br>
 * 以便它可以在此类之外执行.<br>
 */
public abstract class AbstractAlgorithmConfiguration {

    protected String configName;

    protected ArrayList<ConfigurationParameter> configurationParameters;


    /**
     * 算法配置的默认构造函数
     */
    public AbstractAlgorithmConfiguration() {

        configurationParameters = new ArrayList<>();

        ConfigurationParameter<Integer> antS =
                new ConfigurationParameter<>("antsize", 10);
        this.addConfigurationParameter(antS);

        this.prepareConfigParameters();

    }

    /**
     * 类的主要方法.<br>
     * 根据包含参数的配置生成算法实例<br>
     *
     * @param problem SCP 实例
     * @return 算法实例
     */

    public abstract AbstractAlgorithm create(SCProblem problem);

    /**
     * 派生类实现的抽象方法.<br>
     * 这应该生成算法配置的所有必要参数并将它们添加到参数列表中.<br>
     * 实例化配置时在构造函数中调用此方法.<br>
     */
    public abstract void prepareConfigParameters();

    /**
     * 将参数添加到内部参数列表
     *
     * @param parameter 参数
     */
    public void addConfigurationParameter(ConfigurationParameter parameter) {
        // 检查参数是否已经存在
        if (!this.configurationParameters.contains(parameter)) {
            this.configurationParameters.add(parameter);
        } else {
            //替换该参数，因为它已存在
            this.configurationParameters.remove(parameter);
            this.configurationParameters.add(parameter);
        }
    }

    /**
     * 返回对参数的对象引用
     *
     * @param name 参数名称
     * @return 对参数的对象引用
     */
    public ConfigurationParameter getParameter(String name) {
        // 在现有参数中查找参数
        for (ConfigurationParameter parameter : this.configurationParameters) {
            if (parameter.getName().equalsIgnoreCase(name)) return parameter;
        }
        throw new NoSuchElementException();
    }

    /**
     * 提供完整的参数列表
     *
     * @return 参数列表
     */
    public List<ConfigurationParameter> getParameters() {
        return this.configurationParameters;
    }

    public String getConfigName() {
        return this.configName;
    }

    @Override
    public String toString(){
        return this.getConfigName();
    }

}
