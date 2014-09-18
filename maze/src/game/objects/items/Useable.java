package game.objects.items;

import game.objects.units.Player;


public interface Useable extends Portable {
    public void usedBy(Player player); //uses item
}
