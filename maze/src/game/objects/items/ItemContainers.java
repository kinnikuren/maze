package game.objects.items;

import game.core.positional.Coordinate;

public abstract class ItemContainers extends AbstractItem {

    public ItemContainers() {
    }

    public ItemContainers(Coordinate c) {
        super(c);
    }

}
