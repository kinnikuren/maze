package util;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import static util.Print.*;

public class View<T> implements Iterable<T> {
    private final Iterable<T> view;

    public <F> View(final Iterable<? extends F> fromIterable,
            final Function<? super F, ? extends T> transform) {

        this.view = Views.transformReadOnly(fromIterable, transform);
    }

    public <X, F> View(X agent, final Iterable<? extends F> fromIterable,
            final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {

        this.view = Views.catalyzeReadOnly(agent, fromIterable, catalyzer);
    }

    public View(final Iterable<? extends T> fromIterable) {

        this.view = Views.flattenReadOnly(fromIterable);
    }

    public View(final Iterable<T> fromIterable, Predicate<? super T> predicate) {

        this.view = Iterables.filter(fromIterable, predicate);
    }

    @SuppressWarnings("unchecked")
    public <K, V> View(final Map<? extends K, ? extends V> fromMap) {
        this.view = (Iterable<T>) Views.flattenMap(fromMap);
    }

    public boolean contains(final Object o) {
        for (T t : view) {
            //print("checking " + o + " vs " + t);
            if (o == null ? t == null : o.equals(t)) {
              return true;
            }
        }
      return false;
    }

    public boolean containsAll(final Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o))
              return false;
        }
      return true;
    }

    public boolean isEmpty() {
        return !iterator().hasNext();
    }

    @Override
    public Iterator<T> iterator() {
        return view.iterator();
    }

    @SuppressWarnings("unused")
    public int size() {
        int size = 0;
        for (T t : view) ++size;
      return size;
    }

    public Object[] toArray() {
    //returns the view contents as an array of Objects
        int i = 0;
        Object[] arrayObj = new Object[size()];
        for (T t : view) {
            arrayObj[i] = t;
            ++i;
        }
      return arrayObj;
    }
    /* public T[] toArray(T[] inputT) {
    //takes an array of T as input and replaces its contents, if any, with the view contents
        int i = 0;
        T[] arrayT = Arrays.copyOf(inputT, size());
        for (T t : view) {
            arrayT[i] = t;
            ++i;
        }
      return arrayT;
    } */

    public String toString() {
        return view.toString();
    }
}

