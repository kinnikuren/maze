package maze;

import static maze.References.*;
import static util.Print.*;
import static java.lang.Math.*;

import java.util.LinkedList;

public final class Useables {
    private Useables() {} //no instantiation
    //contains various consumable classes that extend AbstractItemUseable

    public static class Compass extends AbstractItemUseable{
        public Compass() { }
        public Compass(Coordinate c) { super(c); }
        @Override public String name() { return "Compass"; }
        @Override public boolean matches(String name) { return matchRef(COMPASS, name); }
        @Override public String details() { return "This will get you were you need to go."; }
        @Override public int weight() { return 1; }

        @Override
        public void usedBy(Player player) {
            LinkedList<Coordinate> path = null;
            Coordinate nextRoom = null;
            Coordinate diff = null;
            Maze maze = player.maze();

            AStar.reset();
            path = AStar.discover(player.location(),player.maze().exit(),player.maze());
            if (path.size() >= 2) {
                nextRoom = path.get(path.size()-2);
            } else {
                nextRoom = path.get(0);
            }
            diff = Coordinates.diff(player.location(), nextRoom);

            //print(path);
            //print(nextRoom);
            //print(diff);
            //print(path.get(path.size()-2));
            //print(player.maze().viewNeighborsOf(player.location()));
            //print(player.maze().viewAvailableMoves(player.location()));

            print("The Compass points " + Cardinals.get(diff) + ".");
        }
    }
}
