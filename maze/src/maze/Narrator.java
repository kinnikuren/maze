package maze;

import static util.Print.*;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;

import maze.Maze.Room;

public class Narrator {

    private static String formatSpeech(String speech) {
        speech = "***" + speech.toUpperCase() + "***";
        return speech;
        //sample comment- REMOVE THIS
    }

    public static void speakIntro(Player player) {
        String speech = "The hero, " + player.name() + ", awakens in a dark, dank room.  He tries to get" +
                " his bearings by taking a look around the room.\n";
        print(formatSpeech(speech));
    }

    public static void talksAboutRoom(Player player, Room room) {
        String speech = "";
        String monsters = "";
        String items = "";

        String fixtures = "";
        ArrayList<String> monsterArray = new ArrayList<String>();
        ArrayList<String> itemArray = new ArrayList<String>();
        ArrayList<String> fixtureArray = new ArrayList<String>();

        //ArrayListMultimap<String, Integer> monsterArray = ArrayListMultimap.create();

        if (room.isBarren()) {
            speech = player.name() + " is disappointed to find nothing of interest in the room.  " +
                    "The stone walls silently stare back at him.\n";
        }
        else {
            for (Interacter i : room.interactions) {
                //print("Hello!");
                if (i instanceof maze.Bestiary.Monster) {
                    //monsters = monsters + "a " + i.name() + ", ";
                    monsterArray.add(i.name().toLowerCase());
                } else if (i instanceof maze.AbstractItemPortable) {
                    itemArray.add(i.name().toLowerCase());
                } else if (i instanceof maze.AbstractItemFixture) {
                    fixtureArray.add(i.name().toLowerCase());
                }
                //print(i.name());
            }

            monsters = oxfordCommify(monsterArray);
            items = oxfordCommify(itemArray);

            fixtures = oxfordCommify(fixtureArray);
            speech = player.name() + " notices several things in the room.  He sees " + monsters +
                    "!!!" + " " + "He also sees " + items + "!!!" + " " + "Finally, he sees " +
                    fixtures + "!!!";
        }

        print(formatSpeech(speech));
        wordWrapPrint(formatSpeech(speech));
    }


    public static String oxfordCommify(ArrayList<String> list) {
        //String result = "a ";
        String result = "";
        list = addArticles(list);
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
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");
        print(vowels);
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

    public static void wordWrapPrint(String text) {
        int i=0;
        int j;
        if (text.length() > 100) {
            do {
                j = i + 100;
                if (text.charAt(j) != ' ') {
                    j = text.indexOf(" ",j);
                }
                if (j == -1) {
                    break;
                }
                print(text.substring(i,j));
                i = j+1;
            } while(text.length()-i > 100);
            print(text.substring(i,text.length()));
        }
    }
}
