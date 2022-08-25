package eu.andredick.tools;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * Google Or Tools 库的包装类,
 * 精确解决 SCP 实例.
 * <p>
 * “Google Or Tools”必须集成为一个软件包.
 * https://developers.google.com/optimization/
 */
public class SCPExactSolver {

    static {
        System.loadLibrary("jniortools");
    }

    private String solverType = null;
    // "Google Or Tools"的包装类
    private MPSolver solver;
    private MPVariable[] var;
    private MPObjective obj;

    // 默认构造函数将求解器初始化为整数程序求解器
    // (一般LP也是可以的)
    public SCPExactSolver() {
        this.solverType = MPSolver.OptimizationProblemType.BOP_INTEGER_PROGRAMMING.name();
    }

    public SCPExactSolver(String solverType) {
        this.solverType = solverType;
    }

    private static MPSolver createSolver(String solverType) {
        return new MPSolver("SCPExactSolver",
                MPSolver.OptimizationProblemType.valueOf(solverType));
    }

    /**
     * 解决SCP实例的方法
     *
     * @param problem SCP实例
     * @return 溶液
     */
    public AbstractSolution solve(SCProblem problem) {

        SCPSolution solution = new SCPSolution(problem);

        this.initSolver(problem);

        /*Solver starten*/

        final MPSolver.ResultStatus resultStatus = solver.solve();

        // Check that the problem has an optimal solution.
        if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
            System.err.println("The problem does not have an optimal solution! ResultStatus: " + resultStatus.name());
        }

        for (int i = 0; i < var.length; i++) {
            if ((int) var[i].solutionValue() == 1)
                solution.addSubset(i);
        }

        this.print();

        this.resetSolver();

        return solution;
    }

    /**
     * 使用 SCP 实例启动“Google or tools”求解器的私有方法
     *
     * @param problem SCP实例
     */
    private void initSolver(SCProblem problem) {

        boolean[][] matrix = problem.getStructure().getRelations();
        float[] objKoef = problem.getObjectiveFunction().getWeights();

        /*规划求解声明*/
        solver = createSolver(this.solverType);
        double infinity = MPSolver.infinity();

        /*变量*/
        var = solver.makeBoolVarArray(objKoef.length);

        /*目标*/
        obj = solver.objective();
        obj.setMinimization();
        for (int i = 0; i < var.length; i++) {
            obj.setCoefficient(var[i], objKoef[i]);
        }

        /*辅助条件*/
        for (int i = 0; i < matrix.length; i++) {
            MPConstraint constraint = solver.makeConstraint(1, infinity);
            for (int j = 0; j < matrix[i].length; j++) {
                constraint.setCoefficient(var[j], matrix[i][j] ? 1 : 0);
            }
        }

        solver.setTimeLimit(30000);

    }

    /**
     * 使用“Google Or Tools”求解器将 SCP 实例转换为 MPS 格式
     *
     * @param problem SCP-抽象问题
     * @return MPS 格式的 SCP 抽象问题
     */
    public String getMPSformat(SCProblem problem) {
        initSolver(problem);
        String mps = solver.exportModelAsMpsFormat(true, true);
        resetSolver();
        return mps;
    }

    /**
     * 使用“Google Or Tools”求解器将 SCP 实例转换为 LP 格式
     *
     * @param problem SCP-抽象问题
     * @return LP格式中的SCP抽象问题
     */
    public String getLPformat(SCProblem problem) {
        initSolver(problem);
        String lp = solver.exportModelAsLpFormat(true);
        resetSolver();
        return lp;
    }

    /**
     * 重置谷歌或工具求解器
     */
    public void resetSolver() {
        solver.reset();
        solver.clear();
        var = null;
        obj.delete();
    }

    public void print() {
        System.out.println("Number of variables = " + solver.numVariables());
        System.out.println("Number of constraints = " + solver.numConstraints());
        System.out.println("Optimal objective value = " + solver.objective().value());
    }

}
