package game.core.inputs;

import game.core.maze.Maze.Room;

import java.util.ArrayList;

import static util.Loggers.*;
import static util.Utilities.*;

import java.util.List;

import util.NumbersToText;
import util.Utilities;
import util.Utilities.Checkable;

public class GrammarGuy {

    /*public static final Set<String> PREPOSITIONS = Collections.unmodifiableSet(
            Arrays. */

    public enum Prepositions {
        IN,
        OF,
        ON,
        TO,
        BETWEEN,
        WITH,
        FROM;

        public static boolean isPreposition(String word) {
            word = strip(word);
            for (Prepositions p : Prepositions.values()) {
                String prep = strip(p.toString());
                if (prep.equals(word)) return true;
            }
          return false;
        }
    }

    public static String pluralizeNoun(String word) {
        int length = word.length();
        String tempWord = word;
        boolean hasSuffix = false;
        int endOfFirstWord = length;

        log("pluralize word: " + word);

        if (length < 3) return "NULL";

        String ul = null;

        // look for preposition
        String[] inputs = word.split("[\\s-]+");

        for (int i=0;i < inputs.length;i++) {
                log("pluralize noun input: " + inputs[i]);
            if (isPreposition(inputs[i]))
                hasSuffix = true;
        }

        if (hasSuffix) {
            //System.out.println("there's a suffix!");
            hasSuffix = true;
            endOfFirstWord = word.indexOf(" ");
                log("end of first word: " + endOfFirstWord);
            if (endOfFirstWord == -1) endOfFirstWord = word.indexOf("-");
            tempWord = word.substring(0,endOfFirstWord);
            //UL = word.substring(endOfFirstWord-1,endOfFirstWord);
            //PL = word.substring(endOfFirstWord-2,endOfFirstWord-1);
        } else {
            //UL = word.substring(length-1,length);
            //PL = word.substring(length-2,length-1);
        }

        //System.out.println("**" + tempWord);

        length = tempWord.length();

        ul = tempWord.substring(length-1,length);
        Checkable pl = check(tempWord.substring(length-2,length-1));
        //if (PL.equals("g")) System.out.println("yay");

        switch (ul) {
            case "a":
            case "e":
            case "i":
            case "u":
                tempWord += "s";
                break;
            case "o":
                if (pl.in("r", "d"))
                  tempWord += "es";
                else
                  tempWord += "s";
                break;
            case "b":
            case "d":
            case "f":
            case "g":
            case "m":
            case "p":
            case "r":
            case "w":
                tempWord += "s";
                break;
            case "y":
                if (pl.in("l", "g", "r", "x", "h", "n"))
                  tempWord = tempWord.substring(0,length-1) + "ies";
                else
                  tempWord += "s";
                break;
            case "n":
            case "l":
            case "t":
                tempWord += "s";
                break;
            case "c":
                if (pl.in("h"))
                  word = word.substring(0,length-1) + "es";
                break;
            case "h":
                if (pl.in("s", "c"))
                  tempWord += "es";
                else
                  tempWord += "s";
                break;
            case "s":
                if (pl.in("u", "i"))
                  tempWord += "es";
                break;
            case "x":
                tempWord += "es";
                break;
        }

        // if pluralizing the first word
        if (hasSuffix)
          word = word.replaceFirst(word.substring(0,endOfFirstWord), tempWord);
        else
          word = tempWord;

      return word;
    }

    public static List<String> numberify(List<String> list, Room room) {
        for (int i = 0;i < list.size();i++) {
            String s = list.get(i);
            int num;
            num = room.howManyOf(s);
            //print(num);
            if (num > 1) {
                s = game.core.inputs.GrammarGuy.pluralizeNoun(s);
                String x = util.NumbersToText.convert(num);
                s =  x + " " + s.toUpperCase();
                //print(s);
            } else {
                s = addArticle(s.toUpperCase());
            }
            list.add(i,s);
            list.remove(i+1);
        }
      return list;
    }

    public static String oxfordCommify(List<String> list) {
        //String result = "a ";
        String result = "";

        if (list.size() == 1) {
            result = result + list.get(0);
        } else if (list.size() == 2) {
            result = list.get(0) + " and " + list.get(1);
        } else if (list.size() > 2) {
            for (int i=0;i < list.size();i++) {
                if (i < list.size()-2) {
                    //result = result + list.get(i) + ", a ";
                    result = result + list.get(i) + ", ";
                }
                else if (i == list.size()-2) {
                    //result = result + list.get(i) + ", and a ";
                    result = result + list.get(i) + ", and ";
                }
                else
                    result = result + list.get(i);
            }
        }
      return result;
    }

    public static ArrayList<String> addArticles(ArrayList<String> list) {
        for (int i=0;i < list.size();i++) {
            //print(list.get(i).substring(0,1));
            if (isFirstLetterVowel(list.get(i))) {
                //print("found a vowel!");
                list.add(i,"an " + list.get(i));
                list.remove(i+1);
            } else {
                list.add(i,"a " + list.get(i));
                list.remove(i+1);
            }
        }
        return list;
    }

    public static String addArticle(String word) {
        if (isFirstLetterVowel(word))
          return "an " + word;
        else
          return "a " + word;
    }

    public static boolean isPreposition(String word) {
      return Prepositions.isPreposition(word);
      //return check(strip(word)).in(
      //        "to", "on", "with", "from", "in", "of", "between");
        /* if(preps.contains(s)) return true;
        return false;
        return preps.contains(s);
        if (preps.contains(s) == true)
        */
    }

    public static boolean isFirstLetterVowel(String word) {
      return check(strip(word).charAt(0)).in('a', 'e', 'i', 'o', 'u','A','E','I','O','U');
    }

    public static String strip(String word) {
      return word.trim().toLowerCase();
    }

    /*public static void main(String[] args) { //debug and test
        String test1 = "hello";
        String test2 = "ayo";
        String test3 = "word to your mama";
        String test4 = " word   to your mamas  ";

        System.out.println(isFirstLetterVowel(test1));
        System.out.println(isFirstLetterVowel(test2));
        System.out.println(isFirstLetterVowel(test3));
        System.out.println(isFirstLetterVowel(test4));

        System.out.println(addArticle(test3));
        System.out.println(addArticle(test4));
    } */

    public static void main(String[] args) {
        String test1 = "of";
        String test2 = "IN ";
        System.out.println(isPreposition(test1));
        System.out.println(isPreposition(test2));
    }
}
