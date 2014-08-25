package util;

public final class Utilities {
    private Utilities() { } //no instantiation

    public static void checkNullArg(Object o) throws NullArgumentException {
        if (o == null) throw new NullArgumentException("Null arguments are not allowed for this method.");
    }

    public static void checkNullArgs(Object... args) throws NullArgumentException {
        for (Object o : args) {
          if (o == null) throw new NullArgumentException("Null arguments are not allowed for this method.");
        }
    }

    public static boolean anyNullObjects(Object... args) {
        for (Object o : args) {
            if (o == null) return true;
        }
      return false;
    }

    public static Checkable check(Object o) {
        return new Checkable(o);
    }

    public static class Checkable {
        private Object checkable;

        public Checkable(Object o) {
            if (o == null) throw new NullArgumentException("Attempt to create checkable on null object");
            checkable = o;
        }

        public boolean in(Object... args) {
            for (Object o : args) {
              if (checkable.equals(o)) return true;
            }
          return false;
        }
    }

    public static int sign(int x) {
    //returns 1 for positive integers, -1 for negative integers, and 0 for 0
      return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }

    public static int sign(double x) {
    //returns 1 for positive doubles, -1 for negative doubles, and 0 for 0
      return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }
}
