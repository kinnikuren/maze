package util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;

public abstract class ReadOnlyCatalyzedIterator<X, F, T> extends
        ReadOnlyTransformedIterator<F, T> {
    private X agent;

    ReadOnlyCatalyzedIterator(X agent, Iterator<? extends F> backingIterator) {
        super(backingIterator);
        //this.backingIterator = checkNotNull(backingIterator);
        this.agent = checkNotNull(agent);
    }

    @Override
    T transform(F source) {
        return catalyze(this.agent, source);
    }

    abstract T catalyze(X agent, F source);
}
