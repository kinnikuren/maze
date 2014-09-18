package tests;

import game.core.positional.Coordinate;

import java.util.*;

import static util.Print.*;

public class TestIteration {
    public ArrayList<String> someList = new ArrayList<String>();
    public HashSet<Coordinate> someSet = new HashSet<Coordinate>();
    private final int u = 20;
    private int b;

    public TestIteration() {
    }

    public void doIteration() {
        //b = new Random().nextInt(3)+1;
        b = 3;
        int m = 0;
        print("Batch size: " + b);
        while (m < u) {
            for(int i = 0; i < b; i++) {
                someSet.add(new Coordinate(m,1));
                m++;
                if (m >= u) { print("breaking loop..."); break; }
            }
            print("batch completed");
        }
    }

    public static void main(String[] args) {
        TestIteration t = new TestIteration();
        t.doIteration();
        print("someSet size: " + t.someSet.size());
    }
}