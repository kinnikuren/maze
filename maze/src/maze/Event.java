package maze;

import static maze.Priority.LOW;
import static util.Loggers.log;
import static util.Print.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import maze.Bestiary.Monster;

public abstract class Event implements Comparable<Event> {
    //comparable implementation is intentionally not consistent with equals
    private Interacter actor;
    private List<Monster> actors = new ArrayList<Monster>();
    //private Stage stage;
    private Priority priority;
    private boolean isSticky = false;

    public Event(Interacter actor, Priority priority) {
        this.actor = actor;
        this.priority = priority;
    }

    public Event(List<Monster> actors, Interacter actor, Priority priority) {
        this.actors = actors;
        this.actor = actor;
        this.priority = priority;
    }

    /* public Event(Interacter actor, Stage stage, Priority priority) {
        this.actor = actor;
        this.stage = stage;
        this.priority = priority;
    } */

    public Interacter actor() { return this.actor; }

    public Priority priority() { return this.priority; }

    //public Stage stage() { return this.stage; }

    public abstract Events.ResultMessage fire(Player player);

    public String toString() {
        return "Event: Actor " + actor + " Priority " + priority;
    }

    public int compareTo(Event other) { //intentionally not consistent with equals
        if (this == other) return 0;
        else {
            //int thisLevel = Priority.getLevel(this.priority);
            //int otherLevel = Priority.getLevel(other.priority());
          return this.priority.compareTo(other.priority());
        }
    }

    public void cleanup(Stage stage) {
        log("checking if " + actor + " is done...");
        if (actor.isDone(stage)) {
            log("removing actor " + actor, LOW);
            stage.removeActor(actor);

            if (actor instanceof Bestiary.PartyRep) {
                log("party rep found!");
                /*
                for (Interacter i : stage.getInteracters()) {
                    if (i instanceof Bestiary.Monster) {
                        stage.removeActor(i);
                    }
                }*/

                for (Iterator<Interacter> itr = stage.getInteracters().iterator(); itr.hasNext();) {
                    Interacter i = itr.next();
                    printnb(i + " ");
                    if (i instanceof Bestiary.Monster) {
                        log("it's a monster! kill it with fire!");
                        itr.remove();
                    }
                }
            }
        }
    }

    public void stick() { isSticky = true; }

    public void unStick() { isSticky = false; }

    public boolean isSticky() { return isSticky; }
}
