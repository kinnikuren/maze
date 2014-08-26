package maze;

import static util.Print.*;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;

import maze.Maze.Room;

public class Narrator {

    private static String formatSpeech(String speech) {
        speech = "***" + speech + "***";
        return speech;
        //sample comment- REMOVE THIS
    }

    public static void speakIntro(Player player) {
        String speech = "The hero, " + player.name() + ", awakens in a dark, dank room.  He tries to get" +
                " his bearings by taking a look around the room.";
        wordWrapPrint(formatSpeech(speech));
    }

    public static void talksAboutRoom(Player player, Room room) {
        String speech = "";
        String monsters = "";
        String items = "";
        String fixtures = "";

        String quantity = "a few";

        ArrayList<String> monsterArray = new ArrayList<String>();
        ArrayList<String> itemArray = new ArrayList<String>();
        ArrayList<String> fixtureArray = new ArrayList<String>();

        ArrayListMultimap<String, Integer> itemArray1 = ArrayListMultimap.create();

        if (room.isBarren()) {
            speech = player.name() + " is disappointed to find nothing of interest in the room.  " +
                    "The stone walls silently stare back at him.\n";
        }
        else {
            for (Interacter i : room.interactions) {
                //print("Hello!");
                if (i instanceof maze.Bestiary.Monster) {
                    //monsters = monsters + "a " + i.name() + ", ";
                    monsterArray = noDuplicates(monsterArray,i.name());
                } else if (i instanceof maze.AbstractItemPortable) {
                    //itemArray.add(i.name().toLowerCase());
                    //itemArray1.put(i.name(), value)
                    itemArray = noDuplicates(itemArray,i.name());
                    //print(inventory.get("bronze coin"));
                } else if (i instanceof maze.AbstractItemFixture) {
                    fixtureArray = noDuplicates(fixtureArray,i.name());
                }
                //print(i.name());
            }

            print(room.interactions.size());

            if (room.interactions.size() > 5) {
                quantity = "some";
            }
            if (room.interactions.size() > 20) {
                quantity = "many";
            }

            /*
            print(monsterArray);
            print(itemArray);
            print(fixtureArray);
            */

            monsterArray = util.GrammarGuy.numberify(monsterArray, room);
            itemArray = util.GrammarGuy.numberify(itemArray, room);
            fixtureArray = util.GrammarGuy.numberify(fixtureArray, room);

            /*
            print(monsterArray);
            print(itemArray);
            print(fixtureArray);
            */


            monsters = util.GrammarGuy.oxfordCommify(monsterArray);
            items = util.GrammarGuy.oxfordCommify(itemArray);

            fixtures = util.GrammarGuy.oxfordCommify(fixtureArray);
            speech = player.name() + " notices " + quantity + " things in the room.  He sees " +
                    monsters + "!!!" + " " + "He also sees " + items + "!!!" + " " +
                    "Finally, he sees " + fixtures + "!!!";

            for (Interacter i : room.interactions) {
                //print(room.howManyOf(i.name()) + " " + i.name());
                //print(util.NumbersToText.convert(room.howManyOf(i.name())) + " " + i.name());
            }
        }

        wordWrapPrint(formatSpeech(speech));
    }

    private static ArrayList<String> noDuplicates(ArrayList<String> list, String s) {
        if (!list.contains(s)) {
            list.add(s);
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
