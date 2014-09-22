package game.objects.obstacles;

import static game.objects.general.References.*;
import static util.Loggers.log;
import static util.Utilities.anyNullObjects;
import static game.core.events.Events.announce;
import static game.core.events.Priority.DEFAULT;
import static game.core.inputs.Commands.*;
import static util.Print.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.View;

import com.google.common.collect.ArrayListMultimap;

import game.core.maze.AbstractRoom;
import game.core.maze.Maze;
import game.core.maze.MazeMap;
import game.core.maze.Maze.Room;
import game.core.positional.Coordinate;
import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.objects.items.Useables.Key;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public class TheDarkness implements Actor {
    private ArrayListMultimap<String, Actor> darknessMap;
    private int maxSpawn = 1;
    private String rarity;
    private String name = "The Darkness";
    private String desc = "The darkness is impenetrable and shrouds everything. You can only return "
            + "whence you came.";
    private List<Coordinate> storedNeighbors = new ArrayList<Coordinate>();
    private Coordinate location;

    public TheDarkness(AbstractRoom room) {
        this.location = room.position;
        if (room.getMap() != null) {
            this.darknessMap = ArrayListMultimap.create();

            Set<String> keysetCopy = new HashSet<String>();
            keysetCopy.addAll(room.getMap().keySet());
            for (String key: keysetCopy) {
                log("The darkness is consuming " + key + "...");
                List<Actor> temp = room.getMap().get(key);
                log(temp.toString());
                this.darknessMap.putAll(key, temp);
                room.getMap().removeAll(key);
            }

        }
    }

    public static boolean addDarkness(Maze maze, Coordinate c) {
        maze.map().checkLegalArgs(c);

        Room room = maze.getRoom(c);

        if (anyNullObjects(room)) return false;

        TheDarkness theDarkness = new TheDarkness(room);

        for (Coordinate coord : room.viewNeighbors()) {
            log("storing coord " + coord);
            theDarkness.storedNeighbors.add(coord);
        }

        Object[] neighbors = theDarkness.storedNeighbors.toArray();

        for (int i=0;i<neighbors.length;i++) {
            Coordinate neighbor = (Coordinate)neighbors[i];
            maze.map().deleteLink(c, neighbor);
        }

        room.addActor(theDarkness);
        return true;
    }

    public void dispelDarkness(Maze maze) {
        Object[] neighbors = this.storedNeighbors.toArray();
        if (neighbors.length == 0) {
            log("No coords stored in the darkness.");
        }

        for (int i=0;i<neighbors.length;i++) {
            Coordinate neighbor = (Coordinate)neighbors[i];
            log("Adding link to " + neighbor);
            maze.map().link(this.location, neighbor);
        }

        for (Actor a : this.darknessMap.values()) {
            maze.getRoom(this.location).addActor(a);
        }
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
