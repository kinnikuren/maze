package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EasyAddList<E> extends ArrayList<E> {

    public <E> EasyAddList(E... elements) {
        super();
        for (E e : elements) {
            //add(e);
        }
    }

}
