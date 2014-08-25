package maze;

import java.util.*;

import static util.Print.*;

public abstract class AbstractRoom implements Stage {
    public static AbstractRoom testRoom(Coordinate c) {
        return new AbstractRoom(c) {
            @Override
            public void populateRoom() { }
        };
    }

    public final Coordinate position;
    ArrayList<Interacter> contents = new ArrayList<Interacter>();
    ArrayList<Interacter> interactions = new ArrayList<Interacter>();
    EventManager manager;

    public AbstractRoom(Coordinate c) {
        this.position = c;
        this.manager = new EventManager(interactions);
    }

    public String toString() { return "Room => " + position; }

    public void describeRoom() {
        print ("You are in an unremarkable, nondescript room.");
        if(contents.size() == 0) { print("There is nothing in this room."); }
        else {
          print("There are things in the room!");
          print ("Contents of this room::");
          for(Interacter i : contents) {
            print(i.name());
          }
        }
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
        interactions.add(actor);
    }

    @Override
    public void removeActor(Interacter actor) {
        if (actor instanceof AbstractUnit || actor instanceof AbstractItem) {
            contents.remove(actor);
        }
        interactions.remove(actor);
    }

    @Override
    public void cleanupActors() {
        for (Interacter actor : interactions) {
          if (actor.isDone(this)) removeActor(actor);
        }
    }

    public PriorityQueue<Event> getCurrentEvents() {
      return manager.getCurrentEvents();
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
      return manager.getCurrentEvents(trigger);
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName) {
      return manager.getCurrentEvents(trigger, objectName);
    }

    public boolean contains(String objectName) {
      return manager.contains(objectName);
    }

    @Override
    public boolean isBarren() {
      return interactions.size() == 0 ? true : false;
    }

    @Override
    public String getName() { return "the room"; }

    public void addToRoom() { }

    public ArrayList<Interacter> contents() { return contents; }

    public ArrayList<Interacter> interactions() { return interactions; }
}
