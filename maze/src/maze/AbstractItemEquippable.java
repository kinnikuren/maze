package maze;

import static maze.Events.equip;
import static maze.Events.unequip;

public abstract class AbstractItemEquippable extends AbstractItemPortable implements Equippable {
    private boolean equipped = false;

    public AbstractItemEquippable() {
        super();
     }

    public AbstractItemEquippable(Coordinate c) {
        super(c);
    }

    @Override
    public void equipped() { equipped = true; }
    @Override
    public void unequipped() { equipped = false; }

    @Override
    public Event interact(Commands trigger) {
        Event event = super.interact(trigger);
        if (event == null) {
            if (trigger == Commands.EQUIP) return equip(this);
            else if (trigger == Commands.UNEQUIP) return unequip(this);
            else return event;
        } else return event;
    }
}
