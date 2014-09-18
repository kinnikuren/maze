package game.objects.items;

import game.core.events.Interacter;

public interface Portable extends Interacter {
    public String details();

    public int weight();

    //public void utilize(Player player);

}
