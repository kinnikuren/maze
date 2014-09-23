package game.objects.obstacles;

import static game.core.inputs.Commands.*;
import static game.objects.general.References.*;
import static util.Loggers.log;
import static util.Print.print;
import static util.Utilities.anyNullObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.View;
import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Priority;
import game.core.events.Theatres.ResultMessage;
import game.core.inputs.Commands;
import game.core.interfaces.Stage;
import game.core.maze.AbstractRoom;
import game.core.maze.Maze;
import game.core.maze.Maze.Room;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.objects.general.References;
import game.objects.items.AbstractItemFixture;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public class RopeBridge extends AbstractItemFixture {
    private static Random rand = new Random();

    protected References ref = ROPE_BRIDGE;
    protected String desc = null;
    protected String name = "Rope Bridge";
    protected List<Coordinate> storedNeighbors = new ArrayList<Coordinate>();

    protected RopeBridge(AbstractRoom room) {
        super();
        this.desc = "It's an old, fraying rope bridge. It looks like it would collapse if you "
                + "breathed on it.";
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

        for (int i=0;i<neighbors.length;i++) {
            Coordinate neighbor = neighbors[i];
            log("deleting link between " + spawnPoint + " and " + neighbor);
            maze.map().deleteLink(spawnPoint, neighbor);
        }

        log("spawning rope bridge at " + spawnPoint);
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
            /*
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {
                    Coordinate playerLocation = player.location();
                    Coordinate targetLocation = null;


                    if (playerLocation.equals(g.o1)) {
                        targetLocation = g.o2;
                    } else if (playerLocation.equals(g.o2)) {
                        targetLocation = g.o1;
                    }

                    if (isLocked()) {
                        print(desc + Cardinals.get(playerLocation, targetLocation) + ".");
                    } else {
                        print("The gate is unlocked and the path " +
                                Cardinals.get(playerLocation, targetLocation) + " is clear.");
                    }
                    return null;
            };
            */
        } else if (trigger == MOVE) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {
                    Coordinate playerLocation = player.location();
                    Coordinate playerPrevLoc = player.getPrevLocation();
                    Coordinate targetLocation = null;

                    for (Coordinate neighbor : storedNeighbors) {
                        if (!neighbor.equals(playerPrevLoc)) {
                            targetLocation = neighbor;
                        }
                    }

                    print("The rope bridge provides access to the " +
                            Cardinals.get(playerLocation, targetLocation) + ".");
                    return null;
                }
            };
        }
        return event;
    }

}
