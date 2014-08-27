package maze;

import static util.Print.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import maze.Maze.Room;

public class Narrator {
    private static Random rand = new Random();

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

        int trinketCount = 0;

        Collection<Interacter> interactions = room.interactions();
        List<String> monsterArray = new ArrayList<String>();
        List<String> itemArray = new ArrayList<String>();
        List<String> fixtureArray = new ArrayList<String>();

        if (room.isBarren()) {
            speech = player.name() + " is disappointed to find nothing of interest in the room.  " +
                    "The stone walls silently stare back at him.\n";
        }
        else {
            for (Interacter i : interactions) {
                //print("Hello!");
                if (i instanceof maze.Bestiary.Monster) {
                    //monsters = monsters + "a " + i.name() + ", ";
                    monsterArray = noDuplicates(monsterArray,i.name());
                } else if (i instanceof maze.AbstractItemPortable) {
                    //itemArray.add(i.name().toLowerCase());
                    //itemArray1.put(i.name(), value)
                    itemArray = noDuplicates(itemArray,i.name());
                    if (i instanceof maze.AbstractItemTrinket) {
                        trinketCount++;
                    }
                } else if (i instanceof maze.AbstractItemFixture) {
                    fixtureArray = noDuplicates(fixtureArray,i.name());
                }
                //print(i.name());
            }

            print(interactions.size());

            if (interactions.size() > 5) {
                quantity = "some";
            }
            if (interactions.size() > 20) {
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
            speech = leadIn(player) + player.name() + " notices " + quantity + " things in the room.  He sees " +
                    monsters + "!!!" + " " + "He also sees " + items + "!!!" + " " +
                    "Finally, he sees " + fixtures + "...";

            if (trinketCount > 8) speech = speech + " So many trinkets!  Glittering prizes!";

            for (Interacter i : interactions) {
                //print(room.howManyOf(i.name()) + " " + i.name());
                //print(util.NumbersToText.convert(room.howManyOf(i.name())) + " " + i.name());
            }
        }

        wordWrapPrint(formatSpeech(speech));
    }

    private static List<String> noDuplicates(List<String> list, String s) {
        if (!list.contains(s)) {
            list.add(s);
        }
      return list;
    }

    private static String leadIn(Player player) {
        List<String> list = new ArrayList<String>();

        String PN = player.name();
        int x = 8; //number of generic leadins

        list.add("Hark! ");
        list.add("Lo! ");
        list.add("Behold! ");
        list.add("Beware... ");
        list.add("The silence is deafening... ");
        list.add("Danger! ");
        list.add("Hi-diddle-dee-dee! ");
        list.add("The cheese stands alone. ");
        list.add("It's theeeee AAAAmmmazzzzzinnnnggggg MAZZZEEEEEE!!! ");

        list.add("What a lark! ");
        list.add("The story continues... ");
        list.add("Subsequently, ");
        list.add("Upon entering the room, ");
        list.add("Yawn...it's another room. ");
        list.add("Yowza! ");
        list.add(("What manner of foul beasts will " + PN + " encounter now?"));
        list.add((PN + " is relieved to find no Minotaur here. "));

        /*
        I wonder what the weather's like outside.  Anyway,
        I wonder...
        <name> is aMAZEd.
        This new area is surprisingly ROOMy.

        <name> takes a moment to ROOMinate on his next move.



        Everything in this room appears to be in disarray.  It's so MAZEy in here.

        If only <name> was a stone MAZEon, he could just dig his way out.  No suck luck, so,

        Of Maze and Man.  Ba Dum Tss!

        <name> is still holding onto his coins.  Don't be so MAZErly.

        <name> feels a rumble in his stomach.  If only the walls were made of MAIZE...

        <name> feels a rumble in his stomach.  Hopefully, there is a restroom in this maze.

        would never find a more wretched hive of scum and villainy.

        ...

        The door slowly creaks open and
        The shadows melt away and
        */

        if(player.location() != player.maze().center()) {
            return (list.get(rand.nextInt(list.size())));
        } else {
            return (list.get(rand.nextInt(x)));
        }
    }

    public static void postFightCommentary(Player player, Fighter fighter) {
        List<String> list = new ArrayList<String>();

        list.add("You fight like a dairy farmer.");
        list.add("You fight like a cow.");
        list.add("What a glorious battle!");
        list.add("What a mediocre battle!");
        list.add("Close call!");
        list.add("Dicey!");
        list.add("Whew, you made it!");
        list.add("Toasty!");
        list.add("Easy as pie.");
        list.add("Down to the wire.");
        list.add(("How amazing.  You killed a " + fighter.name()));


        String s = list.get(rand.nextInt(list.size()));

        wordWrapPrint(formatSpeech(s));
    }

    public static void respondToGo() {
        List<String> list = new ArrayList<String>();

        list.add("Which way is the right way?");
        list.add("Are you lost yet?");
        list.add("Do you know where you are?");
        list.add("WWPD?  P is for Perseus.");
        list.add("Where's Waldo?");

        String s = list.get(rand.nextInt(list.size()));

        wordWrapPrint(formatSpeech(s));
    }

    public static void main(String[] args) {
        //print(leadIn());

    }
}
