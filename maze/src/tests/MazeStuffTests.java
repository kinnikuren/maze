package tests;

import maze.Bestiary.*;
import static util.Print.*;

public class MazeStuffTests {

    public static void main(String[] args) {
        /* Cardinals direction = Cardinals.get("ya");
        print(direction);
        if (Coordinate.PRIME_CARDINALS.contains(direction)) print("ya");
        else print("na");

        Cardinals direction2 = Cardinals.get("north");
        if (Coordinate.PRIME_CARDINALS.contains(direction2)) print("north ya");
        else print("north na"); */

        /* ArrayList<Object> testList = new ArrayList<Object>();
        testList.add(new Skeleton());
        testList.add(new Integer(4));
        testList.add(new Integer(5));
        testList.add(new Skeleton());
        testList.add(new Skeleton());

        for (Object o : testList) {
            try { Announcer ann = (Announcer)o; print(ann.announce()); }
            catch (ClassCastException cce) { }
        } */

        /* Idol i = new Idol();
        i.selfPrint(i.announce());
        Player p = new Player();
        i.question(p);
        i.selfPrint(i.announce());
        i.question(p); */

        /* Coordinate c = new Coordinate();
        Room room = new Room(c);
        room.populateRoom();
        print("contents size: " + room.getContents().size());
        //print("announcements: " + room.getA());
        room.announceAll();
        Player dude = new Player();
        room.interactWith(dude); */

        //new Module.FightToken();
        /* Idol i = new Idol();
        Player p = new Player();
        i.accepts(p, Commands.MOVE); */

        Skeleton sk = new Skeleton();
        print(sk.name());
        if (sk.name().equals("Skeleton")) print("it is equal");
    }

}
