package game.core.maze;

import game.core.events.Event;
import game.core.events.EventManager;
import game.core.events.Interacter;
import game.core.events.Stage;
import game.core.inputs.Commands;
import game.core.inputs.GrammarGuy;
import game.core.positional.Coordinate;
import game.objects.items.AbstractItem;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;

import static util.Print.*;
import static util.Loggers.*;
import static game.core.events.Priority.*;

public abstract class AbstractRoom implements Stage {

    public final Coordinate position;
    List<Interacter> contents;
    ArrayListMultimap<String, Interacter> interactionMap;
    EventManager manager;
    boolean isVisited = false;

    public AbstractRoom(Coordinate c) {
        this.position = c;
        this.contents = new ArrayList<Interacter>();
        this.interactionMap = ArrayListMultimap.create();
        this.manager = new EventManager(interactionMap.values());
    }

    public String toString() { return "Room at " + position; }

    public void setVisitedTrue(Player player) {
        isVisited = true;
        log("This room has been visited by " + player.name(), DEFAULT);
    }

    public boolean hasBeenVisited() { return isVisited; }

    public void describeRoom() {
        int count = 0;
        //print ("You are in an unremarkable, nondescript room.");
        List<String> list = new ArrayList<String>();
        if(contents.size() == 0) { print("There is nothing in this room."); }
        else {
          String roomContents = "CONTENTS OF THIS ROOM:";
          for (String thing : interactionMap.keySet()) {
              count = interactionMap.get(thing).size();
              String addDescription= thing + " (" + count + ")";
              //roomContents += addDescription;
              list.add(addDescription);
          }
          print(roomContents);
          wordWrapPrint(GrammarGuy.oxfordCommify(list));
        }
    }

    public int howManyOf(String name) {
      return interactionMap.get(name).size();
    }

    public abstract void populateRoom();

    public void addActor(Interacter actor) {
        if (actor instanceof AbstractUnit || actor instanceof AbstractItem) {
          contents.add(actor);
        }
        interactionMap.put(actor.name(), actor);
    }

    @Override
    public void removeActor(Interacter actor) {
        if (actor instanceof AbstractUnit || actor instanceof AbstractItem) {
          contents.remove(actor);
        }
        interactionMap.remove(actor.name(), actor);
    }
    @Override
    public void cleanupActors() {
        for (Interacter actor : interactionMap.values()) {
          if (actor.isDone(this)) removeActor(actor);
        }
    }
    @Override
    public PriorityQueue<Event> getCurrentEvents() {
      return manager.getCurrentEvents();
    }
    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
      return manager.getCurrentEvents(trigger);
    }
    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName) {
      return manager.getCurrentEvents(trigger, objectName);
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName) {
        return manager.getCurrentEvents(trigger, objectName, prep, secondObjectName);
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName, Stage secondStage) {
        return manager.getCurrentEvents(trigger, objectName, prep, secondObjectName, secondStage);
    }

    @Override
    public boolean contains(String objectName) {
      return manager.contains(objectName);
    }
    @Override
    public boolean isBarren() {
      return (interactionMap.values().size() == 0);
    }
    @Override
    public String getName() { return "the room"; }

    public void addToRoom() { }

    public List<Interacter> contents() { return contents; }

    public Iterable<Interacter> getActors() { return contents; }

    public Collection<Interacter> interactions() {
      return interactionMap.values();
    }

    public static AbstractRoom testRoom(Coordinate c) {
        return new AbstractRoom(c) {
            @Override
            public void populateRoom() { }
        };
    }
}