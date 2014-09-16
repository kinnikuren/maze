package maze;

import static maze.PaperDoll.EquipSlots.MAIN_HAND;

import java.util.HashMap;

import maze.PaperDoll.EquipSlots;

public abstract class AbstractItemWeapon extends AbstractItemEquippable {
    protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

    public AbstractItemWeapon() {
        super();
    };

    public AbstractItemWeapon(Coordinate c) {
        super(c);
    }

    @Override
    public EquipSlots type() {
        return MAIN_HAND;
    }

    @Override
    public HashMap<Attributes, Integer> getStats() {
        return this.stats;
    }
}
