package game.objects.items;

import static game.objects.inventory.PaperDoll.EquipSlots.MAIN_HAND;
import game.core.positional.Coordinate;
import game.objects.inventory.PaperDoll;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.player.util.Attributes;

import java.util.HashMap;

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
