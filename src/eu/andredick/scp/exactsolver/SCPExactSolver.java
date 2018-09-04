package eu.andredick.scp.exactsolver;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.aco.problem.AbstractSolution;

/**
 * Eine Wrapperklasse für die Bibliothek "Google Or Tools",
 * um SCP-Instanzen exakt zu lösen.
 * <p>
 * Die "Google Or Tools" müssen als Paket eingebunden sein.
 * https://developers.google.com/optimization/
 */
public class SCPExactSolver {

    static {
        System.loadLibrary("jniortools");
    }

    private String solverType = null;
    // Wrapper-Klassen von "Google Or Tools"
    private MPSolver solver;
    private MPVariable[] var;
    private MPObjective obj;

    // Standardkonstruktor initiiert den Solver als Solver für Ganzzahlige Programme
    // (möglich sind auch allgemeine LP)
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
     * Methode zum Lösen einer SCP-Instanz
     *
     * @param problem Die SCP-Instanz
     * @return Lösung
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
     * Private Methode, um die den "Google Or Tools"-Solver mit der SCP-Instanz zu initiieren
     *
     * @param problem Die SCP-Instanz
     */
    private void initSolver(SCProblem problem) {

        boolean[][] matrix = problem.getStructure().getRelations();
        float[] objKoef = problem.getObjectiveFunction().getWeights();

        /*Solver Deklaration*/
        solver = createSolver(this.solverType);
        double infinity = MPSolver.infinity();

        /*Variablen*/
        var = solver.makeBoolVarArray(objKoef.length);

        /*Zielfunktion*/
        obj = solver.objective();
        obj.setMinimization();
        for (int i = 0; i < var.length; i++) {
            obj.setCoefficient(var[i], objKoef[i]);
        }

        /*Nebenbedingungen*/
        for (int i = 0; i < matrix.length; i++) {
            MPConstraint constraint = solver.makeConstraint(1, infinity);
            for (int j = 0; j < matrix[i].length; j++) {
                constraint.setCoefficient(var[j], matrix[i][j] ? 1 : 0);
            }
        }

        solver.setTimeLimit(30000);

    }

    /**
     * Verwendung des "Google Or Tools"-Solver zum konvertieren der SCP-Instanzen in das MPS-Format
     *
     * @param problem SCP-AbstractProblem
     * @return das SCP-AbstractProblem im MPS-Format
     */
    public String getMPSformat(SCProblem problem) {
        initSolver(problem);
        String mps = solver.exportModelAsMpsFormat(true, true);
        resetSolver();
        return mps;
    }

    /**
     * Verwendung des "Google Or Tools"-Solver zum konvertieren der SCP-Instanzen in das LP-Format
     *
     * @param problem SCP-AbstractProblem
     * @return das SCP-AbstractProblem im LP-Format
     */
    public String getLPformat(SCProblem problem) {
        initSolver(problem);
        String lp = solver.exportModelAsLpFormat(true);
        resetSolver();
        return lp;
    }

    /**
     * Zurücksetzen des "Google Or Tools"-Solver
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
