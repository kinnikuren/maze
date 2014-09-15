package maze;

import static maze.Events.*;
import static maze.Commands.*;

public abstract class AbstractItemUseable extends AbstractItemTrinket
implements Useable {
    public AbstractItemUseable() { }

    public AbstractItemUseable(Coordinate c) {
        super(c);
    }

    @Override
    public Event interact(Commands trigger) {
        Event event = super.interact(trigger);
        if (event == null) {
          if (trigger == Commands.USE) event = use(this);
        }
      return event;
    }

    @Override public abstract void usedBy(Player player);
}
