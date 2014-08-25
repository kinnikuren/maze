package tests;

import maze.*;
import java.util.*;
import static util.Print.*;
import java.math.*;

@SuppressWarnings("unused")
public class TestMoreStuff {

    public static void main(String[] args) {

        Node n = new Node();
        if (n instanceof Coordinate) print("yes");
        else print("no");

        //OuterMaze om = new OuterMaze(5);
        //InnerRoom ir = om.buildInnerRoom(c); InnerRoom is not visible if private
        //AbstractRoom ir = om.buildInnerRoom(Coordinates.origin);

        /* PriorityQueue<Priority> pq = new PriorityQueue<Priority>();
        for (Priority p : Priority.values()) pq.add(p);

        int counter = pq.size();

        for (int i = 0; i < counter; i++) {
            Priority upShifted = Priority.upShift(pq.poll());
            print(upShifted);
        }

        print("-----------");

        PriorityQueue<Priority> pq2 = new PriorityQueue<Priority>();
        for (Priority p : Priority.values()) pq2.add(p);

        int counter2 = pq2.size();

        for (int i = 0; i < counter2; i++) {
            Priority downShifted = Priority.downShift(pq2.poll());
            print(downShifted);
        }

        Integer i = 3; Integer j = 4; Integer k = 4;
        Integer l = new Integer(4);
        k = l;

        print(j==k);

        int ii = 4;
        int jj = ii;
        ii += 1;
        print(ii); print(jj);

        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(k, "nothing");
        //Map<? extends Number, String> map = new HashMap<Integer, String>();
        //map.put(i, "thing");
        //Set<Map.Entry<? extends Number,String>> set = map.entrySet();
        //Set<? extends Map.Entry<? extends Number,String>> set = map.entrySet();
        Set<Map.Entry<Integer,String>> set = map.entrySet();
        Map.Entry<Integer, String> entry = new AbstractMap.SimpleEntry<Integer, String>(j, "thing");
        try {
            set.clear();
            //set.add(entry);
        } catch (Exception e) { e.printStackTrace(); }
        for (Integer key : map.keySet()) {
            print("key: " + key + " value: " + map.get(key));
        } */
    }

}
