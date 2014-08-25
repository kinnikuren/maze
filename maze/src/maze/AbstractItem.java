package maze;

import static maze.Commands.CONSUME;
import static maze.Commands.PICKUP;
import static maze.Commands.DROP;
import static maze.Events.consume;
import static maze.Events.pickup;
import static maze.Events.drop;
import static util.Print.print;
import static util.Utilities.checkNullArg;

public abstract class AbstractItem {
    Coordinate location;
    String name; //populated via getClass by default
    boolean canCarry = true;

    public AbstractItem() {
        this.location = null;
        this.name = this.getClass().getSimpleName();
    }

    public AbstractItem(Coordinate c) {
        checkNullArg(c);
        this.location = c;
        this.name = this.getClass().getSimpleName();
    }

    public Coordinate getLocation() { return location; }

    public String name() { return name; }

    public void setLocation(Coordinate c) { this.location = c; }

    public void setName(String newName) { this.name = newName; }

    public void printLocation() { print("Current Position: " + location); }
    @Override
    public String toString() { return this.name(); }

}
