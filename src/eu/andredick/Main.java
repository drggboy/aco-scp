package eu.andredick;

import eu.andredick.aco.algorithm.ACOAlgorithm;
import eu.andredick.aco.algorithm.Statistics;
import eu.andredick.aco.ant.ACOAnt;
import eu.andredick.aco.combination.CombinationSum;
import eu.andredick.aco.construction.ConstructionFromElements;
import eu.andredick.aco.heuristic.HeuristicInfoSet;
import eu.andredick.aco.localsearch.LocalSearchAntCover;
import eu.andredick.aco.masterprocess.MasterProcessElitist;
import eu.andredick.aco.nextstep.NextStepStrategyOnSubsets;
import eu.andredick.aco.pheromoneperception.PerceptionSimple;
import eu.andredick.aco.pheromoneupdate.PheromoneUpdateOnSubsets;
import eu.andredick.aco.solutionquality.SolutionQualityMin;
import eu.andredick.aco.termination.TerminationCriterion;
import eu.andredick.scp.SCPSolution;
import eu.andredick.tools.ChartTools;
import eu.andredick.tools.MatConvert;

import eu.andredick.aco.masterprocess.MasterProcessBasic;
import eu.andredick.aco.pheromoneassociation.PheromoneOnSubsets;
import eu.andredick.scp.SCProblem;
import eu.andredick.tools.orlib.OrlibConverter;

public class Main {

    public static void main(String[] args) {
        // 通过 .mat 文件设置覆盖矩阵设置覆盖矩阵
        SCProblem problem = MatConvert.Mat_to_SCP("Coverage.mat");
        // 使用orlib库中的数据集初始化特定的覆盖问题，涉及覆盖矩阵
//        SCProblem problem = new OrlibConverter().getProblem("scp41");
        // 设置信息素关联组件
        PheromoneOnSubsets pheromone_association = new PheromoneOnSubsets(problem);
        // 判定解的质量，用于信息素的标记
        SolutionQualityMin solution_quality = new SolutionQualityMin();
        // 设置信息素标记组件
        PheromoneUpdateOnSubsets pheromoneupdate = new PheromoneUpdateOnSubsets(pheromone_association, solution_quality);
        // 设置信息素感知组件
        PerceptionSimple pheromone_perception = new PerceptionSimple();
        // 启发式信息设置
        HeuristicInfoSet heuristics = new HeuristicInfoSet();
        // 信息素与启发式信息结合组件
        CombinationSum combination_rule = new CombinationSum((float)0.5);
        // 设置候选集选择组件
        NextStepStrategyOnSubsets nextstep_strategy = new NextStepStrategyOnSubsets(pheromone_association, pheromone_perception, heuristics, combination_rule, (float)0.5);
        // 设置解的构造组件
        ConstructionFromElements construction = new ConstructionFromElements(nextstep_strategy);
        //设置局部搜索组件
        LocalSearchAntCover local_search = new LocalSearchAntCover();
        // 设置蚂蚁种群数量
        ACOAnt[] ants = new ACOAnt[500];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new ACOAnt<SCPSolution, SCProblem>(problem, pheromoneupdate, construction, local_search);
        }
        // 设置最大迭代次数
        TerminationCriterion termination_criterion = new TerminationCriterion(100);
        //主进程由 ACOAlgorithm 调用执行
        MasterProcessElitist master_process = new MasterProcessElitist(pheromone_association, ants, termination_criterion);
//        MasterProcessBasic master_process = new MasterProcessBasic(pheromone_association, ants, termination_criterion);
        ACOAlgorithm aco_algorithm = new ACOAlgorithm(master_process);
        aco_algorithm.go();
        // 获取统计信息
        Statistics statistics = aco_algorithm.getStatistics();
        float[] min_array = statistics.getIterationMinValuesArray();
        //可视化迭代结果
        ChartTools chart = new ChartTools("迭代最优值", "iterations", "value");
        chart.addDataSeries("ants", min_array);
    }
}
