package eu.andredick.aco.localsearch;

import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Kapitel 3.2.9	Lokale Suche
 * Implementiert die lokale Suche mit Ant-Cover-Suchstrategie.
 */
public class LocalSearchStrategyAntCover extends AbstractLocalSearchStrategy {

    /**
     * Statische Klassenvariable, die alle Spalten der Strukturmatrix nach
     * absteigender Güte sortiert bereit hält. Wird beim allerersten Aufruf
     * der lokalen Suche initialisiert, und steht danach unverändert für alle
     * weiteren Aufrufe zur Verfügung.
     */
    private static TreeSet<WeightedColumn> sortedColumns;

    /**
     * Statische Klassenvariable, die für jede Zeile i die "beste" sie
     * überdeckende Spalte j = bestCoveringColumns[i] bereit hält.
     * Wird beim allerersten Aufruf der lokalen Suche initialisiert, und steht
     * danach unverändert für alle weiteren Aufrufe zur Verfügung.
     */
    private static WeightedColumn[] bestCoveringColumns;

    /**
     * Statische Klassenvariable, die den Initialisierungsstatus der
     * statischen Variablen sortedColumns und bestCoveringColumns wiedergibt.
     */
    private static boolean staticFieldsReady = false;

    /**
     * Suchmethode für die lokale Suche nach Ant-Cover-Strategie. Stellt die
     * gesamte Funktionalität der Klasse bereit. Bekommt eine von Ameisen
     * konstruierte, zulässige Ausgangslösung übergeben, und versucht diese
     * spaltenweise zu verbessern. Dafür werden die Spalten der übergebenen Lösung
     * sukzessive in Reihenfolge absteigender Güte durchlaufen und ggf. gelöscht
     * oder durch bessere Spalten ersetzt. Die Zulässigkeit der Lösung bleibt
     * dabei jederzeit erhalten, und es werden ausschließlich Veränderungen an
     * der Lösung vorgenommen, die ihren Zielfunktionswert verbessern oder
     * wenigstens nicht verschlechtern. Nachdem jede Spalte der Ausgangslösung
     * einmal betrachtet und nach Möglichkeit optimiert wurde, wird die
     * verbesserte Lösung zurückgegeben.
     *
     * @param solution - Die von einer Ameise konstruierte, zu verbessernde Ausgangslösung der lokalen Suche
     * @return Die durch die lokale Suche verbesserte Lösung
     */
    @Override
    public Solution search(Solution solution) {

        // das der übergebenen Lösung zugrunde liegende SCP
        SCProblem problem = solution.getProblem();

        /* beim allerersten Aufruf der lokalen Suche müssen die statischen
         * Klassenvariablen auf Grundlage des betrachteten SCP initialisiert werden
         */
        if (!staticFieldsReady) initStaticFields(problem);

        /* Sortieren und speichern der Spalten der Ausgangslösung nach Güte durch sortiertes
         * Einfügen in ein TreeSet.
         */
        TreeSet<WeightedColumn> sortedSolution = sortSolution(problem, solution);

        /* descending Iterator über das TreeSet der Lösungsspalten, für deren
         * Bearbeitung in Reihenfolge absteigender Güte
         */
        Iterator<WeightedColumn> decIt = sortedSolution.descendingIterator();

        /* Einrichten eines Arrays, in dem für jede Zeile i die Anzahl der sie aktuell
         * überdeckenden Lösungsspalten gespeichert wird; wird anhand der Startlösung
         * initialisiert und bei jeder vorgenommenen Veränderung aktualisiert
         */
        int[] nrOfCoveringColumns = getNrOfCoveringColumns(problem, solution);

        /* Variable für die aktuell betrachtete Lösungsspalte als vergleichbares
         * WeightedColumn-Objekt
         */
        WeightedColumn aktColumn;

        /* Liste für die Zeilen, die zum Betrachtungszeitpunkt ausschließlich von
         * der aktuell betrachteten Lösungsspalte überdeckt werden
         */
        ArrayList<Integer> exclusivelyCoveredRows = new ArrayList<Integer>();

        // durchlaufen aller Lösungsspalten in absteigender Güte durch den descending Iterator
        while (decIt.hasNext()) {
            aktColumn = decIt.next();

            // sammeln der Zeilen, die ausschließlich von aktColumn überdeckt werden
            exclusivelyCoveredRows.clear();
            for (Integer i : problem.getStructure().getElementsInSubset(aktColumn.getIndex()))
                if (nrOfCoveringColumns[i] == 1) exclusivelyCoveredRows.add(i);

            // weiterer Ablauf in Abhängigkeit von der Anzahl der exklusiv von aktColumn überdeckten Zeilen
            switch (exclusivelyCoveredRows.size()) {

                case 0:
                    /* keine Zeile wird ausschließlich von aktColumn überdeckt;
                     * aktColumn ist redundant und kann aus der Lösung entfernt werden */
                    nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                    break;

                case 1: /* genau eine Zeile i wird ausschließlich von aktColumn überdeckt;
                 * vergleiche aktColumn mit der besten i überdeckenden Spalte und
                 * ersetze aktColumn ggf. in der Lösung durch diese */
                    WeightedColumn bestColumn = bestCoveringColumns[exclusivelyCoveredRows.get(0)];
                    if (aktColumn.compareTo(bestColumn) != 0) {
                        nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                        nrOfCoveringColumns = addAndUpdate(solution, bestColumn.getIndex(), nrOfCoveringColumns);
                    }
                    break;

                case 2:
                    /* genau zwei Zeilen i1, i2 werden ausschließlich von aktColumn überdeckt;
                     * vergleiche aktColumn mit den besten i1, i2 überdeckenden Spalten und
                     * ersetze aktColumn ggf. in der Lösung durch diese */
                    WeightedColumn bestColumn1 = bestCoveringColumns[exclusivelyCoveredRows.get(0)];
                    WeightedColumn bestColumn2 = bestCoveringColumns[exclusivelyCoveredRows.get(1)];

                    if (bestColumn1.compareTo(bestColumn2) == 0) {
                        if (aktColumn.compareTo(bestColumn1) != 0) {
                            /* i1 und i2 besitzen dieselbe beste überdeckende Spalte,
                             * und diese ist verschieden von aktColumn;
                             * ersetze aktColumn durch die beste überdeckende Spalte
                             */
                            nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn1.getIndex(), nrOfCoveringColumns);
                        }
                    } else // die beiden Zeilen besitzen unterschiedliche beste Spalten
                        if (bestColumn1.getWeight() + bestColumn2.getWeight() <= aktColumn.getWeight()) { // die Summe der Kosten der beiden besten Spalten ist immer noch mindestens so gut wie die von aktSet
                            /* die Summe der Kosten der beiden besten Spalten ist kleiner-gleich
                             * der Kosten von aktColumn; ersetze aktColumn durch beide besten Spalten
                             */
                            nrOfCoveringColumns = removeAndUpdate(solution, aktColumn.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn1.getIndex(), nrOfCoveringColumns);
                            nrOfCoveringColumns = addAndUpdate(solution, bestColumn2.getIndex(), nrOfCoveringColumns);
                        }
                    break;

                default:
                    /* es werden mehr als zwei Zeilen ausschließlich von aktColumn überdeckt;
                     * belasse aktColumn unverändert in der Lösung und fahre mit der nächsten
                     * Lösungsspalte fort */
                    break;
            }
        }
        return solution;
    }

    /**
     * Methode zum initialisieren der statischen Klassenvariablen sortedColumns
     * und bestColumns. Synchronisiert für parallele ACO-Algorithmen.
     *
     * @param problem - Das zugrunde liegende SCP
     */
    private synchronized void initStaticFields(SCProblem problem) {
        if (!staticFieldsReady) {
            initSortedColumns(problem);
            initBestColumns(problem);
            staticFieldsReady = true;
        }
    }

    /**
     * Methode zum Initialisieren der statischen Klassenvariable sortedColumns.
     * Wird von initStaticFields aufgerufen.
     *
     * @param problem - Das zugrunde liegende SCP
     */
    private synchronized void initSortedColumns(SCProblem problem) {
        if (sortedColumns == null) {
            sortedColumns = new TreeSet<WeightedColumn>();
            WeightedColumn aktColumn;
            float[] weights = problem.getObjectiveFunction().getWeights();
            for (int j = 0; j < problem.getStructure().subsetsSize(); j++) {
                aktColumn = new WeightedColumn(j, weights[j], problem.getStructure().getElementsInSubset(j).size());
                sortedColumns.add(aktColumn);
            }
        }
    }

    /**
     * Methode zum Initialisieren der statischen Klassenvariable bestColumns.
     * Wird von initStaticFields aufgerufen.
     *
     * @param problem - Das zugrunde liegende SCP
     */
    private synchronized void initBestColumns(SCProblem problem) {
        if (bestCoveringColumns == null) {
            int nrOfRows = problem.getStructure().elementsSize();
            bestCoveringColumns = new WeightedColumn[nrOfRows];
            for (int i = 0; i < nrOfRows; i++) setBestColumn(i, problem);
        }
    }

    /**
     * Hilfsmethode für initBestColumns. Sucht für eine Zeile i die beste sie
     * überdeckende Spalte in sortedColumns und speichert diese als vergleichbares
     * WeightedColumn-Objekt in bestCoveringColumns[i].
     *
     * @param i       - Index der Zeile in der Strukturmatrix, für die die beste überdeckende
     *                Spalte gesetzt werden soll
     * @param problem - Das zugrunde liegende SCP
     */
    private synchronized void setBestColumn(int i, SCProblem problem) {
        WeightedColumn column;

        /* Da sortedColumns nach absteigender Güte sortiert ist, muss dort nur
         * sukzessive nach der ersten i überdeckenden Spalte gesucht werden. Diese
         * ist dann automatisch auch die Beste.
         */
        Iterator<WeightedColumn> it = sortedColumns.iterator();
        boolean covered = false;
        while (!covered && it.hasNext()) {
            column = it.next();
            if (problem.getStructure().getRelation(i, column.getIndex())) {
                bestCoveringColumns[i] = column;
                covered = true;
            }
        }
    }

    /**
     * Methode, um die Spalten der Ausgangslösung nach Güte zu sortieren. Diese
     * werden dafür als WeightedColumn-Objekte sortiert in ein TreeSet eingefügt.
     *
     * @param problem  - Das zugrunde liegende SCP
     * @param solution - Die zu sortierende Ausgangslösung
     * @return Die Ausgangslösung als nach Güte sortiertes WeightedColumn-TreeSet
     */
    private TreeSet<WeightedColumn> sortSolution(SCProblem problem, Solution solution) {
        TreeSet<WeightedColumn> sortedSolution = new TreeSet<WeightedColumn>();
        float[] weights = problem.getObjectiveFunction().getWeights();
        List<Integer> columnsInSolution = solution.getSubsets();
        for (int column : columnsInSolution)
            sortedSolution.add(new WeightedColumn(column, weights[column], problem.getStructure().getElementsInSubset(column).size()));
        return sortedSolution;
    }

    /**
     * Methode, die für jede Zeile der Strukturmatrix die Anzahl der sie überdeckenden
     * Lösungsspalten aus der übergebenen Lösung solution bestimmt.
     *
     * @param problem  - Das zugrunde liegende SCP
     * @param solution - Die betrachtete Lösung
     * @return int-Array, der unter dem Index i die Anzahl der Lösungsspalten hält, von denen Zeile i überdeckt wird
     */
    private int[] getNrOfCoveringColumns(SCProblem problem, Solution solution) {
        int nrOfRows = problem.getStructure().elementsSize();
        List<Integer> columnsInSolution = solution.getSubsets();
        int[] nrOfCoveringColumns = new int[nrOfRows];
        for (int i = 0; i < nrOfRows; i++) {
            nrOfCoveringColumns[i] = 0;
            for (int column : columnsInSolution)
                if (problem.getStructure().getRelation(i, column)) nrOfCoveringColumns[i]++;
        }
        return nrOfCoveringColumns;
    }

    /**
     * Methode, die die Spalte mit dem übergebenen Index aus der übergebenen Lösung
     * solution entfernt, und im übergebenen Array den Wert aller von der entfernten
     * Spalte überdeckten Zeilen um 1 erniedrigt.
     *
     * @param solution              - Die Lösung, aus der eine Spalte entfernt werden soll
     * @param indexOfColumnToDelete - Der Index der Spalte, die entfernt werden soll
     * @param nrOfCoveringColumns   - Array, der für alle Zeilen die Anzahl der sie
     *                              überdeckenden Lösungsspalten der übergebenen Lösung hält
     * @return Den aktualisierten Array, der für alle Zeilen die Anzahl der sie
     * überdeckenden Lösungsspalten nach Entfernen der Spalte hält
     */
    private int[] removeAndUpdate(Solution solution, int indexOfColumnToDelete, int[] nrOfCoveringColumns) {

        // Spalte aus Lösung entfernen
        solution.removeSubset(indexOfColumnToDelete);

        // nrOfCoveringSets aktualisieren
        for (int i : solution.getProblem().getStructure().getElementsInSubset(indexOfColumnToDelete))
            nrOfCoveringColumns[i]--;

        return nrOfCoveringColumns;
    }

    /**
     * Methode, die der übergebenen Lösung solution die Spalte mit dem übergebenen
     * Index hinzufügt, und im übergebenen Array den Wert aller von der hinzugefügten
     * Spalte überdeckten Zeilen um 1 erhöht.
     *
     * @param solution            - Die Lösung, zu der eine Spalte hinzugefügt werden soll
     * @param indexOfColumnToAdd  - Der Index der Spalte, die hinzugefügt werden soll
     * @param nrOfCoveringColumns - Array, der für alle Zeilen die Anzahl der sie
     *                            überdeckenden Lösungsspalten der übergebenen Lösung hält
     * @return Den aktualisierten Array, der für alle Zeilen die Anzahl der sie
     * überdeckenden Lösungsspalten nach Hinzufügen der neuen Spalte hält
     */
    private int[] addAndUpdate(Solution solution, int indexOfColumnToAdd, int[] nrOfCoveringColumns) {

        // Spalte der Lösung hinzufügen
        solution.addSubset(indexOfColumnToAdd);

        // nrOfCoveringSets aktualisieren
        for (int i : solution.getProblem().getStructure().getElementsInSubset(indexOfColumnToAdd))
            nrOfCoveringColumns[i]++;
        return nrOfCoveringColumns;
    }


    /**
     * Klasse, die eine Ordnung auf den Spalten der Strukturmatrix einführt, die
     * sich in erster Instanz nach Kosten, in zweiter Instanz nach Anzahl überdeckter
     * Zeilen und in letzter Instanz (für equals()-Konsistenz) nach Spaltenindex
     * richtet. Dabei gilt "besser" = geringere Kosten; bei gleichen Kosten: höhere
     * Anzahl überdeckter Zeilen; bei gleicher Anzahl überdeckter Zeilen: kleinerer
     * Index. Wird z.B. verwendet, um Spaltenmengen automatisch sortiert in ein TreeSet
     * einzufügen.
     */
    class WeightedColumn implements Comparable<WeightedColumn> {

        /**
         * Index der Spalte in der Strukturmatrix
         */
        private int index;

        /**
         * mit der Spalte assoziiertes Gewicht bzw. Kosten
         */
        private float weight;

        /**
         * Anzahl der von der Spalte überdeckten Zeilen der Strukturmatrix
         */
        private int nrOfCoveredRows;

        WeightedColumn(int i, float w, int nocr) {
            this.index = i;
            this.weight = w;
            this.nrOfCoveredRows = nocr;
        }

        /**
         * Die Vergleichsmethode. Wird auf einem WeightedColumn-Objekt thisColumn aufgerufen.
         *
         * @param otherColumn - die Spalte, mit der die aufrufende Spalte verglichen werden soll
         * @return -1, falls thisColumn besser als otherColumn<br />0, falls thisColumn = otherColumn<br />1, falls thisColumn schlechter als otherColumn
         */
        public int compareTo(WeightedColumn otherColumn) {
            int result;
            if (this.weight < otherColumn.weight) result = -1;
            else if (this.weight > otherColumn.weight) result = 1;
            else { //die Gewichte sind identisch
                result = otherColumn.nrOfCoveredRows - this.nrOfCoveredRows;
                if (result == 0) // die Anzahl der überdeckten Elemente ist auch identisch -> Sortierung nach Index für equals-Konsistenz
                    result = this.index - otherColumn.index;
            }
            return result;
        }

        int getIndex() {
            return index;
        }

        float getWeight() {
            return weight;
        }

        int getNrOfCoveredRows() {
            return nrOfCoveredRows;
        }
    }

}