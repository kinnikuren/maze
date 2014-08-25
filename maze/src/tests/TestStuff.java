package tests;

import static util.Print.*;
import maze.*;
import java.util.*;


public class TestStuff {

    public static void main(String[] args) {
        Trinkets.BronzeCoin coin = new Trinkets.BronzeCoin();
        print(coin);

        String[] stringarray = new String[4];
        stringarray[0] = "hello";
        stringarray[1] = "yo";
        for (String s : stringarray) print(s);

        /* String s = null;
        if ("".equals(s)) print ("empty is equal to null");
        else print ("empty is not equal to null");
        if (s.isEmpty()) print ("no exception"); */

        /* Set<Integer> setIntA = new HashSet<Integer>();
        setIntA.add(1);
        setIntA.add(2);
        Set<Integer> setIntB = new HashSet<Integer>();
        setIntB.add(1);
        setIntB.add(2);
        print(setIntB.add(4));
        setIntB.add(3);

        if (setIntA.addAll(setIntB)) { print("A.addAll(B) returned true"); }
        else print("A.addAll(B) returned false");

        if (setIntA.addAll(setIntB)) { print("A.addAll(B) returned true"); }
        else print("A.addAll(B) returned false");

        String str = "n";
        Cardinals direction = Cardinals.get(str);
        print(direction); */
        /* Package p = new Package("500 pounds of kangaroo meat");
        Package p2 = new Package("A box of dead batteries");
        Package.Label labelOfP = p.newLabel("Edison", "Fresno", "Bobby Jindal", 91210);
        Package.Label labelOfP2 = p2.newLabel("Toronto", "Vancouver", "Santa Claus", 88401);
        //p2.printContents();

        p2.setLabel(labelOfP);
        p.setLabel(labelOfP2);
        //p.printLabel();
        print("---------------");
        print("---------------");
        print("---------------");
        p2.printLabel();
        p2.printContents();
        p2.label().getPackage().printLabel();
        p2.label().getPackage().printContents();

        //labelOfP2.printout();
        //labelOfP2.getPackage().printContents(); */

    }
}
