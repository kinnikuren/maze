package util;
import java.util.Iterator;
import java.util.Map.Entry;
import com.google.common.base.Function;
import static com.google.common.base.Preconditions.checkNotNull;

public final class RmViewIterators {
  private RmViewIterators() { } //no instantiation

  public static <T> Iterator<T> flatten(final Iterator<? extends T> fromIterator) {
    return new FlatIterator<T>(fromIterator) {
        @Override
        T flatten(T from) {
          return from;
        }
    };
  }
}
