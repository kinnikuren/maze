package util;

import com.google.common.base.Function;

public class UpcastFunction<F,T> implements Function<F,T> {
    @Override
    public T apply(F input) {
        try {
            @SuppressWarnings("unchecked")
            T output = (T)input;
            return output;
        } catch (ClassCastException cce) {
            throw cce;
        }
    }
}
