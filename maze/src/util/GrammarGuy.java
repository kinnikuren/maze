package util;

import java.util.ArrayList;

import maze.Maze.Room;
import maze.Stage;

public class GrammarGuy {

    public static String pluralizeNoun(String word) {
        int length = word.length();
        String tempWord = word;
        boolean hasSuffix = false;
        int endOfFirstWord = length;

        if (length < 3) return "NULL";

        String UL = null;
        String PL = null;

        // look for preposition
        String[] inputs = word.split("[\\s-]+");

        for (int i=0;i < inputs.length;i++) {
            System.out.println(inputs[i]);
            if (isPreposition(inputs[i]))
                hasSuffix = true;
        }

        if (hasSuffix) {
            //System.out.println("there's a suffix!");
            hasSuffix = true;
            endOfFirstWord = word.indexOf(" ");
            System.out.println(endOfFirstWord);
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

        UL = tempWord.substring(length-1,length);
        PL = tempWord.substring(length-2,length-1);
        //if (PL.equals("g")) System.out.println("yay");

        switch (UL) {
            case "a":
            case "e":
            case "i":
            case "u":
                tempWord = tempWord + "s";
                break;
            case "o":
                if (PL.equals("r") || PL.equals("d")) {
                    tempWord = tempWord + "es";
                }
                else {
                    tempWord = tempWord + "s";
                }
                break;
            case "b":
            case "d":
            case "f":
            case "g":
            case "m":
            case "p":
            case "r":
            case "w":
                tempWord = tempWord + "s";
                break;
            case "y":
                if (PL.equals("l") || PL.equals("g") || PL.equals("r") || PL.equals("x") ||
                        PL.equals("h") || PL.equals("n")) {
                    //System.out.println("Hello!");
                    tempWord = tempWord.substring(0,length-1) + "ies";
                } else {
                    tempWord = tempWord + "s";
                }
                break;
            case "n":
            case "l":
            case "t":
                tempWord = tempWord + "s";
                break;
            case "c":
                if (PL.equals("h")) {
                    word = word.substring(0,length-1) + "es";
                }
                break;
            case "h":
                if (PL.equals("s") || PL.equals("c")) {
                    tempWord = tempWord + "es";
                } else {
                    tempWord = tempWord + "s";
                }
                break;
            case "s":
                if (PL.equals("u") || PL.equals("i")) {
                    tempWord = tempWord + "es";
                }
                break;
            case "x":
                tempWord = tempWord + "es";
                break;
        }

        // if pluralizing the first word
        if (hasSuffix) {
            word = word.replaceFirst(word.substring(0,endOfFirstWord), tempWord);
        } else {
            word = tempWord;
        }
        return word;
    }

    public static ArrayList<String> numberify(ArrayList<String> list, Room room) {
        for (int i = 0;i < list.size();i++) {
            String s = list.get(i);
            int num;
            num = room.howManyOf(s);
            //print(num);
            if (num > 1) {
                s = util.GrammarGuy.pluralizeNoun(s);
                String x = util.NumbersToText.convert(num);
                s =  x + " " + s;
                //print(s);
            } else {
                s = addArticle(s);
            }
            list.add(i,s);
            list.remove(i+1);
        }
        return list;
    }

    public static String oxfordCommify(ArrayList<String> list) {
        //String result = "a ";
        String result = "";

        if (list.size() == 1) {
            result = result + list.get(0);
        }
        else if (list.size() > 1) {
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
        ArrayList<String> vowels = new ArrayList<String>();
        vowels.add("a"); vowels.add("e"); vowels.add("i"); vowels.add("o"); vowels.add("u");
        vowels.add("A"); vowels.add("E"); vowels.add("I"); vowels.add("O"); vowels.add("U");
        //print(vowels);
        for (int i=0;i < list.size();i++) {
            //print(list.get(i).substring(0,1));
            if (vowels.contains(list.get(i).substring(0,1))) {
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
        ArrayList<String> vowels = new ArrayList<String>();
        vowels.add("a"); vowels.add("e"); vowels.add("i"); vowels.add("o"); vowels.add("u");
        vowels.add("A"); vowels.add("E"); vowels.add("I"); vowels.add("O"); vowels.add("U");
        if (vowels.contains(word.substring(0,1))) {
            //print("found a vowel!");
            word = "an " + word;
        } else {
            word = "a " + word;
        }
        return word;
    }

    public static boolean isPreposition(String s) {
        ArrayList<String> preps = new ArrayList<String>();
        preps.add("to");
        preps.add("on");
        preps.add("with");
        preps.add("from");
        preps.add("in");
        preps.add("of");

        if(preps.contains(s)) return true;
        return false;
    }

}
