package tests;

import game.objects.general.References;

import java.util.ArrayList;
import java.util.List;

public class ReferenceTest {

    static class ListHolder {
        private List<? extends Object> list;
        public ListHolder(List<? extends Object> list) {
            this.list = list;
        }
        public List<? extends Object> get() { return list; }
        public void printout() { System.out.println(list); }
    }



    public static void main(String[] args) {
        ArrayList<String> stringOne = new ArrayList<String>();
        ArrayList<String> stringTwo = new ArrayList<String>();

        stringOne.add("apple"); stringOne.add("pear"); stringOne.add("cherry");
        stringTwo.add("motorcycle"); stringTwo.add("racecar");

        ListHolder lh = new ListHolder(stringOne);
        lh.printout();
        lh.list = stringTwo;

        //AYL Test
        System.out.println(References.get(101));

    }

}
