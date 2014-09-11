package util;
import java.util.Iterator;
import java.util.Map.Entry;
import com.google.common.base.Function;
import static com.google.common.base.Preconditions.checkNotNull;

public final class CollectionIterators {
  private CollectionIterators() { } //no instantiation

  /* public static <F, T> Iterator<T> transformReadOnly(final Iterator<F> fromIterator,
          final Function<? super F, ? extends T> function) {
      checkNotNull(function);
    return new ReadOnlyTransformedIterator<F, T>(fromIterator) {
        @Override
        T transform(F from) {
          return function.apply(from);
        }
    };
  }

  public static <X, F, T> Iterator<T> catalyzeReadOnly(final X agent, final Iterator<F> fromIterator,
          final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {
      checkNotNull(catalyzer);
    return new ReadOnlyCatalyzedIterator<X, F, T>(agent, fromIterator) {
        @Override
        T catalyze(X agent, F from) {
          return catalyzer.apply(agent, from);
        }
    };
  } */

  public static <T> Iterator<T> flatten(final Iterator<? extends T> fromIterator) {
    return new FlatIterator<T>(fromIterator) {
        @Override
        T flatten(T from) {
          return from;
        }
    };
  }
}
