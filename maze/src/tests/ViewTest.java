package tests;

import game.core.maze.AbstractRoom;
import game.core.positional.Coordinate;
import game.core.positional.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import util.View;
import static game.core.maze.AbstractRoom.*;
import static util.Print.*;

public class ViewTest {

    public static void main(String[] args) {
        Set<Node> nodeSet = new HashSet<Node>();
        Node n1 = new Node(0,0);
        Node n2 = new Node(1,2);
        Node n3 = new Node(6,6);
        n1.addLinkTo(n2);
        n2.addLinkTo(n3);
        nodeSet.add(n1); nodeSet.add(n2); nodeSet.add(n3);
        View<Coordinate> viewCoordinates = new View<Coordinate>(nodeSet);

        Set<AbstractRoom> roomSet = new HashSet<AbstractRoom>();
        roomSet.add(testRoom(n3));
        //View<Coordinate> viewRooms = new View<Coordinate>(roomSet);

        for (Coordinate c : viewCoordinates) {
            print(c);
        }
        for (Iterator<Node> i = nodeSet.iterator(); i.hasNext();) {
            Node n = i.next();
            print(n + "=> " + n.viewNeighbors());
        }
        HashMap<Node, AbstractRoom> roomMap = new HashMap<Node, AbstractRoom>();
        roomMap.put(n1, testRoom(n1));
        roomMap.put(n2, testRoom(n2));

        View<Integer> vi = new View<Integer>(roomMap);
        View<Entry<Coordinate, AbstractRoom>> vi2 = new View<Entry<Coordinate, AbstractRoom>>(roomMap);
        print("-----------");
        print("roomMap");
        for (Entry<Coordinate,AbstractRoom> e : vi2) {
            print(e);
            //Node ii = e.getKey().toNode();
            //ii.addLinkTo(n3);
        }
    }
}
