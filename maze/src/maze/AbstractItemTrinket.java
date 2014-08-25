package maze;

import static maze.Events.*;
import static maze.Commands.PICKUP;

public abstract class AbstractItemTrinket extends AbstractItemPortable
implements Portable {
    private boolean pickedUp = false;
    public AbstractItemTrinket() { }

    public AbstractItemTrinket(Coordinate c) {
        super(c);
    }

    @Override public abstract boolean matches(String name);
    @Override
    public boolean canInteract(AbstractUnit unit) {
      return false;
    }

    /* @Override
    public Event interact(Commands trigger, Stage stage) {
      return trigger == Commands.PICKUP ? pickup(this) : null;
    } */
    @Override
    public boolean isDone(Stage stage) {
        if (stage instanceof AbstractRoom) return pickedUp;
        else return !pickedUp;
    }
    @Override
    public void pickedUp() { pickedUp = true; }
    @Override
    public void dropped() { pickedUp = false; }
    @Override public abstract String details();
    @Override public abstract int weight();
}
