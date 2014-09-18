package game.core.positional;

import game.core.maze.MazeMap.Gate;
import game.objects.items.Trinkets.Key;

import java.util.EnumSet;
import java.util.Set;
import java.util.Collections;

import static util.Utilities.*;
import static game.core.positional.Cardinals.*;

import util.BinaryFunction;

public final class Coordinates {
    private Coordinates() { } //no instantiation

    public static final Set<Cardinals> CARDINALS = Collections.unmodifiableSet(
            EnumSet.of(NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST));

    public static final Set<Cardinals> PRIME_CARDINALS = Collections.unmodifiableSet(
            EnumSet.of(NORTH, SOUTH, EAST, WEST));

    public static final Set<Cardinals> LESSER_CARDINALS = Collections.unmodifiableSet(
            EnumSet.of(NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST));

    public static Coordinate origin = new Coordinate(0,0);

    public enum Find implements BinaryFunction<Coordinate, Coordinate, Cardinals> {
        DIRECTION;
        @Override public Cardinals apply(Coordinate c, Coordinate target) {
            return Cardinals.get(c, target);
        }
    }

    public static Coordinate diff(Coordinate c, Coordinate target) {
        return new Coordinate(target.x() - c.x(), target.y() - c.y());
    }

    public static class Paired {
        public final Coordinate c1;
        public final Coordinate c2;

        public Paired(Coordinate c1, Coordinate c2) {
            checkNullArgs(c1, c2);
            this.c1 = c1;
            this.c2 = c2;
        }
        public boolean matches(Coordinate c1, Coordinate c2) {
          return contains(c1) && contains(c2);
        }
        public boolean matches(Paired other) {
          return matches(other.c1, other.c2);
        }
        public boolean contains(Coordinate c) {
          return this.c1.equals(c) || this.c2.equals(c);
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;

            if (o instanceof Paired) {
                Paired p = (Paired)o;
                if (matches(p.c1, p.c2))
                  return true;
            }
          return false;
        }
        @Override
        public int hashCode() {
            int x = c1.hashCode();
            int y = c2.hashCode();
          return ((x*x)+17)*31 + ((y*y)+17)*31;
        }
    }
}
