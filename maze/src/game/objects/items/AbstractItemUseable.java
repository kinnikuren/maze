package game.objects.items;

import static game.core.events.Events.*;
import static game.core.inputs.Commands.*;
import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.positional.Coordinate;
import game.objects.units.Player;

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
