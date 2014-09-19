package game.core.interfaces;

import game.objects.units.Player;

public interface Consumable extends Portable {
    public void consumedBy(Player player); //consumes item
}
