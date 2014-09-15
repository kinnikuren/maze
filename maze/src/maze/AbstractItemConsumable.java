package maze;

import static maze.Events.*;
import static maze.Commands.*;

public abstract class AbstractItemConsumable extends AbstractItemTrinket
implements Consumable {
    public AbstractItemConsumable() { }

    public AbstractItemConsumable(Coordinate c) {
        super(c);
    }

    @Override
    public Event interact(Commands trigger) {
        Event event = super.interact(trigger);
        if (event == null) {
          if (trigger == Commands.CONSUME) event = consume(this);
        }
      return event;
    }

    @Override public abstract void consumedBy(Player player);
}
