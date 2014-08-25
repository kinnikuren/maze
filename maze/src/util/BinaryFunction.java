package util;

public interface BinaryFunction<X, F, T> {
    T apply(X x, F f);
}
