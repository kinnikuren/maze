package tests;

import static util.Print.*;
import game.core.interfaces.Portable;
import game.objects.items.Useables.BlueKey;
import game.objects.items.Useables.PurpleKey;
import game.objects.items.Useables.RedKey;

import java.util.*;


public class TestStuff {

    public interface Flammable {
        public void lightOnFire();
    }
    public interface IsLiquid {
        public void flow();
    }
    public interface Stickyable {
        public void sticky();
    }
    public interface Torchable {
        public void holdAloft();
    }
    public interface ReturnCombination {
        public String returnCombination();
    }

    public static class TestItem {
        public TestItem() { }
    }

    public static class StickyFlameGel extends TestItem implements Flammable, IsLiquid, Stickyable {
        @Override
        public void sticky() {}
        @Override
        public void flow() {}
        @Override
        public void lightOnFire() {}
    }
    public static class LongPole extends TestItem implements Torchable {
        @Override
        public void holdAloft() {}
    }
    public static class ShortPole extends TestItem implements Torchable {
        @Override
        public void holdAloft() {}
    }
    public static class Rag extends TestItem implements Flammable, Stickyable {
        @Override
        public void sticky() {}
        @Override
        public void lightOnFire() {}
    }
    public static class Paper extends TestItem implements Flammable {
        @Override
        public void lightOnFire() {}
    }

    public enum CombinerDraft implements ReturnCombination {
        C01 ("Torch", new Class[] {Flammable.class, Stickyable.class, Torchable.class})
        {
            @Override public String returnCombination() {
                return getResult();
            }
            @Override boolean accepts(TestItem[] items) {
                return true;
            }
        };
        private final String result;
        private final Class[] recipeList;

        CombinerDraft(String result, Class[] recipeList) {
            this.result = result;
            this.recipeList = recipeList;
        }
        String getResult() { return this.result; }
        Class[] getRecipeList() { return this.recipeList; }

        abstract boolean accepts(TestItem[] items);
    }



    public static void main(String[] args) {
        /* HashMap<Integer, String> map = new HashMap<Integer, String>();
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
        */
        //Scanner scnr = new Scanner(System.in);
        //int N = scnr.nextInt();
        //print("hello");
        //return;
        //print("world");

        /*Integer[] intArray = new Integer[] {1,2,3,4};
        List<Integer> integers = Arrays.asList(intArray);

        String s = null;

        for (Integer i : integers) {
          s = i.toString();
        }
        System.out.println("s: " + s);*/

        String s1 = "abcde";
        String s2 = "abcde";

        System.out.println(s1 == s2);
        Object o1 = s1;
        Object o2 = s2;
        System.out.println(s1 == o2);
        System.out.println(s2 == o2);
        System.out.println(o1 == o2);

        String s3 = new String("abcde");
        System.out.println(s2 == s3);

        Integer[] intArray = new Integer[] {1,2,3,4, null};
        List<Integer> integers = Arrays.asList(intArray);
        if (integers.contains(null)) {
            System.out.println("5 passed");
        }
    }
}
