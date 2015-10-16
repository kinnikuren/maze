package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import static util.Tuples.*;

public final class CombineSolver<X> {
    private CombineSolver() { } //no instantiation

    public static <X, Y> Tuple<X, Y> findCandidateTuple(Set<Tuple<X, Y>> fromTuples) {

        Set<Tuple<X, Y>> tuples = new HashSet<Tuple<X, Y>>(fromTuples);
            System.out.println(tuples);
        //derive views of X and Y
        View<X> viewX = xViewOfTuples(tuples);
        View<Y> viewY = yViewOfTuples(tuples);
        System.out.println("view Y: " + viewY);

        //derive min cardinality among X values
        Map<X, Integer> xCardMap = CollectionUtils.getCardinalityMap(viewX);
            System.out.println(xCardMap);
        int xMinCard = Collections.min(xCardMap.values());
            System.out.println("min x cardinality: " + xMinCard);

        //min Y cardinality will be determined amongst the Y values of the valid X candidates only
        //but the cardinality itself must be derived over the entire view
        Map<Y, Integer> yCardMap = CollectionUtils.getCardinalityMap(viewY);
        System.out.println(yCardMap);

        //get X values which have the minimum cardinality
        //these are candidate X values
        Set<X> xCandidates = new HashSet<X>();
        for (Map.Entry<X, Integer> entry: xCardMap.entrySet()) {
            if (xMinCard == entry.getValue()) {
                System.out.println("X matched: " + entry.getKey());
              xCandidates.add(entry.getKey());
            }
        }

        //remove tuples which don't have X values amongst the candidate X values
        //what remains are the candidate tuples
        Iterator<Tuple<X, Y>> itrX = tuples.iterator();
        while (itrX.hasNext()) {
            Tuple<X, Y> t = itrX.next();
            if (!xCandidates.contains(t.x)) {
                itrX.remove();
            }
        }
            System.out.println(tuples);
            System.out.println("view Y: " + viewY);

        //remove Y cardinality mappings for the Y values which aren't present amongst the candidate tuples
        //since viewY is a live view, it sees only the Y values from the remaining candidates
        Iterator<Map.Entry<Y, Integer>> itrYMap = yCardMap.entrySet().iterator();
        while (itrYMap.hasNext()) {
            Map.Entry<Y, Integer> yEntry = itrYMap.next();
            if (!viewY.contains(yEntry.getKey())) {
                itrYMap.remove();
            }
        }
        System.out.println(yCardMap);

        //derive min Y cardinality amongst the remaining valid values
        int yMinCard = Collections.min(yCardMap.values());
        System.out.println("min y cardinality: " + yMinCard);

        //get Y values which have the minimum cardinality
        //these are candidate Y values
        Set<Y> yCandidates = new HashSet<Y>();
        for (Map.Entry<Y, Integer> entry: yCardMap.entrySet()) {
            if (yMinCard == entry.getValue()) {
                System.out.println("Y matched: " + entry.getKey());
              yCandidates.add(entry.getKey());
            }
        }

        //now remove tuples which don't have Y values amongst the candidate Y values
        //what remains are the final candidate tuples
        Iterator<Tuple<X, Y>> itrY = tuples.iterator();
        while (itrY.hasNext()) {
            Tuple<X, Y> t = itrY.next();
            if (!yCandidates.contains(t.y)) {
                itrY.remove();
            }
        }
            System.out.println(tuples);

        //return an arbitrary final candidate tuple if more than one exists
        List<Tuple<X, Y>> tuplesList = new ArrayList<Tuple<X, Y>>(tuples);
        if (tuplesList.size() == 1)
            return tuplesList.get(0);
        else {
            Random rnd = new Random();
            int i = rnd.nextInt(tuplesList.size());
            return tuplesList.get(i);
        }
    }

