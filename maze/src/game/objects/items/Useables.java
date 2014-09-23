package game.objects.items;

import static util.Print.*;
import static game.objects.general.References.*;
import static java.lang.Math.*;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.maze.Maze;
import game.core.maze.AbstractRoom;
import game.core.maze.MazeMap.Gate;
import game.core.maze.Win;
import game.core.pathfinding.AStar;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;
import game.objects.obstacles.Cable;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

import java.util.LinkedList;
import java.util.List;

import util.Paired;

public final class Useables {
    private Useables() {} //no instantiation
    //contains various consumable classes that extend AbstractItemUseable

    public static class Compass extends AbstractItemUseable{
        public Compass() { super(); }
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

    public static class DissolvingPotion extends AbstractItemUseable{
        public DissolvingPotion() { super(); }

        @Override public String name() { return "Dissolving Potion"; }
        @Override public boolean matches(String name) { return matchRef(DISSOLVING_POTION, name); }
        @Override public String details() { return "The potion is a dark color and smells acidic."; }
        @Override public int weight() { return 1; }

        @Override
        public void usedBy(Player player) {
            print("You use the dissolving potion.");
        }

        @Override
        public boolean usedBy(Player player, Actor target, Stage targetStage) {
            print("You attempt to use the dissolving potion on the " + target + ".");
            if (target instanceof AbstractUnit) {
                ((AbstractUnit)target).setHP(0);
                print("The " + target + " dissolves into a puddle.");
                targetStage.removeActor(target);
                return true;
            } else {
                print("You can't dissolve the " + target + ".");
                return false;
            }
        }
    }

    public static class WarpWhistle extends AbstractItemUseable{
        public WarpWhistle() { super(); }
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

    public static abstract class Key extends AbstractItemUseable {

        /*
        final String id;


        private Key(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;

            if (o instanceof Key) {
              Key k = (Key)o;
              if (k.id.equals(id)) return true;
            }
          return false;
        }
        @Override
        public int hashCode() { return id.hashCode(); }


        @Override
        public String toString() { return this.id; }
        */
        @Override
        public void usedBy(Player player) {
        }

        @Override
        public boolean usedBy(Player player, Actor target, Stage targetStage) {
            print("You attempt to use the " + this + " on the " + target + ".");
            if (target instanceof Gate) {
                Gate gate = (Gate)target;
                if (!gate.isLocked()) {
                    print("It's already unlocked.");
                } else {
                    String oldGateName = target.name();
                    Paired<Coordinate> c = gate.getCoords();
                    if(gate.unlock(this)) {
                        print("You unlocked it!");
                        player.maze().getRoom(c.o1).interactionMap().remove(oldGateName, target);
                        player.maze().getRoom(c.o2).interactionMap().remove(oldGateName, target);
                        player.maze().getRoom(c.o1).interactionMap().put(target.name(), target);
                        player.maze().getRoom(c.o2).interactionMap().put(target.name(), target);

                    } else {
                        print("You failed to unlock the " + target + " with the " + this.name());
                    }
                }
                return true;
            } else {
                print("You can't unlock the " + target + ".");
                return false;
            }
        }
    }

    public static class PlainKey extends Key {
        public PlainKey() { super(); }
        @Override public String name() { return "Plain Key"; }
        @Override public boolean matches(String name) { return matchRef(PLAIN_KEY, name); }
        @Override public String details() { return "It's a plain, old key."; }
        @Override public int weight() { return 1; }
    }

    public static class RedKey extends Key {
        public RedKey() { super(); }
        @Override public String name() { return "Red Key"; }
        @Override public boolean matches(String name) { return matchRef(RED_KEY, name); }
        @Override public String details() { return "It's a red key."; }
        @Override public int weight() { return 1; }
    }

    public static class BlueKey extends Key {
        public BlueKey() { super(); }
        @Override public String name() { return "Blue Key"; }
        @Override public boolean matches(String name) { return matchRef(BLUE_KEY, name); }
        @Override public String details() { return "It's a blue key."; }
        @Override public int weight() { return 1; }
    }

    public static class PurpleKey extends Key {
        public PurpleKey() { super(); }
        @Override public String name() { return "Purple Key"; }
        @Override public boolean matches(String name) { return matchRef(PURPLE_KEY, name); }
        @Override public String details() { return "It's a purple key."; }
        @Override public int weight() { return 1; }
    }

    public static class RubberChicken extends AbstractItemUseable {
        public RubberChicken() {
            super();
            this.maxUses = 1;
        }

        @Override public String name() { return "Rubber Chicken with a Pulley in the Middle"; }
        @Override public boolean matches(String name) { return matchRef(RUBBER_CHICKEN, name); }
        @Override public String details() { return "A rubber chicken with a pulley in the middle."; }
        @Override public int weight() { return 4; }

        @Override
        public void usedBy(Player player) {
            print("You play with your rubber chicken for a while.");
        }

        @Override
        public boolean usedBy(Player player, Actor target, Stage targetStage) {

            print("You attempt to use the " + this + " on the " + target + ".");
            if (target instanceof Cable) {
                Cable cable = (Cable)target;
                print("You attach the rubber chicken to the cable.");
                print("You've made a strange-looking zipline.");

                cable.enable();

                super.usedBy(player, target, targetStage);
                return true;
            } else {
                print("Your rubber chicken flops uselessly against the " + target + ".");
                return false;
            }
        }
    }
}
