package game.core.interfaces;

import game.objects.inventory.PaperDoll;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.player.util.Attributes;

import java.util.HashMap;

public interface Equippable extends Useable {

    public PaperDoll.EquipSlots type();

    public HashMap<Attributes, Integer> getStats();

}
