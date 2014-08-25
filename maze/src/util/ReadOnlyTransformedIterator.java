package util;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;

//read only version of guava TransformedIterator
abstract class ReadOnlyTransformedIterator<F, T> implements Iterator<T> {
    final Iterator<? extends F> backingIterator;

    ReadOnlyTransformedIterator(Iterator<? extends F> backingIterator) {
        this.backingIterator = checkNotNull(backingIterator);
    }

    abstract T transform(F source);

    @Override
    public final boolean hasNext() {
      return backingIterator.hasNext();
    }

    @Override
    public final T next() {
      return transform(backingIterator.next());
    }

    @Override
    public final void remove() throws UnsupportedOperationException {
        //backingIterator.remove();
        throw new UnsupportedOperationException();
    }
}
