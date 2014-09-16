package util;

public class RmView<T> extends View<T> implements Iterable<T>{
    public RmView(final Iterable<? extends T> fromIterable) {

        super(fromIterable);
    }

    @Override
    protected Iterable<T> flatten(final Iterable<? extends T> fromIterable) {

        return Views.flatten(fromIterable);
    }
}
