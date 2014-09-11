package maze;

import static maze.Commands.*;
import static maze.Events.consume;
import static maze.Events.pickup;
import static maze.Events.drop;
import static maze.Events.use;
import static maze.Events.describe;
import static util.Print.print;
import static util.Utilities.checkNullArg;

public abstract class AbstractItemPortable extends AbstractItem implements Portable {
    Coordinate location;
    String name; //populated via getClass by default
    boolean canCarry = true;
    private boolean pickedUp = false;

    public AbstractItemPortable() {
        super();
    }

    public AbstractItemPortable(Coordinate c) {
        super(c);
    }

    public Coordinate getLocation() { return location; }

    public String name() { return name; }

    public void setLocation(Coordinate c) { this.location = c; }

    public void setName(String newName) { this.name = newName; }

    public void printLocation() { print("Current Position: " + location); }
    @Override
    public String toString() { return this.name(); }

    public Event interact(Commands trigger) {
        if (trigger == DROP) return drop(this);
        else if (trigger == PICKUP) return pickup(this);
        else if (trigger == DESCRIBE) return describe(this);
        else return null;
    }

    @Override public abstract boolean matches(String name);
    @Override
    public boolean canInteract(AbstractUnit unit) {
        return false;
    }
    @Override
    public boolean isDone(Stage stage) {
        return pickedUp;
    }
    @Override
    public void pickedUp() { pickedUp = true; }
    @Override
    public void dropped() { pickedUp = false; }
}