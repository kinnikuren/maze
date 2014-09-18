package tests;

import game.core.positional.Coordinate;

import java.util.*;

public class ReverseTest {
    public static void main(String[] args) {
        LinkedList<Coordinate> a = new LinkedList<Coordinate>();
        a.add(new Coordinate());
        a.add(new Coordinate(1,0));
        a.add(new Coordinate(5,3));
        a.add(new Coordinate(1,2));

        for (Iterator<Coordinate> i = a.descendingIterator(); i.hasNext();) {
            Coordinate s = i.next();
            System.out.println(s);

        }

    }

}
