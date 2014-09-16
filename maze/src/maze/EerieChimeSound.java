package maze;

import static maze.Commands.*;
import static maze.Events.*;
import static maze.Priority.*;

public class EerieChimeSound implements Interacter {
    int maxSpawn;
    String rarity;

    EerieChimeSound() { }

    @Override
    public String name() { return "Eerie Chime Sound"; }
    @Override
    public boolean matches(String name) {
      return name().equalsIgnoreCase(name);
    }
    @Override
    public boolean canInteract(AbstractUnit unit) { return true; }
    @Override
    public Event interact(Commands trigger) {
      return trigger == MOVE ? announce(this, MAX, "You hear an eerie "
              + "chiming sound in the distance.") : null;
    }

    @Override
    public Event interact(Commands trigger, String prep, Interacter interactee) { return null; }

    @Override
    public boolean isDone(Stage stage) { return true; }
    @Override
    public String toString() { return "EerieChimeSound"; }

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    public String getRarity() { return rarity; }

    public void setRarity(String rarity) { this.rarity = rarity; }
}
