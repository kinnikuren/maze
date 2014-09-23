package game.objects.obstacles;

import static game.core.inputs.Commands.APPROACH;
import static game.core.inputs.Commands.DESCRIBE;
import static game.core.inputs.Commands.LEAVE;
import static game.core.inputs.Commands.MOVE;
import static game.objects.general.References.CABLE;
import static game.objects.general.References.matchRef;
import static game.player.util.Attributes.DEX;
import static util.Loggers.log;
import static util.Print.print;
import game.core.events.Event;
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
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cable extends AbstractItemFixture {
    private static Random rand = new Random();

    protected References ref = CABLE;
    protected String desc = null;
    protected String name = "Cable";
    protected List<Coordinate> storedNeighbors = new ArrayList<Coordinate>();
    protected Coordinate cableLocation;
    protected Coordinate targetLocation;
    protected boolean isEnabled = false;

    protected Cable(AbstractRoom room) {
        super();
        this.desc = "It's a long, metal cable that spans the room across a deep chasm.";
    }


    public static Coordinate addCable(Maze maze) {

        List<Coordinate> coordinateSet = new ArrayList<Coordinate>();

        for (Coordinate coord : maze.map().viewTwoLinkNodes()) {
            coordinateSet.add(coord);
        }

        Coordinate spawnPoint;

        Coordinate[] spawnPoints =
                coordinateSet.toArray(new Coordinate[coordinateSet.size()]);

        spawnPoint = spawnPoints[rand.nextInt(spawnPoints.length)];

        Room room = maze.getRoom(spawnPoint);

        Cable cable = new Cable(room);

        for (Coordinate coord : room.viewNeighbors()) {
            log("storing coord " + coord);
            cable.storedNeighbors.add(coord);
        }

        Coordinate[] neighbors = cable.storedNeighbors.toArray(new Coordinate[cable.storedNeighbors.size()]);

        log("spawning cable at " + spawnPoint);
        cable.cableLocation = spawnPoint;
        room.addActor(cable);
        return spawnPoint;
    }

    public void enable() {
        isEnabled = true;
        this.desc = "It's a long, metal cable with a rubber chicken and pulley attachment. You "
                + "could use it to slide over the chasm.";
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
                    print("The cable stretches to a door to the " +
                            Cardinals.get(playerLocation, targetLocation) + ".");
                    print("Do you want to approach the cable?");
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
                        player.maze().map().link(cableLocation, neighbor);
                    }

                    return null;
                }
            };
        } else if (trigger == APPROACH) {
            event = new Event(this, Priority.LOW) {
                @Override public ResultMessage fire(Player player) {

                    if (!isEnabled) {

                        print("You approach the cable. It looks really sharp. There's no way you "
                                + "could get across with your hands. If only you had some device "
                                + "you could hook onto it...");

                    } else {

                        print("You approach the improvised rubber chicken zipline.");
                        print("A) Zipline! Whooooo!!!");
                        print("B) Chicken out.");

                        String input = MultipleChoiceInputHandler.run(2);
                        log("input entered was " + input);

                        if (input.equals("A")) {

                            print("You zoom across the chasm!");

                            //restore link
                            player.maze().map().link(player.location(), targetLocation);

                            Cardinals direction = Cardinals.get(player.location(), targetLocation);

                            GameInputHandler.run("move " + direction, player, player.maze());

                        } else if (input.equals("B")) {
                            print("The rubber chicken seems to be shaking his head.");
                        }

                    }
                    return null;
                }
            };
        }
        return event;
    }

}
