package maze;

import static maze.Priority.LOW;
import static util.Loggers.log;
import java.util.ArrayList;
import java.util.List;

import maze.Bestiary.Monster;

public abstract class Event implements Comparable<Event> {

    //comparable implementation is intentionally not consistent with equals
    private Interacter actor;
    private List<Monster> monsters = new ArrayList<Monster>();
    //private Stage stage;
    private Priority priority;
    private boolean isSticky = false;
    public boolean cleanupActor = false;

    public Event(Interacter actor, Priority priority) {
        this.actor = actor;
        this.priority = priority;
    }

    public Event(List<Monster> monsters, Interacter actor, Priority priority) {
        this.monsters = monsters;
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

    //public void setCleanup(Stage stage) {

    public void cleanup(Stage stage) {
        log("checking if " + actor + " is done...");
        if (actor.isDone(stage)) {
            log("removing actor " + actor, LOW);
            stage.removeActor(actor);
        }

        for (Interacter m : monsters) {
            if (m.isDone(stage)) {
                log("removing actor from actors " + actor, LOW);
                stage.removeActor(m);
            }
        }
    }

    public void stick() { isSticky = true; }

    public void unStick() { isSticky = false; }

    public boolean isSticky() { return isSticky; }
}
