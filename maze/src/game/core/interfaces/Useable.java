package game.core.interfaces;

import game.objects.units.Player;


public interface Useable extends Portable {
    public void usedBy(Player player); //uses item

    public boolean usedBy(Player player, Actor target, Stage targetStage);
}
