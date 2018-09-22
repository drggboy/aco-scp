package eu.andredick.scp;

import eu.andredick.aco.problem.AbstractProblem;

/**
 * Die Klasse repräsentiert das <b>Set Covering Problem (SCP)</b>.<br>
 * Im Kontext der ACO-Metaheuristik ist das SCP eine Ausprägung des Abstrakten Problems ({@link AbstractProblem}).<br>
 * Kapitel 2.3, S. 17, Set Covering Problem (SCP). <br>
 * <br>
 * <b>Definition:</b> Gegeben sei eine Sammlung von Teilmengen einer Menge von Grundelementen.
 * Eine Mengenüberdeckung (Set Cover) ist eine Auswahl aus dieser Sammlung,
 * deren Vereinigung die Menge aller Grundelemente ist. Als Optimierungsproblem ist die
 * kleinste Auswahl gesucht und im Falle, dass die Teilmengen gewichtet sind, das geringste
 * Gesamtgewicht der Auswahl.<br>
 * <br>
 * Das SCP setzt sich aus der Strukturmatrix ({@link Structure}) und der Zielfunktion ({@link ObjectiveFunction}) zusammen.<br>
 * Eine Lösung des SCP ist explizit definiert (siehe {@link SCPSolution}) und beinhaltet als Instanz die Festlegung der Entscheidungsvariablen des SCP.<br>
 * Info: Über die Entscheidungsvariablen X_j={0,1} wird festgelegt, ob die Teilmenge j in der Lösung enthalten sein soll.<br>
 *
 * <p><img src="{@docRoot}/images/SCP.svg" alt=""></p>
 */
public class SCProblem extends AbstractProblem {

    /**
     * Name des konkreten Problems (der Probleminstanz)
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
     * Liefert den Namen der Problem-Instanz
     *
     * @return Name des Problems
     */
    public String getName() {
        return this.name;
    }

    /**
     * Liefert die Strukturmatrix der Problem-Instanz
     *
     * @return Strukturmatrix
     */
    public Structure getStructure() {
        return this.structure;
    }

    /**
     * Liefert die Zielfunktion der Problem-Instanz
     *
     * @return Zielfunktion
     */
    public ObjectiveFunction getObjectiveFunction() {
        return this.objectiveFunction;
    }


    /**
     * Prüft auf Konsistenz der Instanz des SCP-Problems
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
