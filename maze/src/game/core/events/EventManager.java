package game.core.events;

import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;

import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

import com.google.common.collect.*;

import util.View;
import static util.Utilities.NullFilter.*;
import static game.core.events.Priority.*;
import static util.Loggers.*;

public class EventManager {
    private View<Actor> interactions;
    private PriorityQueue<Event> currentEvents =
            new PriorityQueue<Event>(10, Collections.reverseOrder());

    public EventManager(Collection<? extends Actor> interactions) {
        Collection<? extends Actor> actors = Collections2.filter(interactions, NOT_NULL);
        this.interactions = new View<Actor>(actors);
    }

    public View<Actor> interactions() {
      return interactions;
    }

    /* public void addActor(Actor actor) {
        interactions.add(actor);
    }

    public Actor removeActor(Actor actor) {
        if (interactions.remove(actor)) {
          return actor;
        }
      return null;
    } */

    public PriorityQueue<Event> getCurrentEvents() { return currentEvents; }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
        for (Actor i : interactions) {
            Event event = i.interact(trigger);
            if (event != null) currentEvents.add(event);
        }
            log("Event manager on trigger " + trigger + " returns " + currentEvents, LOW);
      return currentEvents;
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName) {
        for (Actor i : interactions) {
            if (i.matches(objectName)) {
                Event event = i.interact(trigger);
                if (event != null) currentEvents.add(event);
            }
        }
            log("Event manager on trigger " + trigger + " on object "
                    + objectName + " returns " + currentEvents, LOW);
      return currentEvents;
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName) {
        boolean firstObjectExists = false;
        boolean secondObjectExists = false;
        Actor actor = null;
        Actor actee = null;
        for (Actor i: interactions) {
            if (i.matches(objectName)) {
                firstObjectExists = true;
                actor = i;
                //Event event = i.interact(trigger, prep)
                        //WIP
            }
            if (i.matches(secondObjectName)) {
                secondObjectExists = true;
                actee = i;
            }
        }

        if (firstObjectExists && secondObjectExists) {
            log("Both actors, " + actor.name() + " and " + actee.name() + " found!");
            Event event = actor.interact(trigger, prep, actee);
            if (event != null) currentEvents.add(event);
        }

        log("Event manager on trigger " + trigger + " on object "
                + objectName + " returns " + currentEvents, LOW);

        return currentEvents;
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName, Stage secondStage) {
        Actor actor = null;
        Actor actee = null;
        for (Actor i: interactions) {
            if (i.matches(objectName)) {
                actor = i;
            }
        }

        for (Actor i : secondStage.getActors()) {
            if (i.matches(secondObjectName)) {
                actee = i;
            }
        }

        if (actor != null && actee != null) {
            log("Both interacters, " + actor.name() + " and " + actee.name() + " found!");
            Event event = actor.interact(trigger, prep, actee, secondStage);
            if (event != null) currentEvents.add(event);
        }

        log("Event manager on trigger, " + trigger + ", on object, "
                + objectName + ", and on second object, " + secondObjectName +
                ", returns " + currentEvents, LOW);

        return currentEvents;
    }

    public boolean contains(String objectName) {
        boolean objectExists = false;
        for (Actor actor : interactions) {
                    log("looking for actors that match string '" + objectName + "'...");
          if (actor.matches(objectName)) {
            objectExists = true;
                    log("actor found!");
            break;
          }
        }
      return objectExists;
    }

    public boolean contains(Actor intr) {
        boolean actorExists = false;
        for (Actor actor : interactions) {
                    log("looking for actors that match " + actor);
          if (actor.equals(intr)) {
            actorExists = true;
                    log("actor found!");
            break;
          }
        }
      return actorExists;
    }
}
