package util;

import static game.core.events.Priority.LOW;
import static util.Loggers.log;
import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Priority;
import game.core.events.Theatres;
import game.core.interfaces.Actor;
import game.core.interfaces.Consumable;
import game.core.interfaces.Fighter;
import game.core.interfaces.Stage;
import game.core.interfaces.Useable;
import game.objects.units.Player;
import game.objects.units.Bestiary.Skeleton;
import game.objects.items.Consumables.HealingPotion;

import java.util.ArrayList;
import java.util.List;

public abstract class EventP<A extends Actor> implements Comparable<EventP<A>> {

    //comparable implementation is intentionally not consistent with equals
    private List<A> actors;
    //private Stage stage;
    private Priority priority;
    private boolean isSticky = false;
    public boolean cleanupActor = false;

    public EventP(A actor, Priority priority) {
        List<A> temp = new ArrayList<A>();
        temp.add(actor);
        this.actors = temp;
        this.priority = priority;
    }

    public EventP(List<A> actors, Priority priority) {
        this.actors = actors;
        this.priority = priority;
    }

    public List<A> actors() { return actors; }

    public void addActor(A actor) {
        actors.add(actor);
    }

    public Priority priority() { return this.priority; }

    public abstract Theatres.ResultMessage fire(Player player);

    public String toString() {
        return "Event: Actors " + actors + " Priority " + priority;
    }

    //public boolean absorb(Event<A>)

    @SuppressWarnings("rawtypes")
    public int compareTo(EventP other) { //intentionally not consistent with equals
        if (this == other) return 0;
        else {
            //int thisLevel = Priority.getLevel(this.priority);
            //int otherLevel = Priority.getLevel(other.priority());
          return this.priority.compareTo(other.priority());
        }
    }

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

    /* public static void main(String[] args) {
        Skeleton sk1 = new Skeleton();
        EventP<Fighter> ep1 = new EventP<Fighter>(sk1, LOW);

        HealingPotion hp1 = new HealingPotion();
        EventP<Consumable> ep2 = new EventP<Consumable>(hp1, Priority.HIGH);

        int x = ep1.compareTo(ep2);
        System.out.println("comparison: " + x);
    } */
}
