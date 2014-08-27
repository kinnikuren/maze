package maze;

import static maze.Priority.LOW;
import static util.Loggers.log;

public abstract class Event implements Comparable<Event> {
    //comparable implementation is intentionally not consistent with equals
    private Interacter actor;
    //private Stage stage;
    private Priority priority;
    private boolean isSticky;

    public Event(Interacter actor, Priority priority) {
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

    public abstract void fire(Player player);

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
        if (actor.isDone(stage)) {
            log("removing actor " + actor, LOW);
            stage.removeActor(actor);
        }
    }

    public void stick() { isSticky = true; }

    public void unStick() { isSticky = false; }

    public boolean isSticky() { return isSticky; }
}
