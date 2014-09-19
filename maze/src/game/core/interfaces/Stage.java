package game.core.interfaces;

import game.core.events.Event;
import game.core.inputs.Commands;

import java.util.List;
import java.util.PriorityQueue;

public interface Stage {
    //public void addActor(Actor actor);

    public void removeActor(Actor actor);

    public void cleanupActors();

    public Iterable<? extends Actor> getActors();

    public PriorityQueue<Event> getCurrentEvents();

    public PriorityQueue<Event> getCurrentEvents(Commands trigger);

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName);

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName);

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName, Stage secondStage);

    public boolean contains(String objectName);

    public boolean isBarren();

    public String getName();
}
