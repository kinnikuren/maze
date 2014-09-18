package tests;

import static util.Print.*;
import maze.*;

import java.util.*;


public class TestStuff {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "string1");
        map.put(2, "string2");
        map.put(3, "string3");
        map.put(4, "string4");

        ArrayList<String> list = new ArrayList<String>();
        print(map);

        for (Iterator<String> itr = map.values().iterator(); itr.hasNext();) {
            String s = itr.next();
            list.add(s);
            itr.remove();
        }
        print("--post itr--");
        print(map);
        print(list);

    }
}
