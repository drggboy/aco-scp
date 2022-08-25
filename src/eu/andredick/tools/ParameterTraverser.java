package eu.andredick.tools;

import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.configuration.AbstractAlgorithmConfiguration;
import eu.andredick.configuration.ConfigurationParameter;
import eu.andredick.scp.SCProblem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数扫描类
 * 测试 alogrithm 配置的参数组合.
 * 每个参数都有有限的出现次数.
 * 该机制对应于树的递归遍历.
 * 对于每种组合，都会创建并执行配置算法的一个或多个实例.
 * 最佳目标函数值对应最佳参数组合.
 */
public class ParameterTraverser {

    AbstractAlgorithmConfiguration configuration;
    SCProblem problem;
    List<ConfigurationParameter> parameters;


    private Float currentBestValue;
    private Map<String, Number> currentBestParameters;

    public ParameterTraverser(AbstractAlgorithmConfiguration configuration, SCProblem problem) {
        this.configuration = configuration;
        this.problem = problem;

        parameters = configuration.getParameters();
        this.currentBestValue = null;


    }

    public void scan() {
        System.out.println("ParameterTraverser.scan... ");
        if (this.parameters.size() > 0) {
            traverseParameter(0);
        }
    }

    /**
     * 用于遍历参数列表及其特征的递归方法
     * 最终帐户是参数列表的最后一个参数。到达结束节点时,
     * 定义了参数组合。在结束节点中，该算法可以与参数组合一起使用
     * 创建并执行.
     *
     * @param parameterListIndex: 参数列表中参数的索引
     */
    private void traverseParameter(int parameterListIndex) {

        ConfigurationParameter parameter = parameters.get(parameterListIndex);

        int expressionsSize = parameter.getExpressionsSize();

        for (int i = 0; i < expressionsSize; i++) {
            parameter.setCurrentValueOnIndex(i);

            if (parameterListIndex == (parameters.size() - 1)) {
                Statistics[] statistics = MultipleRunner.run(configuration, problem, 10);
                float bestValue = ArrayTools.getMinValue(ArrayTools.getAverageValuesOfArrays(Criterions.getArraysOfIterationBestValues(statistics)));

                if (currentBestValue == null || bestValue < currentBestValue) {
                    currentBestValue = bestValue;
                    this.createParameterMap();
                    this.currentBestParameters.put("z_best_average", this.currentBestValue);
                }
            } else {
                traverseParameter(parameterListIndex + 1);
            }
        }

    }

    private void createParameterMap() {
        currentBestParameters = new HashMap<>();
        for (ConfigurationParameter p : parameters) {
            currentBestParameters.put(p.getName(), p.getCurrentValue());
        }
    }

    public Map<String, Number> getBestParameters() {
        return this.currentBestParameters;
    }

    public float getBestValue() {
        return this.currentBestValue;
    }

}
