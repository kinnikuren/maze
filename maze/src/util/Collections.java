package util;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Collections {
    private Collections() { } //no instantiation
/*
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
    } */

    public static <T> Collection<T> flatten(final Iterable<? extends T> fromIterable) {
        checkNotNull(fromIterable);
      return new Collection<T>() {
          @Override
          public Iterator<T> iterator() {
            return CollectionIterators.flatten(fromIterable.iterator());
          }

        @Override
        public boolean add(T e) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void clear() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean contains(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean remove(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            // TODO Auto-generated method stub
            return null;
        }
      };
    }
/*
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
    } */
}
