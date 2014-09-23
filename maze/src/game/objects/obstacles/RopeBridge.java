package game.objects.obstacles;

import static game.core.inputs.Commands.*;
import static game.player.util.Attributes.*;
import static game.objects.general.References.*;
import static util.Loggers.log;
import static util.Print.print;
import static util.Utilities.anyNullObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import util.View;
import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Priority;
import game.core.events.Theatres.ResultMessage;
import game.core.inputs.Commands;
import game.core.inputs.GameInputHandler;
import game.core.inputs.MultipleChoiceInputHandler;
import game.core.interfaces.Stage;
import game.core.maze.AbstractRoom;
import game.core.maze.Maze;
import game.core.maze.Maze.Room;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.objects.general.References;
import game.objects.items.AbstractItemFixture;
import game.objects.items.Useables;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public class RopeBridge extends AbstractItemFixture {
    private static Random rand = new Random();

    protected References ref = ROPE_BRIDGE;
    protected String desc = null;
    protected String name = "Rope Bridge";
    protected List<Coordinate> storedNeighbors = new ArrayList<Coordinate>();
    protected Coordinate bridgeLocation;
    protected Coordinate targetLocation;

    protected RopeBridge(AbstractRoom room) {
        super();
        this.desc = "It's an old, fraying rope bridge. It looks like it would collapse if you "
                + "breathed on it. Below the bridge is a bottomless abyss. ";
    }


    public static boolean addBridge(Maze maze) {

        List<Coordinate> coordinateSet = new ArrayList<Coordinate>();

        for (Coordinate coord : maze.map().viewTwoLinkNodes()) {
            coordinateSet.add(coord);
        }

        Coordinate spawnPoint;

        Coordinate[] spawnPoints =
                coordinateSet.toArray(new Coordinate[coordinateSet.size()]);

        spawnPoint = spawnPoints[rand.nextInt(spawnPoints.length)];

        Room room = maze.getRoom(spawnPoint);

        RopeBridge rp = new RopeBridge(room);

        for (Coordinate coord : room.viewNeighbors()) {
            log("storing coord " + coord);
            rp.storedNeighbors.add(coord);
        }

        Coordinate[] neighbors = rp.storedNeighbors.toArray(new Coordinate[rp.storedNeighbors.size()]);

        /*
        for (int i=0;i<neighbors.length;i++) {
            Coordinate neighbor = neighbors[i];
            log("deleting link between " + spawnPoint + " and " + neighbor);
            maze.map().deleteLink(spawnPoint, neighbor);
        }
        */

        log("spawning rope bridge at " + spawnPoint);
        rp.bridgeLocation = spawnPoint;
        room.addActor(rp);
        return true;
    }

    @Override
    public boolean canInteract(AbstractUnit unit) {
      return unit instanceof Player ? true : false;
    }

    @Override
    public String name() { return name; }

    @Override
    public boolean matches(String name) {
      return matchRef(ref, name);
    }

    @Override
    public boolean isDone(Stage stage) {
        return false;
    }

    @Override
    public Event interact(Commands trigger) {
        Event event = null;
        if (trigger == DESCRIBE) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {
                    Coordinate playerLocation = player.location();

                    print(desc);
                    print("The rope bridge provides access to the " +
                            Cardinals.get(playerLocation, targetLocation) + ".");
                    print("Do you want to approach it?");
                    return null;
                }
            };
        } else if (trigger == MOVE) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {
                    Coordinate playerLocation = player.location();
                    Coordinate playerPrevLoc = player.getPrevLocation();
                    targetLocation = null;

                    for (Coordinate neighbor : storedNeighbors) {
                        if (!neighbor.equals(playerPrevLoc)) {
                            targetLocation = neighbor;
                        }
                    }

                    player.maze().map().deleteLink(playerLocation, targetLocation);

                    return null;
                }
            };
        } else if (trigger == LEAVE) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {

                    // restore links
                    for (Coordinate neighbor : storedNeighbors) {
                        player.maze().map().link(bridgeLocation, neighbor);
                    }

                    return null;
                }
            };
        } else if (trigger == APPROACH) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {

                    print("You approach the fraying rope bridge. Are you sure about this?");
                    print("A) Attempt to cross the bridge.");
                    print("B) Leave.");

                    String input = MultipleChoiceInputHandler.run(2);
                    log("input entered was " + input);

                    if (input.equals("A")) {
                        if (!player.skillCheck(DEX, 0, 2)) {
                            print("Uh oh! One of the rotten wooden planks breaks under your "
                                    + "weight and you find yourself falling, and falling, and "
                                    + "falling...");
                            print("You're pretty much dead.");
                            player.death();
                        } else {
                            print("You gingerly make your way across the rope bridge.");

                            //restore link
                            player.maze().map().link(player.location(), targetLocation);

                            Cardinals direction = Cardinals.get(player.location(), targetLocation);

                            //String stringDir = direction.toString();

                            GameInputHandler.run("move " + direction, player, player.maze());
                        }

                    } else if (input.equals("B")) {
                        print("You decide to find another way around for now.");
                    }

                    return null;
                }
            };
        }
        return event;
    }

}
