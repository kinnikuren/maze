package maze;

import static util.Print.*;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;

import maze.Maze.Room;

public class Narrator {

    private static String formatSpeech(String speech) {
        speech = "***" + speech.toUpperCase() + "***";
        return speech;
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
        ArrayList<String> monsterArray = new ArrayList<String>();
        ArrayList<String> itemArray = new ArrayList<String>();
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
                    monsterArray.add(i.name());
                } else if (i instanceof maze.AbstractItemPortable) {
                    itemArray.add(i.name());
                }
                //print(i.name());
            }

            monsters = oxfordCommify(monsterArray);
            items = oxfordCommify(itemArray);
            speech = player.name() + " notices several things in the room.  He sees " + monsters +
                    "!!!" + " " + "He also sees " + items + "!!!";
        }

        print(formatSpeech(speech));
    }


    public static String oxfordCommify(ArrayList<String> list) {
        String result = "a ";
        if (list.size() == 1) {
            result = list.get(0);
        }
        else if (list.size() > 1) {
            for (int i=0;i < list.size();i++) {
                if (i < list.size()-2)
                    result = result + list.get(i) + ", a ";
                else if (i == list.size()-2)
                    result = result + list.get(i) + ", and a ";
                else
                    result = result + list.get(i);
            }
        }
        return result;
    }
}
