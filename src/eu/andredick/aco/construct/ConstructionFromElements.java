package eu.andredick.aco.construct;

import eu.andredick.aco.nextstep.AbstractNextStepRule;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Solution;
import eu.andredick.scp.Structure;
import eu.andredick.tools.Tools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Konstruktionsmechanismus einer zulässigen Lösung, von Grundelementen ausgehend.
 * Basiert auf der Pherromonassoziation mit Teilmengen.
 */
public class ConstructionFromElements extends AbstractConstructionStrategy {

    public ConstructionFromElements(AbstractNextStepRule nextStepRule) {
        super(nextStepRule);
    }

    /**
     * Die Konstruktion der Lösung erfolgt itarativ in folgenden Schritten:
     * 1. zufällige Auswahl eines Grundelements, welches noch nicht überdeckt ist
     * 2. bestimmen der Teilmengen, die das Grundelement enthalten und noch nicht in Lösung sind
     * 3. Auswahl einer dieser Teilmengen über NextStep
     * 4. bestimmen der Grundelemente, die in der ausgewählten Teilmenge enthalten sind
     * 5. hinzufügen der bestimmten Grundelemente zur Menge bereits überdeckter Grundelemente
     * 6. Zurück zu 1, wenn es weitere nicht überdeckte Grundelemente gibt
     *
     * @param problem: Das SCProblem, für das eine Lösung zu konstruieren ist
     * @return: konstruierte zulässige Lösung
     */
    @Override
    public Solution construct(SCProblem problem) {
        Structure structure = problem.getStructure();
        Solution solution = new Solution(problem);

        List<Integer> elements = Tools.getIndexList(structure.elementsSize()); //geordnet
        List<Integer> tabuSubsets = new LinkedList<>();
        //List<Integer> subsets = Tools.getIndexList(structure.subsetsSize()); //geordnet

        while (!elements.isEmpty()) {
            // Zufällige Wahl eines Elementes aus der Grundmenge
            int r = ThreadLocalRandom.current().nextInt(elements.size());           //Zufahlszahl 0 .. mE.size()
            int elementIndex = elements.get(r);

            // Alle Teilmengen für das gewählte Element
            List<Integer> subsetsWithElement;
            subsetsWithElement = new LinkedList<>(structure.getSubsetsWithElement(elementIndex));

            // Schnittmenge mit den verbliebenen Teilmengen
            subsetsWithElement.removeAll(tabuSubsets);

            // Auswahl einer Teilmenge aus den noch verfügbaren Teilmengen
            int subsetIndex = this.nextStepRule.chooseSubset(solution, subsetsWithElement);

            // Entfernen aller Elemente der gewählten Teilmenge aus den zu berücksichtigenden
            for (Integer e : structure.getElementsInSubset(subsetIndex)) {
                elements.remove(e);
            }


            // Entfernen der gewählten Teilmenge aus den zu berücksichtigenden
            //subsets.remove( (Integer)subsetIndex);
            tabuSubsets.add((Integer) subsetIndex);


            // Einfügen der Teilmenge in die Lösung
            solution.addSubset(subsetIndex);

        }
        return solution;
    }
}
