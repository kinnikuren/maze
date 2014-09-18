package tests;

import static util.Print.*;
import game.core.maze.MazeMap;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Node;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.*;
import com.google.common.base.*;

public class NodeTest {

    public static void main(String[] args) {
        Node n1 = new Node(4,3);
        Coordinate c1 = new Coordinate();
        Node n2 = new Node(n1, Cardinals.NORTH);
        Node n3 = new Node(n1, Cardinals.SOUTH);
        Node n4 = new Node(-3,-3);
        Node n5 = new Node(0,0);

        n1.addLinkTo(n2);
        n1.addLinkTo(n3);
        print("n1 neighbors -> " + n1.viewNeighbors());

        MazeMap map = new MazeMap();
        map.add(n1); map.add(n2); map.add(n3);

        print("unlinked -> "); print(map.viewUnlinkedNodes());
        print("-----");
        print("linked -> "); print(map.viewLinkedNodes());
        print("-----");

        /* RoomOld r = new RoomOld(n1);
        map.build(r);
        print("room built"); print("-----");

        print("n1 neighbors -> " + n1.viewNeighbors());
        print("unlinked -> "); map.printUnlinkedNodes();
        print("-----");
        print("linked -> "); print(map.viewLinkedNodes());
        print("-----");

        //print("n1 at " + n1 + " with " + map.getRoom(n1));
        for (Entry<Coordinate, RoomOld> e : map.view()) {
            System.out.print(e.getKey() + " :==: " + e.getValue() + "\n");
            Coordinate key = e.getKey();
            Node nx = key.toNode();
            nx.addLinkTo(n5);
            print("Node version " + nx + " and neighbors " + nx.viewNeighbors());
        } */

        /*for (Cardinals card : Coordinate.LESSER_CARDINALS) {
            //..
        }

        Coordinate[] n1Neighbors = n1.getNeighboringPrimes();

        for (Coordinate c : n1Neighbors) {
            print(c);
        }

        if (c1.equals(n1)) print("c1 equals n1");
        else print("c1 fails to equal n1");

        if (n1.equals(c1)) print("n1 equals c1");
        else print("n1 fails to equal c1");
        */

        //n1.addLinkTo(n2);
        //n3.addLinkTo(n1);
        /* print("n1 link # : " + n1.numberOfLinks());
        print("n2 link # : " + n2.numberOfLinks());
        if (Range.closed(0, 0).contains(n1.numberOfLinks())) print("range 0-0 contains n1");
        if (Range.closed(0, 0).contains(n2.numberOfLinks())) print("range 0-0 contains n2");

        Set<Node> setOfNodes = new HashSet<Node>();
        setOfNodes.add(n1);
        setOfNodes.add(n2);
        setOfNodes.add(n3);
        setOfNodes.add(n4);

        Predicate<Node> pn = Node.Filter.HAS_NO_LINKS;
        Set<Node> filteredSetOfNodes = Sets.filter(setOfNodes, pn);
        print("----- filtered set 1 -----");
        for (Node node : filteredSetOfNodes) print(node);

        //n1.clearAllLinks();
        //n2.addLinkTo(n4);
        Node n5 = new Node(n4, Cardinals.WEST);
        setOfNodes.add(n5);
        print("----- filtered set 2 -----");
        for (Node node : filteredSetOfNodes) print(node);
        */
    }

}
