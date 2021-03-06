package util;

import java.util.*;

import org.apache.commons.collections4.CollectionUtils;

import game.core.positional.Node;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class Tuples {
    private Tuples() { } //no instantiation

    public static <X, Y> Function<Tuple<X, Y>, X> xFunction() {
        return new Function<Tuple<X, Y>, X>() {
            @Override
            public X apply(Tuple<X, Y> tuple) {
                return tuple.x;
            }
        };
    }

    public static <X, Y> Function<Tuple<X, Y>, Y> yFunction() {
        return new Function<Tuple<X, Y>, Y>() {
            @Override
            public Y apply(Tuple<X, Y> tuple) {
                return tuple.y;
            }
        };
    }

    public static <X, Y> View<X> xViewOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        final Function<Tuple<X, Y>, X> xF = Tuples.xFunction();
        return new View<X>(fromIterable, xF);
    }

    public static <X, Y> View<Y> yViewOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        final Function<Tuple<X, Y>, Y> yF = Tuples.yFunction();
        return new View<Y>(fromIterable, yF);
    }

    public static <X, Y> Set<X> xSetOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        Set<X> xSet = new HashSet<X>();
        for (Tuple<X, Y> t : fromIterable) {
            xSet.add(t.x);
        }
        return xSet;
    }

    public static <X, Y> Set<Y> ySetOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        Set<Y> ySet = new HashSet<Y>();
        for (Tuple<X, Y> t : fromIterable) {
            ySet.add(t.y);
        }
        return ySet;
    }

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

        //min Y cardinality will be determined amongst the valid X candidates only
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

        List<Tuple<X, Y>> tuplesList = new ArrayList<Tuple<X, Y>>(tuples);
        if (tuplesList.size() == 1) return tuplesList.get(0);
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
            System.out.println("TUPLE SIZE: " + tuples.size());
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



}
