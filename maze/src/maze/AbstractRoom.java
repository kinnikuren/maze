package maze;

import java.util.*;

import util.GrammarGuy;

import com.google.common.collect.ArrayListMultimap;

import static util.Print.*;
import static util.Loggers.*;
import static maze.Priority.*;

public abstract class AbstractRoom implements Stage {

    public static AbstractRoom testRoom(Coordinate c) {
        return new AbstractRoom(c) {
            @Override
            public void populateRoom() { }
        };
    }


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

    public void visitedBy(Player player) {
        isVisited = true;
        log("This room has been visited by you.",LOW);
    }

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

    /* public void populateRoom(Maze linkedMaze) {
        int i = new Random().nextInt(25);
        if (!position.equals(linkedMaze.getCenter())) {
            if(i % 9 == 0) {
                Skeleton sk = new Skeleton(position);
                contents.add(sk);
                interactions.add(sk);
                print("Skeleton added to Room " + position);
            }
            if(i == 14) {
                Idol idol = new Idol(position);
                contents.add(idol);
                interactions.add(idol);
                print("Idol added to Room " + position);
            }
        }
        else {
            Rock r = new Rock(position);
            contents.add(r);
            interactions.add(r);
            print("Rock added to Room " + position);
        }
    } */

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

    public Collection<Interacter> interactions() {
      return interactionMap.values();
    }

    //public ArrayListMultiMap interactionMap
}
