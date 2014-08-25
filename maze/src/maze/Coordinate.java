package maze;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c, Cardinals direction) {
        int xSign = Cardinals.getXSign(direction);
        int ySign = Cardinals.getYSign(direction);
        this.x = c.x() + (xSign);
        this.y = c.y() + (ySign);
    }

    public Coordinate(Coordinate c, Cardinals direction, int xDistance, int yDistance) {
        int xSign = Cardinals.getXSign(direction);
        int ySign = Cardinals.getYSign(direction);

        if ((xDistance != 0 && xSign == 0) || (yDistance != 0 && ySign == 0)) {
            throw new IllegalArgumentException("INVALID NONZERO DISTANCE USED "
                    + "FOR COORDINATE CONSTRUCTION");
        }
        this.x = c.x() + (xDistance*xSign);
        this.y = c.y() + (yDistance*ySign);
    }

    public String toString() { return "(" + x + ", " + y + ")"; }

    public int x() { return x; }

    public int y() { return y; }

    public Coordinate getDiff(Coordinate target) {
      return Coordinates.diff(this, target);
    }

    public Double getDistanceTo(Coordinate target) {
        Double distance = Math.sqrt(Math.pow(target.x() - x, 2) + Math.pow(target.y() - y, 2));
      return distance;
    }

    public Coordinate clone() { return new Coordinate(x, y); }

    public Cardinals whichDirectionTo(Coordinate target) {
      return Cardinals.get(this, target);
    }

    public Coordinate[] getNeighboringPrimes() {
        Coordinate[] neighbors = new Coordinate[Coordinates.PRIME_CARDINALS.size()];
        int i = 0;
        for (Cardinals card : Coordinates.PRIME_CARDINALS) {
            neighbors[i] = new Coordinate(this, card);
            ++i;
        };
      return neighbors;
    }

    public Node toNode() { return new Node(x, y); }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate)o;
            if (c.x() == x && c.y() == y)
              return true;
        }
      return false;
    }
    @Override
    public int hashCode() {
        if (x == 0 && y == 0) return 0;
        else {
            int xPrime = Math.abs(x) + Math.abs(y);
            int ordinalXPrime = 2*(xPrime*xPrime + xPrime);
            int distance = (y <= 0) ? xPrime - x : 3*xPrime + x;
            int ordinalXY = ordinalXPrime - distance;
          return ordinalXY;
        }
    }
}
