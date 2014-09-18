package game.objects.items;

import static game.core.events.Events.*;
import static game.core.inputs.Commands.PICKUP;
import game.core.positional.Coordinate;
import game.objects.units.AbstractUnit;

public abstract class AbstractItemTrinket extends AbstractItemPortable {
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
    @Override public abstract String details();
    @Override public abstract int weight();
}
