# aco-scp
Forked from https://github.com/andre-dick/aco-scp

<b>此测试框架适配了 <a href="https://de.wikipedia.org/wiki/Ameisenalgorithmus">蚁群算法（ACO）</a> 到 <a href="https://de.wikipedia.org/wiki/Mengen%C3%BCberdeckungsproblem">集合覆盖问题（SCP）</a>的实现。</b>
<p>对于ACO算法的组成和执行，实现了一个面向对象的测试框架。它的使用（ACO元启发式对某些问题类的适应）是基于继承的。</p>

## 运行
程序从Main文件开始，传入问题实例的覆盖矩阵，即可运行。

可以通过指定 .mat 文件传入覆盖矩阵，文件需放置在路径 `resources/mat/`下
~~~java
SCProblem problem = MatConvert.Mat_to_SCP("Coverage.mat");
~~~
