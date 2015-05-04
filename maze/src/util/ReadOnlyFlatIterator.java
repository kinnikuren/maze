package util;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Iterator;

public abstract class ReadOnlyFlatIterator<T> implements Iterator<T> {
    final Iterator<? extends T> backingIterator;

    public ReadOnlyFlatIterator(Iterator<? extends T> backingIterator) {
        this.backingIterator = checkNotNull(backingIterator);
    }

    abstract T flatten(T ofFlattenedType);
    /* {
      return ofFlattenedType;
    } */

    @Override
    public final boolean hasNext() {
      return backingIterator.hasNext();
    }

    @Override
    public final T next() {
      return flatten(backingIterator.next());
    }

    @Override
    public final void remove() {
        //backingIterator.remove();
        throw new UnsupportedOperationException();
    }
}
