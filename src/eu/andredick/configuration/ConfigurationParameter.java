package eu.andredick.configuration;

/**
 * 使用这个类，可以定义算法的参数。
 * 以便参数的值可以自动变化。
 * 参数用于配置抽象算法的子类。
 *
 * @param <N>: Integer, Float, Double ...
 */
public class ConfigurationParameter<N extends Number> {

    // 参数的名称。参数由名称找到.
    private String name;

    // 默认值
    private N defaultValue;

    // 当前值
    private N currentValue;

    // 可自动测试的值
    private N[] expressions;

    public ConfigurationParameter(String name, N defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public N getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(N defaultValue) {
        this.defaultValue = defaultValue;
    }

    public N getCurrentValue() {
        return (currentValue == null) ? defaultValue : currentValue;
    }

    public void setCurrentValue(N currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 将当前值设置为具有索引 i 的表达式
     *
     * @param i: 表达式数组的索引
     */
    public void setCurrentValueOnIndex(int i) {
        this.currentValue = (this.expressions == null) ? this.defaultValue : this.expressions[i];
    }

    public int getExpressionsSize() {
        return (expressions == null) ? 1 : expressions.length;
    }

    public void setExpressions(N[] expressions) {
        this.expressions = expressions;
    }


    /**
     * 如果名称匹配，则给出参数的标识
     *
     * @param o: 要比较的参数
     * @return 相等测试结果
     */
    public boolean equals(Object o) {
        if (o instanceof ConfigurationParameter) {
            ConfigurationParameter toCompare = (ConfigurationParameter) o;
            return this.name.equalsIgnoreCase(toCompare.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
