package maze;

import static util.Print.*;
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
        if (room.isBarren()) {
            speech = player.name() + " is disappointed to find nothing of interest in the room.  " +
                    "The stone walls silently stare back at him.\n";
        }
        else {
            for (Interacter i : room.interactions) {
                //print("Hello!");
                if (i instanceof maze.Bestiary.Monster) {
                    monsters = monsters + "a " + i.name() + ", ";
                }
                //print(i.name());
            }

            speech = player.name() + " notices several things in the room.  He sees " + monsters;
        }

        print(formatSpeech(speech));
    }
}
