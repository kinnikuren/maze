package tests;

import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;
import game.core.positional.Node;

import java.util.*;

import util.View;
import maze.*;
import static util.Print.*;

import com.google.common.base.Function;
import com.google.common.collect.*;

public class CoContraVarianceTest {
    public enum Stringer implements Function<Object, String> {
        DO;
        @Override
        public String apply(Object o) { return o.toString(); };
    }

    public static void main(String[] args) {
        /*List<Object> myObjs = new ArrayList<Object>();
        myObjs.add("Luke");
        myObjs.add("Obi-wan");

        List<? super Skeleton> mySkellies = new ArrayList<Interacter>();
        //List<? super Integer> myInts = myObjs;
        //myInts.add(10);
        //myInts.add(3);

        Object string = "Darth Vader";
        Skeleton sk = new Skeleton();
        //Integer five = 5;
        //myNums.add(string);
        //myInts.add(five);
        mySkellies.add(sk);
        Unit actor = new Player();
        //mySkellies.add(actor);
        myObjs.add(string);

        //List<? super Number> myNums = (List<? super Number>) myInts;

        print("myObjs => " + myObjs);
        //print("myNums => " + myInts);
        print("mySkellies => " + mySkellies);
        */

        List<SimpleWord> wordList = new ArrayList<SimpleWord>();
        wordList.add(new SimpleWord("duck"));
        wordList.add(new SimpleWord("goose"));
        wordList.add(new SimpleWord("chicken"));
        wordList.add(new SimpleWord("rooster"));

        //Iterable<Palindrome> palindromes = Iterables.transform(wordList, SimpleWord.Convert.TO_PALINDROME);
        //for (Palindrome p : palindromes) { print(p); }
        View<Palindrome> palindromes = new View<Palindrome>(wordList, SimpleWord.Convert.TO_PALINDROME);
        print("palindromes => " + palindromes);


        List<Palindrome> palindromeList = new ArrayList<Palindrome>();
        Iterables.addAll(palindromeList, palindromes);
        palindromeList.add(new Palindrome(new SimpleWord("hen")));
        wordList.add(new SimpleWord("wtf"));

        print("wordList => " + wordList);
        print("palindromes => " + palindromes);
        print("palindromeList => " + palindromeList);

        wordList.add(new SimpleWord("dubya"));
        print("wordList => " + wordList);
        print("palindromes => " + palindromes);
        print("palindromeList => " + palindromeList);

        //if (palindromes.contains("wtf")) print("CONTAINS WTF");
        //if (palindromes.contains("wtfftw")) print("CONTAINS WTFFTW");
        Palindrome[] pal2 = new Palindrome[] {
                new Palindrome("duck"),
                new Palindrome("wtf"),
                //new Palindrome("notinhere")
        };
        List<Palindrome> palList2 = new ArrayList<Palindrome>(Arrays.asList(pal2));
        if (palindromes.containsAll(palList2)) print("CONTAINS ALL");

        List<Coordinate> cSet = new ArrayList<Coordinate>();
        Coordinate c1 = new Coordinate(2,2);
        Coordinate c2 = new Coordinate(-2,5);
        Coordinate c3 = new Coordinate(3,-1);
        Node n1 = new Node(1,1);
        cSet.add(c1);
        cSet.add(c2);
        cSet.add(c3);

        View<Cardinals> viewDirections = new View<Cardinals>(n1, cSet, Coordinates.Find.DIRECTION);
        print("view as Cardinals...");
            for (Cardinals card : viewDirections) {
                print(card);
            }
            for (Cardinals card : viewDirections) {
                if (card == Cardinals.NORTHWEST) print(card + " is " + Cardinals.NORTHWEST);
            }
        print("view as Objects...");
            View<Object> viewObjects = new View<Object>(viewDirections);
            for (Object o : viewObjects) print(o);
        print("view as Strings...");
            View<String> viewStrings = new View<String>(viewObjects, Stringer.DO);
            for (String str : viewStrings) print(str);

        Coordinate c4 = new Coordinate(-5,-6);
        cSet.add(c4);

        print("view as SimpleWords...");
            View<SimpleWord> viewSimpleWords = new View<SimpleWord>(viewStrings, SimpleWord.FromString.DO);
            for (SimpleWord sw : viewSimpleWords) print(sw);

        cSet.remove(c1); cSet.remove(c2);
        print("view as Palindromes...");
            View<Palindrome> viewPalindromes = new View<Palindrome>(viewSimpleWords, SimpleWord.Convert.TO_PALINDROME);
            for (Palindrome pal : viewPalindromes) print(pal);
    }
}
