package game.objects.items;

import game.objects.units.Player;

public interface Consumable extends Portable {
    public void consumedBy(Player player); //consumes item
}
