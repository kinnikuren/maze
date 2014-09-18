package game.core.events;

import game.core.inputs.Commands;

import java.util.List;
import java.util.PriorityQueue;

public interface Stage {
    //public void addActor(Interacter actor);

    public void removeActor(Interacter actor);

    public void cleanupActors();

    public PriorityQueue<Event> getCurrentEvents();

    public PriorityQueue<Event> getCurrentEvents(Commands trigger);

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName);

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName);

    public boolean contains(String objectName);

    public boolean isBarren();

    public String getName();
}
