package game.objects.items;

import static game.core.events.Events.*;
import static game.core.inputs.Commands.*;
import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.interfaces.Consumable;
import game.core.positional.Coordinate;
import game.objects.units.Player;

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
