package tests;

import java.util.List;
import java.util.HashSet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;

import static util.Print.*;
import maze.*;

public class GuavaMultimapTest {

    public static void main(String[] args) {
        ArrayListMultimap<Coordinate, Coordinate> arrayC = ArrayListMultimap.create();
        HashMultimap<Coordinate, Coordinate> hashC = HashMultimap.create();
        int counter;

        Coordinate k1 = new Coordinate();
        Coordinate k2 = new Coordinate(0,1);
        Coordinate k3 = new Coordinate();
        Coordinate k4 = new Coordinate(-20,20);
        Coordinate k5 = new Coordinate(99,10);
        Coordinate v1 = new Coordinate(1,1);
        Coordinate v2 = new Coordinate(1,2);
        Coordinate v3 = new Coordinate(1,1);
        Coordinate v4 = new Coordinate(7,7);
        Coordinate v5 = new Coordinate(8,8);
        Coordinate v6 = new Coordinate(1234, 5678);


        arrayC.put(k1, v1);
        arrayC.put(k1, v2);
        arrayC.put(k1, v1);
        arrayC.put(k2, v1);
        arrayC.put(k2, v2);
        arrayC.put(k2, v3);
        arrayC.put(k2, v4);
        arrayC.put(k3, v1);
        arrayC.put(k3, v2);
        arrayC.put(k3, v3);
        arrayC.put(k3, v4);
        arrayC.put(k3, v5);
        arrayC.put(k3, v5);
        arrayC.put(k4, v4);
        arrayC.put(k4, v1);

        print(arrayC);
        print("\n");
        print("-----ARRAY MAP KEYS FUNCTION-----");
        counter = 1;
        for (Coordinate c : arrayC.keys()) {
            print("C: " + c + "counter: " + counter);
            ++counter;
        }
        print("\n");
        print("-----ARRAY MAP KEYSET-----");
        counter = 1;
        for (Coordinate c : arrayC.keySet()) {
            print("C: " + c + "counter: " + counter);
            ++counter;
        }

        print(arrayC.get(k1));
        print(arrayC.get(k1).size());
        List<Coordinate> arrayMod = arrayC.get(k1);

        arrayMod.add(v6);

        print(arrayC.get(k1));
        print(arrayC.get(k1).size());

        print("----------------");
        print("++++++++++++++++");
        print("----------------");

        hashC.put(k1, v1);
        hashC.put(k1, v2);
        hashC.put(k1, v3);
        hashC.put(k2, v1);
        hashC.put(k2, v2);
        hashC.put(k2, v3);
        hashC.put(k2, v4);
        hashC.put(k3, v1);
        hashC.put(k3, v2);
        hashC.put(k3, v3);
        hashC.put(k4, v2);
        hashC.put(k4, v4);
        hashC.put(k5, null);
        hashC.put(k5, null);
        hashC.put(k5, null);
        hashC.put(k5, null);
        hashC.put(k5, null);
        hashC.put(k5, v5);
        //hashC.removeAll(k5);

        print(hashC);

        //HashSet<Coordinate> neighborSet = (HashSet<Coordinate>)hashC.get(k1);
        HashSet<Coordinate> neighborSet2 = new HashSet<Coordinate>();
        for (Coordinate c : hashC.get(k2)) {
            neighborSet2.add(c);
        }

        //print(neighborSet);
        print(neighborSet2);
        print("\n");
        print("-----HASH MAP KEYS FUNCTION-----");
        counter = 1;
        for (Coordinate c : hashC.keys()) {
            print("C: " + c + "counter: " + counter);
            ++counter;
        }
        print("\n");
        print("-----HASH MAP KEYSET-----");
        counter = 1;
        for (Coordinate c : hashC.keySet()) {
            print("C: " + c + "counter: " + counter);
            ++counter;
        }

        print(hashC.get(k5));
        //Coordinate[] carray = new Coordinate[5];
        for (Coordinate d: hashC.get(k5)) {
            print(d);
        }
    }
}
