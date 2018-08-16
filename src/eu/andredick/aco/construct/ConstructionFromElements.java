package eu.andredick.aco.construct;

import eu.andredick.aco.nextstep.AbstractNextStepStrategy;
import eu.andredick.scp.SCPSolution;
import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Structure;
import eu.andredick.tools.Tools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <b>Realisierung der Komponente Konstruktionsheuristik.</b><br>
 * Kapitel 3.3.4, S. 30, Konstruktionsheuristik<br>
 * <br>
 * Die Konstruktion der SCP-Lösung einer Ameise erfolgt erfolgt <b>ausgehend von den Grundelementen.</b><br>
 * Die Konstruktionsheuristik wird von der Klasse {@link eu.andredick.aco.ant.ACOAnt} (Ameise) verwendet, um neue Lösungen zu konstruieren.<br>
 * <p><img src="{@docRoot}/images/Construction.svg" alt=""></p>
 */
public class ConstructionFromElements extends AbstractConstructionStrategy<AbstractNextStepStrategy<?>, SCProblem> {

    public ConstructionFromElements(AbstractNextStepStrategy nextStepRule) {
        super(nextStepRule);
    }

    /**
     * <b>Die Konstruktion der Lösung erfolgt itarativ in folgenden Schritten:</b><br>
     * 1. zufällige Auswahl eines Grundelements, welches noch nicht überdeckt ist<br>
     * 2. bestimmen der Teilmengen, die das Grundelement enthalten und noch nicht in Lösung sind<br>
     * 3. Auswahl einer dieser Teilmengen über NextStep<br>
     * 4. bestimmen der Grundelemente, die in der ausgewählten Teilmenge enthalten sind<br>
     * 5. hinzufügen der bestimmten Grundelemente zur Menge bereits überdeckter Grundelemente<br>
     * 6. Zurück zu 1, wenn es weitere nicht überdeckte Grundelemente gibt<br>
     *
     * @param problem Das SCProblem, für das eine Lösung zu konstruieren ist
     * @return konstruierte zulässige Lösung
     */
    @Override
    public SCPSolution construct(SCProblem problem) {
        Structure structure = problem.getStructure();
        SCPSolution solution = new SCPSolution(problem);

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
