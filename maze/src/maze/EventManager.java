package maze;

import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

import static maze.Priority.*;
import static util.Loggers.*;

public class EventManager {
    private Collection<Interacter> interactions;
    private PriorityQueue<Event> currentEvents =
            new PriorityQueue<Event>(10, Collections.reverseOrder());

    public EventManager(Collection<Interacter> interactions) {
        this.interactions = interactions;
    }

    public Collection<Interacter> interactions() {
      return interactions;
    }

    public void setInteractions(Collection<Interacter> interactions) {
        this.interactions = interactions;
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

    public boolean contains(String objectName) {
        boolean objectExists = false;
        if (interactions.size() > 0) {
            for (Interacter actor : interactions) {
              if (actor.matches(objectName)) {
                objectExists = true;
              }
            }
        }
      return objectExists;
    }
}
