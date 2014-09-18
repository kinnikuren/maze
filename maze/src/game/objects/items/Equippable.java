package game.objects.items;

import game.objects.inventory.PaperDoll;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.player.util.Attributes;

import java.util.HashMap;

public interface Equippable extends Portable {

    public PaperDoll.EquipSlots type();

    public HashMap<Attributes, Integer> getStats();

}