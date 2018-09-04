package eu.andredick.scp;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse repräsentiert die Strukturmatrix A=(a_ij) eines Set Covering Problems.
 */
public class Structure {

    /**
     * Strukturmatrix als boolsches 2D-Feld
     */
    private boolean[][] relations;

    /**
     * Redundant mitgeführte Zeilen- und Spalten- Listen:
     * Zu jeder Teilmenge eine Liste von Grundelementen, die in ihr enthalten sind.
     */
    private List<Integer>[] elementsInSubset;

    /**
     * Redundant mitgeführte Zeilen- und Spalten- Listen:
     * Zu jedem Grundelement eine Liste von Teilmengen, die es überdecken
     */
    private List<Integer>[] subsetsWithElement;


    /**
     * Konstruktor
     *
     * @param relations Strukturmatrix als boolsches 2D-Feld
     */
    public Structure(boolean[][] relations) {
        this.relations = relations;
        this.prepare();
    }

    /**
     * Vorbereitung der redundanten Listen
     */
    private void prepare() {
        System.out.println("Structure prepare ...");
        this.subsetsWithElement = new ArrayList[this.relations.length];
        this.elementsInSubset = new ArrayList[this.relations[0].length];
        System.out.println("Structure prepare ... extractSubsetsWithElement");
        for (int i = 0; i < this.relations.length; i++) {
            this.subsetsWithElement[i] = this.extractSubsetsWithElement(i);
        }
        System.out.println("Structure prepare ... extractElementsInSubset");
        for (int j = 0; j < this.relations[0].length; j++) {
            this.elementsInSubset[j] = this.extractElementsInSubset(j);
        }
        System.out.println("Structure prepare ... end!");
    }


    /**
     * Erzeugen einer Liste für Teilmengen, die das Grundelement i überdecken
     *
     * @param i das Grundelement i
     * @return Liste für Teilmengen, die das Grundelement i überdecken
     */
    private List<Integer> extractSubsetsWithElement(int i) {
        List<Integer> cols = new ArrayList<>();
        for (int j = 0; j < this.subsetsSize(); j++) {
            if (relations[i][j]) cols.add(j);
        }
        return cols;
    }


    /**
     * Erzeugen einer Liste von Grundelementen, die in der Teilmenge j enthalten sind
     *
     * @param j Liste für Teilmengen, die das Grundelement i überdecken
     * @return Liste von Grundelementen, die in der Teilmenge j enthalten sind
     */
    private List<Integer> extractElementsInSubset(int j) {
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < this.elementsSize(); i++) {
            if (relations[i][j]) {
                rows.add(i);
            }
        }
        return rows;
    }

    /**
     * Liefert die Anzahl der Zeilen der Strukturmatrix bzw. der Grundelemente
     *
     * @return Anzahl der Zeilen der Strukturmatrix
     */
    public int elementsSize() {
        return relations.length;
    }

    /**
     * Liefert die Anzahl der Spalten der Strukturmatrix bzw. der Teilmengen
     *
     * @return Anzahl der Spalten der Strukturmatrix bzw. der Teilmengen
     */
    public int subsetsSize() {
        return this.relations[0].length;
    }

    /**
     * Liefert die Dichte der Strukturmatrix
     *
     * @return Dichte der Strukturmatrix
     */
    public float getDensity() {
        int matrixElements = elementsSize() * subsetsSize();
        int entries = 0;
        for (List<Integer> subsetsWith_i : subsetsWithElement) {
            entries += subsetsWith_i.size();
        }
        return (float) entries / (float) matrixElements * 100;
    }

    /**
     * Liefert das Element a_ij der Strukturmatrix bzw. ob die Teilmenge j das Grundelement i enthält
     *
     * @param i Grundelement i
     * @param j Teilmenge j
     * @return Wahr, wenn die Teilmenge j das Grundelement i enthält
     */
    public boolean getRelation(int i, int j) {
        return this.relations[i][j];
    }


    /**
     * Liefert die Strukturmatrix
     *
     * @return Strukturmatrix als boolsches 2D-Feld
     */
    public boolean[][] getRelations() {
        return this.relations;
    }


    /**
     * Liefert Liste der Teilmengen, die das Grundelement i überdecken
     *
     * @param i Grundelement i
     * @return Liste der Teilmengen, die das Grundelement i überdecken
     */
    public List<Integer> getSubsetsWithElement(int i) {
        return subsetsWithElement[i];
    }


    /**
     * Liefert Liste der Elmente, die in der Teilmenge j enthalten sind
     *
     * @param j Teilmenge j
     * @return Liste der Elmente, die in der Teilmenge j enthalten sind
     */
    public List<Integer> getElementsInSubset(int j) {
        return elementsInSubset[j];
    }

    /**
     * Drucken
     */
    public void print() {
        for (boolean[] relation : relations) {
            for (boolean aRelation : relation) {
                String s = (aRelation) ? "1" : " ";
                String value = "" + s;
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }


}
