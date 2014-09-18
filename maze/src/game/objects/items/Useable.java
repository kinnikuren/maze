package game.objects.items;

import game.objects.units.Player;
import game.core.events.Interacter;
import game.core.events.Stage;


public interface Useable extends Portable {
    public void usedBy(Player player); //uses item

    public boolean usedBy(Player player, Interacter target, Stage targetStage);
}
