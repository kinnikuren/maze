package maze;

import static maze.PaperDoll.EquipSlots.MAIN_HAND;

import java.util.HashMap;

import maze.PaperDoll.EquipSlots;

public abstract class AbstractItemWeapon extends AbstractItemEquippable {
    protected HashMap<References, Integer> stats = new HashMap<References, Integer>();

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
    public HashMap<References, Integer> getStats() {
        return this.stats;
    }
}
