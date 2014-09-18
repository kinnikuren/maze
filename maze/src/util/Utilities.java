package util;

import game.core.positional.Coordinate;
import game.core.positional.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import com.google.common.base.Predicate;

public final class Utilities {
    private Utilities() { } //no instantiation

    public enum NullFilter implements Predicate<Object> {
        //allows for view filtering on set of nodes for listed conditions
        NOT_NULL {
          @Override public boolean apply(Object object) {
            return object != null;
          }
        },
        NULL {
          @Override public boolean apply(Object object) {
            return object == null;
          };
        }
    }

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

    public static <E> LinkedList<E> reverseLinkedList(LinkedList<E> list) {
        LinkedList<E> reverse = new LinkedList<E>();

        for (Iterator<E> itr = list.descendingIterator(); itr.hasNext();) {
          reverse.add(itr.next());
        }
        list.clear();
        for (E e : reverse) {
          list.add(e);
        }
      return list;
    }
}
