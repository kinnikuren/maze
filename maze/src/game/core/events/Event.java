package game.core.events;

import static game.core.events.Priority.LOW;
import static util.Loggers.log;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.objects.units.Bestiary;
import game.objects.units.Player;
import game.objects.units.Bestiary.Monster;

import java.util.ArrayList;
import java.util.List;

public abstract class Event implements Comparable<Event> {

    //comparable implementation is intentionally not consistent with equals
    private List<? extends Actor> actors;
    //private Stage stage;
    private Priority priority;
    private boolean isSticky = false;
    public boolean cleanupActor = false;

    public Event(Actor actor, Priority priority) {
        List<Actor> temp = new ArrayList<Actor>();
        temp.add(actor);
        this.actors = temp;
        this.priority = priority;
    }

    public Event(List<? extends Actor> actors, Priority priority) {
        this.actors = actors;
        this.priority = priority;
    }

    /* public Event(Actor actor, Stage stage, Priority priority) {
        this.actor = actor;
        this.stage = stage;
        this.priority = priority;
    } */

    public Priority priority() { return this.priority; }

    //public Stage stage() { return this.stage; }

    public abstract Theatres.ResultMessage fire(Player player);

    public String toString() {
        return "Event: Actors " + actors + " Priority " + priority;
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
        log("checking if actors are done...");

        for (Actor actor : actors) {
            if (actor.isDone(stage)) {
                log("removing actor " + actor + " from actors ", LOW);
                stage.removeActor(actor);
            }
        }
    }

    public void stick() { isSticky = true; }

    public void unStick() { isSticky = false; }

    public boolean isSticky() { return isSticky; }
}
