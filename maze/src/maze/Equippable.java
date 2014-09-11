package maze;

import java.util.HashMap;

public interface Equippable extends Portable {

    public void equipped();

    public void unequipped();

    public String type();

    public HashMap<References, Integer> getStats();

}
