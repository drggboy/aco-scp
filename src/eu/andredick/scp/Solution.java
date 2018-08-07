package eu.andredick.scp;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse repräsentiert die Lösung eines Set Covering Problems (SCP).<br>
 * Die Lösung besteht aus einem Vektor von Entscheidungsvariablen
 * bzw. einer Auswahl von Teilmengen. Beide Repräsentationen werden synchron vorgehalten.
 */
public class Solution {
    /**
     * Das SCP-Problem, zu dem diese Lösung gehört
     */
    private SCProblem problem;

    /**
     * Entscheidungsvariablen
     */
    private boolean[] vars;

    /**
     * Auswahl der Teilmengen
     */
    private List<Integer> subsetsList;

    /**
     * Anzahl der Teilmengen die ein bestimmtes Grundelement überdecken
     */
    private Integer[] coveredElementsCount;

    /**
     * Temporärer Lösungswert
     */
    private Float value;


    /**
     * Konstruktor
     *
     * @param problem Das zugehörige SCP-Problem
     */
    public Solution(SCProblem problem) {
        this.problem = problem;
        this.vars = new boolean[problem.getStructure().subsetsSize()];
        this.subsetsList = new ArrayList<>();
        this.coveredElementsCount = new Integer[problem.getStructure().elementsSize()];
        this.initZeros();
        this.value = null;
    }


    /**
     * Liefert zugehöriges SCP-Problem
     *
     * @return zugehöriges SCP-Problem
     */
    public SCProblem getProblem() {
        return this.problem;
    }

    /**
     * Liefert den Zielfunktionswert der Lösung
     *
     * @return Zielfunktionswert der Lösung
     */
    public float getValue() {
        if (this.value == null) {
            this.value = this.problem.getObjectiveFunction().getValue(this);
        }
        return this.value;
    }


    /**
     * Ist das Grundelement mindestens durch eine der Teilmengen der Lösung überdeckt?
     *
     * @param element das Grundelement
     * @return Wahr, wenn das Grundelement mindestens durch eine der Teilmengen der Lösung überdeckt
     */
    public boolean isElementCovered(Integer element) {
        return this.coveredElementsCount[element] != null && this.coveredElementsCount[element] > 0;
    }


    /**
     * Hinzufügen einer Teilmenge zur Lösung
     *
     * @param subset Teilmenge, die hinzugefügt werden soll
     * @return Wahr, wenn noch nicht in Lösung vorhanden gewesen
     */
    public boolean addSubset(int subset) {
        if (!vars[subset]) {
            vars[subset] = true;
            subsetsList.add(subset);
            this.coverElements(subset);
            this.value = null;
            return true;
        }
        return false;
    }


    /**
     * Entfernen einer Teilmenge aus der Lösung
     *
     * @param subset Teilmenge, die entfernt werden soll
     * @return Wahr, wenn in Lösung vorhanden gewesen
     */
    public boolean removeSubset(int subset) {
        if (vars[subset]) {
            vars[subset] = false;
            subsetsList.remove((Integer) subset);
            this.decoverElements(subset);
            this.value = null;
            return true;
        }
        return false;
    }


    /**
     * Anpassen der Zähler der Überdeckungen von Grundelementen druch Hinzufügen einer Teilmenge
     *
     * @param subset hinzugefügte Teilmenge
     */
    private void coverElements(int subset) {
        List<Integer> elementsInSubset = this.problem.getStructure().getElementsInSubset(subset);
        for (Integer e : elementsInSubset) {
            if (this.coveredElementsCount[e] == null) {
                this.coveredElementsCount[e] = 1;
            } else this.coveredElementsCount[e]++;
        }
    }


    /**
     * Anpassen der Zähler der Überdeckungen von Grundelementen durch Entfernen einer Teilmenge
     *
     * @param subset entfernte Teilmenge
     */
    private void decoverElements(int subset) {
        List<Integer> elementsInSubset = this.problem.getStructure().getElementsInSubset(subset);
        for (Integer e : elementsInSubset) {
            this.coveredElementsCount[e]--;
        }
    }

    /**
     * Liefert die Entscheidungsvariablen
     *
     * @return Boolsches Array mit Entscheidungsvariablen
     */
    public boolean[] getVars() {
        return this.vars;
    }

    /**
     * Liefert die Auswahl der Teilmengen
     *
     * @return Auswahl der Teilmengen
     */
    public List<Integer> getSubsets() {
        return this.subsetsList;
    }

    /**
     * Ist eine Teilmenge in der Lösung enthalten?
     *
     * @param index Teilmenge
     * @return Wahr, wenn Teilmenge in der Lösung enthalten
     */
    public boolean hasSubset(int index) {
        return this.vars[index];
    }

    /**
     * Ist die Lösung zulässig?
     *
     * @return Ergebnis der Bewertung
     */
    public boolean isPermissible() {
        for (int i = 0; i < this.problem.getStructure().elementsSize(); i++) {
            int sum = 0;
            for (int j = 0; j < vars.length; j++) {
                sum += (vars[j] & this.problem.getStructure().getRelation(i, j)) ? 1 : 0;
            }
            if (sum == 0) return false;
        }
        return true;
    }


    /**
     * Initiierung der Entscheidungsvariablen mit 0 nach Erzeugen einer leeren Lösung
     */
    private void initZeros() {
        for (int j = 0; j < this.vars.length; j++) {
            vars[j] = false;
        }
        for (int i = 0; i < this.vars.length; i++) {
            vars[i] = false;
        }
    }

    /**
     * Drucken der Lösung
     */
    public void print() {
        System.out.println("Solution:");
        for (int j = 0; j < this.vars.length; j++) {
            String value = String.valueOf(this.vars[j]);
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
