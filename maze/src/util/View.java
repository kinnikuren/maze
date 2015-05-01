package util;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class View<T> implements Iterable<T> {
    protected final Iterable<T> view;

    //Constructors below. Each takes an Iterable to transform into the wrapped iterable view
    //as well as various transformation mechanisms.
    //The constructors use protected initialization methods to initialize the wrapped iterable view
    //to allow subclass override of the initialization while keeping it final.
    public <F> View(final Iterable<? extends F> fromIterable,
            final Function<? super F, ? extends T> transform) {
        //iterating through this view will yield transformed versions of the members of the backing iterable
        //the transformation is defined by the function
        this.view = this.transform(fromIterable, transform);
    }

    public <X, F> View(X agent, final Iterable<? extends F> fromIterable,
            final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {
        //this is similar to the transformed view above, except it uses a binary function to transform
        this.view = this.catalyze(agent, fromIterable, catalyzer);
    }

    public View(final Iterable<? extends T> fromIterable) {
        //iterating through this view will yield type-flattened versions of the members of the backing iterable
        //functionally equivalent to Collection<? extends T> given the original Collection<T>, except can be read-only
        this.view = this.flatten(fromIterable);
    }

    public View(final Iterable<T> fromIterable, Predicate<? super T> predicate) {
        //temp
        this.view = Iterables.filter(fromIterable, predicate);
    }

    @SuppressWarnings("unchecked")
    public <K, V> View(final Map<? extends K, ? extends V> fromMap) {
        //temp
        this.view = (Iterable<T>) Views.flattenMapReadOnly(fromMap);
    }
    //end constructors

    //protected initialization methods. The View class itself uses read-only transformations that
    //will throw an exception if the 'remove' operation is attempted when using the iterator.
    protected <F> Iterable<T> transform(final Iterable<? extends F> fromIterable,
            final Function<? super F, ? extends T> transform) {

        return Views.transformReadOnly(fromIterable, transform);
    }

    protected <X, F> Iterable<T> catalyze(X agent, final Iterable<? extends F> fromIterable,
            final BinaryFunction<? super X, ? super F, ? extends T> catalyzer) {

        return Views.catalyzeReadOnly(agent, fromIterable, catalyzer);
    }

    protected Iterable<T> flatten(final Iterable<? extends T> fromIterable) {

        return Views.flattenReadOnly(fromIterable);
    }
    //end initialization methods

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

    public String toString() {
        return view.toString();
    }
}

