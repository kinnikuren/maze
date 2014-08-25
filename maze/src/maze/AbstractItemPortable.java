package maze;

import static maze.Commands.CONSUME;
import static maze.Commands.PICKUP;
import static maze.Commands.DROP;
import static maze.Events.consume;
import static maze.Events.pickup;
import static maze.Events.drop;
import static util.Print.print;
import static util.Utilities.checkNullArg;

public abstract class AbstractItemPortable extends AbstractItem implements Portable {
    Coordinate location;
    String name; //populated via getClass by default
    boolean canCarry = true;

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
        else return null;
    }
}