package maze;

import java.util.HashMap;

public interface Equippable extends Portable {

    public PaperDoll.EquipSlots type();

    public HashMap<References, Integer> getStats();

}
