package tests;
import java.util.*;

import util.ReadOnlyFlatIterator;
import util.View;
import static util.Print.*;
import maze.*;

public class ExceptionTest {

    public static void main(String[] args)
            throws UnsupportedOperationException {
        Set<Node> nodeSet = new HashSet<Node>();
        nodeSet.add(new Node(1,2));
        nodeSet.add(new Node(2,2));
        nodeSet.add(new Node(3,2));
        View<Coordinate> nodeView = new View<Coordinate>(nodeSet);

        Coordinate c1 = new Coordinate(2,2);
        Coordinate c2 = new Coordinate(-2,2);
        print(nodeView.contains(c1));
        print(nodeView.contains(c2));

        tryRemoving(nodeSet.iterator());
    }

    public static void tryRemoving(Iterator<? extends Coordinate> itr)
            throws UnsupportedOperationException {
        /*ReadOnlyFlatIterator<Coordinate> rofi =
                new ReadOnlyFlatIterator<Coordinate>(itr);
        for (rofi = rofi; rofi.hasNext();) {
            Coordinate c = rofi.next();
            print(c);
            rofi.remove(); */
            /* try {
                rofi.remove();
            } catch (UnsupportedOperationException uoe) {
                uoe.printStackTrace();
            }
        } */
    }
}
