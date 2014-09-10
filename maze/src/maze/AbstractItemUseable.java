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
            return trigger == Commands.USE ? use(this) : null;
        }
        else return event;
    }

    @Override public abstract void usedBy(Player player);
}
