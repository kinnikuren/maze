package game.objects.items;

import static game.objects.inventory.PaperDoll.EquipSlots.MAIN_HAND;
import game.core.positional.Coordinate;
import game.objects.inventory.PaperDoll;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.player.util.Attributes;

import java.util.HashMap;

public abstract class AbstractItemWeapon extends AbstractItemEquippable {

    public AbstractItemWeapon() {
        super();
    };

    @Override
    public EquipSlots type() {
        return MAIN_HAND;
    }

    @Override
    public HashMap<Attributes, Integer> getStats() {
        return this.stats;
    }
}
