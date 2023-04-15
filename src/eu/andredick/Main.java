package eu.andredick;

import eu.andredick.aco.algorithm.ACOAlgorithm;
import eu.andredick.aco.algorithm.AbstractAlgorithm;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.aco.ant.ACOAnt;
import eu.andredick.aco.combination.CombinationSum;
import eu.andredick.aco.construction.ConstructionFromElements;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.localsearch.LocalSearchAntCover;
import eu.andredick.aco.masterprocess.MasterProcessElitist;
import eu.andredick.aco.nextstep.NextStepStrategyOnSubsets;
import eu.andredick.aco.pheromoneevaporation.AbstractPheromoneEvaporation;
import eu.andredick.aco.pheromoneevaporation.PheromoneEvaporation;
import eu.andredick.aco.pheromoneinit.AbstractPheromoneInit;
import eu.andredick.aco.pheromoneinit.PheromoneInit;
import eu.andredick.aco.pheromoneperception.PerceptionSimple;
import eu.andredick.aco.pheromoneupdate.PheromoneUpdateOnSubsets;
import eu.andredick.aco.problem.AbstractSolution;
import eu.andredick.aco.solutionquality.SolutionQualityMin;
import eu.andredick.aco.termination.TerminationCriterion;
import eu.andredick.configuration.*;
import eu.andredick.scp.SCPSolution;
import eu.andredick.tools.ChartTools;
import eu.andredick.tools.MatConvert;

import eu.andredick.aco.masterprocess.MasterProcessBasic;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.orlib.OrlibConverter;

public class Main {

    public static void main(String[] args) {
        // 设置覆盖矩阵
//        String mat_name = "Coverage.mat";  // 指定mat文件名
        String mat_name = "Coverage_4x4.mat";  // 指定mat文件名
        SCProblem problem = MatConvert.Mat_to_SCP(mat_name);
        // 使用自定义策略
        AlgorithmConfiguration_self Alg_config = new AlgorithmConfiguration_self();
        AbstractAlgorithm aco_algorithm = Alg_config.create(problem);

        // ACO算法开始执行
        aco_algorithm.run();
        // 获取统计信息
        Statistics statistics = aco_algorithm.getStatistics();
        float[] min_array = statistics.getIterationMinValuesArray();
        SCPSolution solution = (SCPSolution) statistics.getGlobalMinSolution();
        solution.print();
        //可视化迭代结果
        ChartTools chart = new ChartTools("迭代最优值", "iterations", "value");
        chart.addDataSeries("ants", min_array);
    }
}
