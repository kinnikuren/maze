package util;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Null arguments not allowed for Tuple creation");
        }
        this.x = x;
        this.y = y;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Tuple) {
            Tuple t = (Tuple)o;
            if (t.x.equals(x) && t.y.equals(y))
              return true;
        }
      return false;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        hashCode = prime * hashCode + x.hashCode();
        hashCode = prime * hashCode + y.hashCode();
        return hashCode;
    }
    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }

}
