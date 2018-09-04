package eu.andredick.scp;

import eu.andredick.aco.problem.AbstractProblem;

/*
 * Die Klasse repräsentiert das Set Covering AbstractProblem.
 */
public class SCProblem extends AbstractProblem {

    /**
     * Name des konkreten Problems
     */
    protected String name;

    /**
     * Strukturmatrix [a_ij]
     */
    private Structure structure;

    /**
     * Zielfunktion
     */
    private ObjectiveFunction objectiveFunction;

    /**
     * Konstruktor
     *
     * @param relations                     Boolsche Matrix m x n
     * @param objectiveFunctionCoefficients Koeffizienten der Zielfunktion c_j
     */
    public SCProblem(boolean[][] relations, float[] objectiveFunctionCoefficients) {
        this.structure = new Structure(relations);
        this.objectiveFunction = new ObjectiveFunction(objectiveFunctionCoefficients);
        this.name = null;
    }

    /**
     * Konstruktor
     *
     * @param relations                     Boolsche Matrix m x n
     * @param objectiveFunctionCoefficients Koeffizienten der Zielfunktion c_j
     * @param name                          Name des Problems
     */
    public SCProblem(boolean[][] relations, float[] objectiveFunctionCoefficients, String name) {
        this.structure = new Structure(relations);
        this.objectiveFunction = new ObjectiveFunction(objectiveFunctionCoefficients);
        this.name = name;
    }

    /**
     * Liefert den Namen des Problems
     *
     * @return Name des Problems
     */
    public String getName() {
        return this.name;
    }

    /**
     * Liefert die Strukturmatrix
     *
     * @return Strukturmatrix
     */
    public Structure getStructure() {
        return this.structure;
    }

    /**
     * Liefert die Zielfunktion
     *
     * @return Zielfunktion
     */
    public ObjectiveFunction getObjectiveFunction() {
        return this.objectiveFunction;
    }


    /**
     * Prüft auf Konsistenz des SCP-Problems
     *
     * @return Wahr, wenn konsistent
     */
    public boolean isConsistent() {
        System.out.println("SCProblem isConsistent ...");
        boolean[][] relations = this.getStructure().getRelations();
        boolean success = true;
        int sumCovers_i = 0;
        for (int i = 0; i < relations.length; i++) {
            int sum_i = 0;
            for (int j = 0; j < relations[i].length; j++) {
                if (relations[i][j]) sum_i++;
            }
            if (sum_i == 0) {
                System.out.println(" Zeile " + i + " von keiner Spalte überdeckt");
                success = false;
            }
            if (this.structure.getSubsetsWithElement(i).size() != sum_i) {
                System.out.println(" Zeile " + i + " nicht konsistent");
                success = false;
            }
            sumCovers_i += sum_i;
        }
        System.out.println(" Mindestens 1fache Überdeckung in Matrix - OK!");
        int sumCovers = 0;
        for (int i = 0; i < structure.elementsSize(); i++) {
            int coveredTimes = structure.getSubsetsWithElement(i).size();
            sumCovers += coveredTimes;
            if (coveredTimes < 1) System.out.println(" Element " + i + " nicht überdeckt");
        }
        float a = sumCovers / structure.elementsSize();
        float b = sumCovers_i / relations.length;
        System.out.println(" Durchschnittliche Überdeckung: " + a + " " + b);

        System.out.println("SCProblem isConsistent ..." + success);
        return success;
    }
}
