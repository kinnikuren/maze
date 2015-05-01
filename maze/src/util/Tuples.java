package util;

import game.core.positional.Node;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class Tuples {
    private Tuples() { } //no instantiation

    public static <X, Y> Function<Tuple<X, Y>, X> xFunction() {
        return new Function<Tuple<X, Y>, X>() {
            @Override
            public X apply(Tuple<X, Y> tuple) {
                return tuple.x;
            }
        };
    }

    public static <X, Y> Function<Tuple<X, Y>, Y> yFunction() {
        return new Function<Tuple<X, Y>, Y>() {
            @Override
            public Y apply(Tuple<X, Y> tuple) {
                return tuple.y;
            }
        };
    }

    public <X, Y> View<X> xViewOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        final Function<Tuple<X, Y>, X> xF = Tuples.xFunction();
        return new View<X>(fromIterable, xF);
    }

    public <X, Y> View<Y> yViewOfTuples(final Iterable<Tuple<X,Y>> fromIterable) {
        final Function<Tuple<X, Y>, Y> yF = Tuples.yFunction();
        return new View<Y>(fromIterable, yF);
    }

    public enum Filter implements Predicate<Node> {
        //allows for filtering tuple views on input value
        HAS_NO_LINKS {
          @Override public boolean apply(Node node) {
            return node.numberOfLinks() == 0 ? true : false;
          }
        },
        HAS_TWO_LINKS {
            @Override public boolean apply(Node node) {
                return node.numberOfLinks() == 2 ? true : false;
            }
        },
        HAS_LINKS {
          @Override public boolean apply(Node node) {
            return node.numberOfLinks() > 0 ? true : false;
          };
        }
    }

}
