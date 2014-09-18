package util;

import static util.Utilities.checkNullArgs;

public class Paired<E> {
    public final E o1;
    public final E o2;

    public Paired(E o1, E o2) {
        checkNullArgs(o1, o2);
        this.o1 = o1;
        this.o2 = o2;
    }

    public boolean contains(Object o) {
        return this.o1.equals(o) || this.o2.equals(o);
    }

    public boolean matches(Object o1, Object o2) {
      return contains(o1) && contains(o2);
    }

    @SuppressWarnings("rawtypes")
    public boolean matches(Paired other) {
      return matches(other.o1, other.o2);
    }

    @Override @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Paired) {
          Paired p = (Paired)o;
          if (matches (p.o1, p.o2)) {
            return true;
          }
        }
      return false;
    }

    @Override
    public int hashCode() {
        int x = o1.hashCode();
        int y = o2.hashCode();
      return ((x*x)+17)*31 + ((y*y)+17)*31;
    }
}
