package util;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Function;

import static util.Print.*;

public class WrapperCollection<T> implements Collection<T> {
    private final Collection<T> collection;

/*
    public <F> WrapperCollection(final Iterable<? extends F> originalSet,
            final Function<? super F, ? extends T> transform) {
        this.view = Views.transformReadOnly(originalSet, transform);
    }

    public <X, F> WrapperCollection(X agent, final Iterable<? extends F> originalSet,
            final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {
        this.view = Views.catalyzeReadOnly(agent, originalSet, catalyzer);
    } */

    public WrapperCollection(final Iterable<? extends T> originalSet) {
        this.collection = Collections.flatten(originalSet);
    }

    public boolean contains(final Object o) {
        for (T t : collection) {
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
        return collection.iterator();
    }

    @SuppressWarnings("unused")
    public int size() {
        int size = 0;
        for (T t : collection) ++size;
      return size;
    }

    public Object[] toArray() {
    //returns the view contents as an array of Objects
        int i = 0;
        Object[] arrayObj = new Object[size()];
        for (T t : collection) {
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
        return collection.toString();
    }
}

