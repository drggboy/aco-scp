# aco-scp

**This testing framework is an implementation of the [Ant Colony Optimization (ACO)](https://en.wikipedia.org/wiki/Ant_colony_optimization) algorithm for the [Set Cover Problem (SCP)](https://en.wikipedia.org/wiki/Set_cover_problem).**

For the composition and execution of the ACO algorithm, an object-oriented testing framework has been implemented. Its usage (adapting the ACO metaheuristic to specific problem classes) is based on inheritance.

## [Documentation](http://drggboy.github.io/aco-scp)

## Running
The program starts from the Main file and can be executed by providing the coverage matrix of the problem instance.

You can provide the coverage matrix by specifying a .mat file, which should be placed in the `resources/mat/` directory, and you can specify the file name in the configuration using `mat_name`, like this:

```java
String mat_name = "Coverage.mat";
```
You can adjust specific parameter configurations in the `src\eu\andredick\configuration\AlgorithmConfiguration_self.java` parameter configuration file.