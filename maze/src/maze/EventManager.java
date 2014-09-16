package maze;

import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import com.google.common.collect.*;

import util.View;
import static util.Utilities.NullFilter.*;
import static maze.Priority.*;
import static util.Loggers.*;

public class EventManager {
    private View<Interacter> interactions;
    private PriorityQueue<Event> currentEvents =
            new PriorityQueue<Event>(10, Collections.reverseOrder());

    public EventManager(Collection<? extends Interacter> interactions) {
        Collection<? extends Interacter> actors = Collections2.filter(interactions, NOT_NULL);
        this.interactions = new View<Interacter>(actors);
    }

    public View<Interacter> interactions() {
      return interactions;
    }

    /* public void addActor(Interacter actor) {
        interactions.add(actor);
    }

    public Interacter removeActor(Interacter actor) {
        if (interactions.remove(actor)) {
          return actor;
        }
      return null;
    } */

    public PriorityQueue<Event> getCurrentEvents() { return currentEvents; }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
        for (Interacter i : interactions) {
            Event event = i.interact(trigger);
            if (event != null) currentEvents.add(event);
        }
            log("Event manager on trigger " + trigger + " returns " + currentEvents, LOW);
      return currentEvents;
    }

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName) {
        for (Interacter i : interactions) {
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
        Interacter interacter = null;
        Interacter interactee = null;
        for (Interacter i: interactions) {
            if (i.matches(objectName)) {
                firstObjectExists = true;
                interacter = i;
                //Event event = i.interact(trigger, prep)
                        //WIP
            }
            if (i.matches(secondObjectName)) {
                secondObjectExists = true;
                interactee = i;
            }
        }

        if (firstObjectExists && secondObjectExists) {
            log("Both interacters, " + interacter.name() + " and " + interactee.name() + " found!");
            Event event = interacter.interact(trigger, prep, interactee);
            if (event != null) currentEvents.add(event);
        }

        log("Event manager on trigger " + trigger + " on object "
                + objectName + " returns " + currentEvents, LOW);

        return currentEvents;
    }

    public boolean contains(String objectName) {
        boolean objectExists = false;
        for (Interacter actor : interactions) {
                    log("looking for actors that match string '" + objectName + "'...");
          if (actor.matches(objectName)) {
            objectExists = true;
                    log("actor found!");
            break;
          }
        }
      return objectExists;
    }

    public boolean contains(Interacter intr) {
        boolean actorExists = false;
        for (Interacter actor : interactions) {
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
