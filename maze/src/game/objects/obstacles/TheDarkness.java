package game.objects.obstacles;

import static game.objects.general.References.*;
import static util.Loggers.log;
import static util.Utilities.anyNullObjects;
import static util.Utilities.checkNullArgs;
import static game.core.events.Events.announce;
import static game.core.events.Priority.DEFAULT;
import static game.core.inputs.Commands.*;
import static util.Print.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.NullArgumentException;
import util.View;

import com.google.common.collect.ArrayListMultimap;

import game.core.maze.Maze;
import game.core.maze.MazeMap;
import game.core.maze.Maze.Room;
import game.core.positional.Coordinate;
import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.objects.units.AbstractUnit;

public class TheDarkness implements Actor {
    private ArrayListMultimap<String, Actor> darknessMap;
    private int maxSpawn = 1;
    private String rarity;
    private String name = "The Darkness";
    private String desc = "The darkness is impenetrable and shrouds everything. You can only return "
            + "whence you came.";
    private Set<Coordinate> storedNeighbors = new HashSet<Coordinate>();
    private Coordinate location;

    public TheDarkness(Maze maze, Maze.Room room) {
        checkNullArgs(maze, room);
        maze.map().checkLegalArgs(room.position);

        this.location = room.position;
        this.darknessMap = ArrayListMultimap.create();

        Set<String> keysetCopy = new HashSet<String>();
        keysetCopy.addAll(room.interactionMap().keySet());
        for (String key: keysetCopy) {
            log("The darkness is consuming " + key + "...");
            List<Actor> temp = room.interactionMap().get(key);
            log(temp.toString());
            this.darknessMap.putAll(key, temp);
            room.interactionMap().removeAll(key);
        }

        for (Coordinate coord : room.viewNeighbors()) {
            log("storing coord " + coord);
            storedNeighbors.add(coord);
        }

        for (Coordinate neighbor : storedNeighbors) {
            maze.map().deleteLink(room.position, neighbor);
        }
        room.addActor(this);
    }

    public void dispelDarkness(Maze maze) {
        //Object[] neighbors = this.storedNeighbors.toArray();
        if (storedNeighbors.size() == 0) {
            log("No coords stored in the darkness.");
        }

        for (Coordinate neighbor : storedNeighbors) {
            log("Adding link to " + neighbor);
            maze.map().link(location, neighbor);
        }

        storedNeighbors.clear();

        for (Actor actor : this.darknessMap.values()) {
            maze.getRoom(location).addActor(actor);
        }

        darknessMap.clear();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean matches(String name) {
        return matchRef(THE_DARKNESS, name);
    }

    @Override
    public boolean canInteract(AbstractUnit unit) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Event interact(Commands trigger) {
        Event event = null;
        if (trigger == MOVE) {
            event = announce(this, DEFAULT, "A chilling and complete darkness envelops the room.");
        } else if (trigger == DESCRIBE) {
            event = announce(this, DEFAULT, desc);;
        }
      return event;
    }

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee,
            Stage secondStage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDone(Stage stage) {
        return false;
    }

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

}
