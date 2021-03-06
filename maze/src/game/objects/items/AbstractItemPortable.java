package game.objects.items;

import static game.core.events.Events.consume;
import static game.core.events.Events.describe;
import static game.core.events.Events.drop;
import static game.core.events.Events.pickup;
import static game.core.events.Events.use;
import static game.core.inputs.Commands.*;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import game.core.events.Event;
import game.core.events.Events;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Portable;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public abstract class AbstractItemPortable extends AbstractItem implements Portable {
    Coordinate location;
    String name; //populated via getClass by default
    boolean canCarry = true;

    public AbstractItemPortable() {
        super();
    }

    public Coordinate getLocation() { return location; }

    public String name() { return name; }

    public void setLocation(Coordinate c) { this.location = c; }

    public void setName(String newName) { this.name = newName; }

    public void printLocation() { print("Current Position: " + location); }
    @Override
    public String toString() { return this.name(); }

    @Override
    public Event interact(Commands trigger) {
        Event event = null;
             if (trigger == DROP) event = drop(this);
        else if (trigger == PICKUP) event = pickup(this);
        else if (trigger == DESCRIBE) event = describe(this);
      return event;
    }

    public Event interact(Commands trigger, String prep, Actor interactee) {
        Event event = null;
        if (trigger == COMBINE && prep.equals("with")) {
          event = Events.combine(this, (Portable)interactee);
        }
      return event;
    }

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage) {
      return null;
    }

    @Override public abstract boolean matches(String name);
    @Override
    public boolean canInteract(AbstractUnit unit) {
        return false;
    }
    @Override
    public boolean isDone(Stage stage) {
        return false;
    }
}
