package maze;

import static util.Utilities.*;

public enum Cardinals {
    NORTH("north", "n", 0, 1),
    SOUTH("south", "s", 0, -1),
    EAST("east", "e", 1, 0),
    WEST("west", "w", -1, 0),
    NORTHEAST("northeast", "ne", 1, 1),
    NORTHWEST("northwest", "nw", -1, 1),
    SOUTHEAST("southeast", "se", 1, -1),
    SOUTHWEST("southwest", "sw", -1, -1);

    private final String name;
    private final String shortcut;
    private final int xSign;
    private final int ySign;

    Cardinals(String name, String shortcut, int xSign, int ySign) {
        this.name = name;
        this.shortcut = shortcut;
        this.xSign = xSign;
        this.ySign = ySign;
    }

    public String toString() { return name; }

    public static Cardinals get(String str) {
        for (Cardinals cardinal : Cardinals.values()) {
          if (str.equals(cardinal.name) || str.equals(cardinal.shortcut)) {
            return cardinal;
          }
        }
      return null; //if nothing matches
    }

    public static Cardinals get(int x, int y) {
        int xInSign = sign(x);
        int yInSign = sign(y);
        for (Cardinals cardinal : Cardinals.values()) {
          if (cardinal.xSign == xInSign && cardinal.ySign == yInSign) {
            return cardinal;
          }
        }
      return null; //if nothing matches
    }

    public static Cardinals get(Coordinate c) {
      return Cardinals.get(c.x(), c.y());
    }

    public static Cardinals get(Coordinate c, Coordinate target) {
        Coordinate diff = Coordinates.diff(c, target);
      return Cardinals.get(diff.x(), diff.y());
    }

    public static int getXSign(Cardinals direction) { return direction.xSign; }

    public static int getYSign(Cardinals direction) { return direction.ySign; }
}
