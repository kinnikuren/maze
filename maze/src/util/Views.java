package util;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Views {
    private Views() { } //no instantiation

    public static <F, T> Iterable<T> transformReadOnly(final Iterable<F> fromIterable,
            final Function<? super F, ? extends T> function) {
        checkNotNull(fromIterable);
        checkNotNull(function);
      return new FluentIterable<T>() {
          @Override
          public Iterator<T> iterator() {
            return ViewIterators.transformReadOnly(fromIterable.iterator(), function);
          }
      };
    }

    public static <T> Iterable<T> flattenReadOnly(final Iterable<? extends T> fromIterable) {
        checkNotNull(fromIterable);
      return new FluentIterable<T>() {
          @Override
          public Iterator<T> iterator() {
            return ViewIterators.flattenReadOnly(fromIterable.iterator());
          }
      };
    }

    public static <X, F, T> Iterable<T> catalyzeReadOnly(final X agent, final Iterable<F> fromIterable,
            final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {
        checkNotNull(fromIterable);
        checkNotNull(catalyzer);
      return new FluentIterable<T>() {
          @Override
          public Iterator<T> iterator() {
            return ViewIterators.catalyzeReadOnly(agent, fromIterable.iterator(), catalyzer);
          }
      };
    }

    public static <K, V> Iterable<Entry<K,V>> flattenMapReadOnly(
            final Map<? extends K, ? extends V> fromMap) {
        checkNotNull(fromMap);
        final Iterable<? extends Entry<? extends K, ? extends V>> fromItr = fromMap.entrySet();
      return new FluentIterable<Entry<K,V>>() {
          @Override
          public Iterator<Entry<K,V>> iterator() {
            return ViewIterators.flattenMapIterator(fromItr.iterator());
          }
      };
    }

    public static <T> Iterable<T> flatten(final Iterable<? extends T> fromIterable) {
        checkNotNull(fromIterable);
      return new FluentIterable<T>() {
          @Override
          public Iterator<T> iterator() {
            return ViewIterators.flatten(fromIterable.iterator());
          }
      };
    }
}
