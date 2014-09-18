package game.objects.items;

import static game.core.events.Events.consume;
import static game.core.events.Events.drop;
import static game.core.events.Events.pickup;
import static game.core.inputs.Commands.CONSUME;
import static game.core.inputs.Commands.DROP;
import static game.core.inputs.Commands.PICKUP;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import game.core.positional.Coordinate;

public abstract class AbstractItem {
    Coordinate location;
    String name; //populated via getClass by default
    boolean canCarry = true;
    int maxSpawn = 1;
    String rarity="common";

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

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    public String getRarity() { return rarity; }

    public void setRarity(String rarity) { this.rarity = rarity; }

}
