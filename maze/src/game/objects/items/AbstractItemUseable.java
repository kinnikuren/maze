package game.objects.items;

import static game.core.events.Events.*;
import static game.core.inputs.Commands.*;
import game.core.events.Event;
import game.core.events.Events;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.interfaces.Useable;
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

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage) {
        if (trigger == USE) {
            if (prep.equals("on")) {
                return Events.useOn(this, interactee, secondStage);
            } else return null;
        } else {
            return null;
        }
    }

    @Override public abstract void usedBy(Player player);

    @Override public boolean usedBy(Player player, Actor target, Stage targetStage) { return false; }
}
