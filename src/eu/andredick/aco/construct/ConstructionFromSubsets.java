package eu.andredick.aco.construct;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;
import eu.andredick.scp.Structure;
import eu.andredick.tools.ArrayTools;
import eu.andredick.tools.Tools;

import java.util.List;

/**
 * <b>Realisierung der Komponente Konstruktionsheuristik.</b><br>
 * Kapitel 3.3.4, S. 30, Konstruktionsheuristik<br>
 * <br>
 * Die Konstruktion der SCP-Lösung einer Ameise erfolgt erfolgt <b>ausgehend von den Teilmengen.</b><br>
 * Die Konstruktionsheuristik wird von der Klasse ACOAnt (Ameise) verwendet, um neue Lösungen zu konstruieren.<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 */
public class ConstructionFromSubsets extends AbstractConstructionStrategy {

    /**
     * Konstruktor
     * @param nextStepRule Beliebige Ausprägung der Komponente Alternativenauswahl (siehe {@link AbstractNextStepStrategy})
     */
    public ConstructionFromSubsets(AbstractNextStepStrategy nextStepRule) {
        super(nextStepRule);
    }


    /**
     * <b>Die Konstruktion der Lösung erfolgt itarativ in folgenden Schritten:</b><br>
     * 1. Auswahl einer dieser Teilmengen über NextStep und hinzufügen zur Lösung<br>
     * 2. Hinzufügen dieser Teilmenge zur TabuListe und zur Lösung<br>
     * 3. bestimmen der Grundelemente, die in der gewählten Teilmenge enthalten sind<br>
     * 4. Entfernen dieser Grundelemente aus allen Teilmengen, die noch verfügbar sind<br>
     * 5. entfernen aller Teilmengen, die nach dem letzten Schritt leer geworden sind<br>
     * 6. entfernen der Grundelemente (3.) aus der Menge noch nicht überdeckter Grundelemente<br>
     * 7. Zurück zu 1, wenn es weitere nicht überdeckte Grundelemente gibt<br>
     *
     * @param problem Das SCProblem, für das eine Lösung zu konstruieren ist
     * @return konstruierte zulässige Lösung
     */
    @Override
    public Solution construct(SCProblem problem) {

        Structure structure = problem.getStructure();
        Solution solution = new Solution(problem);

        // Bereitstellen eines Arrays mit Listen von Grundelementen, die in den Teilmengen enthalten sind
        List<Integer> subsets = Tools.getIndexList(structure.subsetsSize()); //geordnet

        // Bereitstellen der TabuListen für Teilmengen und Grundelemente
        boolean[] tabuSubsets = ArrayTools.getZeroBoolArray(structure.subsetsSize());
        boolean[] tabuElements = ArrayTools.getZeroBoolArray(structure.elementsSize());

        // Bereitstellen einer Arbeits-Liste mit Summen von Elementen, die in Teilmengen enthalten sind
        int[] subsetSizes = new int[structure.subsetsSize()];
        for (int g = 0; g < subsetSizes.length; g++) {
            subsetSizes[g] = structure.getElementsInSubset(g).size();
        }

        // Ein Counter für die Anzahl der noch nicht überdeckter Grundelemente
        int elementsRemain = structure.elementsSize();

        // Iteration, bis keine Grundelemente mehr zu überdecken sind
        while (elementsRemain != 0) {

            // Auswahl einer Teilmenge aus Teilmengen, die noch nicht in Lösung sind
            int subsetIndex = this.nextStepRule.chooseSubset(solution, subsets);

            // Hinzufügen der Teilmenge in die Lösung
            solution.addSubset(subsetIndex);

            // Markieren der gewählten Teilmenge in der TabuListe
            tabuSubsets[subsetIndex] = true;

            // bestimmen der Grundelemente, die in der gewählten Teilmenge enthalten sind
            List<Integer> elementsInSubset = structure.getElementsInSubset(subsetIndex);

            // Im weiteren müssen Teilmengen identifiziert werden, die selbst eine Teilmenge der gewählten Teilmenge sind
            // d.h. Teilmengen, die einen Teil gleicher Elemente überdecken und sonst keine weiteren
            for (Integer e : elementsInSubset) {
                if (!tabuElements[e]) {
                    List<Integer> subsetsWith_e = structure.getSubsetsWithElement(e);
                    for (Integer s : subsetsWith_e) {
                        if (!tabuSubsets[s]) {
                            subsetSizes[s]--;
                            if (subsetSizes[s] == 0) {
                                tabuSubsets[s] = true;
                                subsets.remove(s);
                            }
                        }
                    }
                    tabuElements[e] = true;
                    elementsRemain--;
                }
            }
        }

        return solution;
    }
}