    public static <X, Y> Set<Tuple<X, Y>> findSolutionTuples(Set<Tuple<X, Y>> fromTuples) {

        Set<Tuple<X, Y>> tuples = new HashSet<Tuple<X, Y>>(fromTuples);
        Set<Tuple<X, Y>> solutions = new HashSet<Tuple<X, Y>>();

        while (tuples.size() > 0) {

            System.out.println(tuples);
            System.out.println("TUPLE SET SIZE: " + tuples.size());
            System.out.println("--------------------");

            Tuple<X, Y> tCandidate = findCandidateTuple(tuples);
            System.out.println("--------------------");
            System.out.println("candidate: " + tCandidate);
            solutions.add(tCandidate);

            Iterator<Tuple<X, Y>> itrT = tuples.iterator();
            while (itrT.hasNext()) {
                Tuple<X, Y> t = itrT.next();
                if (t.x.equals(tCandidate.x) || t.y.equals(tCandidate.y)) {
                    itrT.remove();
                }
            }
            System.out.println("---After removing linked tuples---");
            System.out.println(tuples);
        }

        return solutions;
    }

    public static <X, Y> boolean isCombineSolutionValid(Set<Tuple<X, Y>> fromTuples,
            Set<Tuple<X, Y>> solutions) {

        Set<X> setX = xSetOfTuples(fromTuples);
        Set<Y> setY = ySetOfTuples(fromTuples);

        if (setX.size() != setY.size()) {
            System.out.println("# of distinct X elements does not match # of distinct Y elements.");
            System.out.println("Therefore no solution is possible.");
            return false;
        }
        if (setX.size() != solutions.size()) {
            System.out.println("# of distinct solution nodes does not match # of distinct X and Y elements");
            System.out.println("Therefore no solution was reached.");
            return false;
        }
        //next is failsafe check against the algorithm
        //should be unecessary since the algorithm shouldn't allow duplicate elements
        //without which the above size check cannot incorrectly pass when the below checks would fail
        View<X> viewX = xViewOfTuples(solutions);
        View<Y> viewY = yViewOfTuples(solutions);
        for (X x : setX) {
            if (!viewX.contains(x)) {
                System.out.println("Element " + x + " is missing from proposed solution");
                return false;
            }
        }
        for (Y y : setY) {
            if (!viewY.contains(y)) {
                System.out.println("Element " + y + " is missing from proposed solution");
                return false;
            }
        }
        //if all checks passed then return true!
        return true;
    }

    public static <X, Y> boolean doesCombineSolutionExist(Set<Tuple<X, Y>> fromTuples) {

        Set<Tuple<X, Y>> solutions = findSolutionTuples(fromTuples);
        return isCombineSolutionValid(fromTuples, solutions);
    }

    public static void main(String[] args) {

        Set<Tuple<String, Integer>> tuples = new HashSet<Tuple<String, Integer>>();

        Tuple<String, Integer> t1 = new Tuple<String, Integer>("A", 1); tuples.add(t1);
        Tuple<String, Integer> t2 = new Tuple<String, Integer>("A", 2); tuples.add(t2);
        Tuple<String, Integer> t3 = new Tuple<String, Integer>("A", 3); tuples.add(t3);
        //Tuple<String, Integer> t4 = new Tuple<String, Integer>("B", 1); tuples.add(t4);
        Tuple<String, Integer> t5 = new Tuple<String, Integer>("B", 2); tuples.add(t5);
        Tuple<String, Integer> t6 = new Tuple<String, Integer>("C", 3); tuples.add(t6);
        //Tuple<String, Integer> t7 = new Tuple<String, Integer>("C", 3); tuples.add(t7);
        //Tuple<String, Integer> t8 = new Tuple<String, Integer>("C", 4); tuples.add(t8);
        //Tuple<String, Integer> t9 = new Tuple<String, Integer>("D", 3); tuples.add(t9);
        //Tuple<String, Integer> t10 = new Tuple<String, Integer>("D", 4); tuples.add(t10);

        Set<Tuple<String, Integer>> solutions = findSolutionTuples(tuples);
        System.out.println("---***************---");
        System.out.println("---***************---");
        System.out.println("---solution tuples---");
        System.out.println(solutions);
        System.out.println("---***************---");
        if (doesCombineSolutionExist(tuples)) {
            System.out.println("A solution was reached!");
        } else {
            System.out.println("No solution was reached. :(");
        }
    }
}
