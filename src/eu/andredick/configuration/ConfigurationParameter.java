package eu.andredick.configuration;

/**
 * Mittels dieser Klasse können Parameter des Algorithmus definiert werden,
 * damit der Wert der Parameter automatisiert variiert werden kann.
 * Die Parameter werden in den Subklassen von AbstractAlgorithmConfiguration verwendet.
 *
 * @param <N>: Integer, Float, Double ...
 */
public class ConfigurationParameter<N extends Number> {

    // Name des Parameters. Über den Namen wird der Parameter gefunden.
    private String name;

    // Standard-Wert
    private N defaultValue;

    // Aktueller Wert
    private N currentValue;

    // Wertausprägungen, die automatisiert getestet werden können
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
     * Setzt den aktuellen Wert auf Ausprägung mit dem Index i
     *
     * @param i: Index des Array expressiouns
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
     * Die Identität des Parameters ist gegeben, wenn der Name übereinstimmt
     *
     * @param o: zu vergleichender Parameter
     * @return Prüfergebnis auf Gleichheit
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
