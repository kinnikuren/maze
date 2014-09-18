package game.objects.items;

import game.core.positional.Coordinate;

public abstract class AbstractItemArmor extends AbstractItemEquippable {

    public AbstractItemArmor() {
        super();
    }

    public AbstractItemArmor(Coordinate c) {
        super(c);
    }

}
