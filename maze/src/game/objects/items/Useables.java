package game.objects.items;

import static util.Print.*;
import static game.objects.general.References.*;
import static java.lang.Math.*;
import game.core.maze.Maze;
import game.core.maze.Win;
import game.core.pathfinding.AStar;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;
import game.objects.units.Player;

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

    public static class Matches extends AbstractItemUseable{
        public Matches() { super(); }

        @Override public String name() { return "Matches"; }
        @Override public boolean matches(String name) { return matchRef(MATCHES, name); }
        @Override public String details() { return "It's a book of matches."; }
        @Override public int weight() { return 1; }

        @Override
        public void usedBy(Player player) {
            print("You light a match. It briefly lights up the room before burning out.");
        }
    }

    public static class WarpWhistle extends AbstractItemUseable{
        public WarpWhistle() { super(); }
        public WarpWhistle(Coordinate c) { super(c); }
        @Override public String name() { return "Warp Whistle"; }
        @Override public boolean matches(String name) { return matchRef(WARPWHISTLE, name); }
        @Override public String details() { return "Blow it."; }
        @Override public int weight() { return 1; }

        @Override
        public void usedBy(Player player) {
            LinkedList<Coordinate> path = null;

            AStar.reset();

            path = AStar.discover(player.location(),player.maze().exit(),player.maze());

            player.setLocation(player.maze().exit());

            Win.foundExit();
        }
    }
}
