package util;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.*;

import java.util.*;
import java.util.Map.Entry;

public abstract class MapFunction<F, T> implements Function<F, T> {
    public static <K, V> Entry<K, V> flattenMapEntry(
            final Entry<? extends K, ? extends V> fromEntry) {
        checkNotNull(fromEntry);
        Entry<K, V> flatEntry = new AbstractMap.SimpleEntry<K, V>(fromEntry);
      return flatEntry;
    }
    /* @Override
    public T apply(F input) {

    } */
}
