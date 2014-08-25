package tests;

import java.util.*;
import static util.Print.*;

public class CollectionTest {
    public static HashSet<Integer> testSet = new HashSet<Integer>();

    public static void main(String[] args) {
        /* testSet.add(new Integer(1));
        testSet.add(new Integer(2));
        testSet.add(new Integer(7));
        testSet.add(new Integer(8));
        if(testSet.add(new Integer(9))) {
            print("element successfully added!");
        } else {
            print("failed to add element! it's already present!");
        }
        if(testSet.add(new Integer(9))) {
            print("element successfully added!");
        } else {
            print("failed to add element! it's already present!");
        }
        if(testSet.add(new Integer(10))) {
            print("element successfully added!");
        } else {
            print("failed to add element! it's already present!");
        }
        if(testSet.add(new Integer(7))) {
            print("element successfully added!");
        } else {
            print("failed to add element! it's already present!");
        }
        //testSet.add(new Integer(9));
        Integer [] printIntegerSet = testSet.toArray(new Integer[0]);
        print(Arrays.toString(printIntegerSet)); */

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "yo");
        map.put(2, "zz");
        map.put(2, null);
        print(map.remove(3));
        print(map.keySet());
        String s = map.remove(2);
        print(map.keySet()); print(s);
    }

}
