package game.objects.items;

import static game.core.events.Events.equip;
import static game.core.events.Events.unequip;
import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.positional.Coordinate;

public abstract class AbstractItemEquippable extends AbstractItemPortable implements Equippable {
    public AbstractItemEquippable() {
        super();
        this.maxSpawn = 1;
     }

    public AbstractItemEquippable(Coordinate c) {
        super(c);
    }

    @Override
    public Event interact(Commands trigger) {
        Event event = super.interact(trigger);
        if (event == null) {
               if (trigger == Commands.EQUIP) event = equip(this);
          else if (trigger == Commands.UNEQUIP) event = unequip(this);
        }
      return event;
    }
}
