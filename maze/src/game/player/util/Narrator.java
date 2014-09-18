package game.player.util;

import static util.Print.*;
import static util.Loggers.*;
import static game.core.events.Priority.*;
import game.core.events.Fighter;
import game.core.events.Interacter;
import game.core.maze.Maze.Room;
import game.objects.units.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ArrayListMultimap;

public class Narrator {
    private int prevRandNum;
    private static Random rand = new Random();

    public Narrator() {
        this.prevRandNum = 0;
    }

    protected int getRand(int limit) {
        int num;
        do {
            num = rand.nextInt(limit);
        } while (num == this.prevRandNum);
        this.prevRandNum = num;
        return num;
    }

    private String formatSpeech(String speech) {
        speech = "***" + speech + "***";
        return speech;
        //sample comment- REMOVE THIS
    }

    public void speakIntro(Player player) {
        String speech = "The hero, " + player.name() + ", awakens in a dark, dank room.  He tries to get" +
                " his bearings by taking a look around the room.";
        wordWrapPrint(formatSpeech(speech));
    }

    public void talksAboutRoom(Player player, Room room) {
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
                if (i instanceof game.objects.units.Bestiary.Monster) {
                    //monsters = monsters + "a " + i.name() + ", ";
                    monsterArray = noDuplicates(monsterArray,i.name());
                } else if (i instanceof game.objects.items.AbstractItemPortable) {
                    //itemArray.add(i.name().toLowerCase());
                    //itemArray1.put(i.name(), value)
                    itemArray = noDuplicates(itemArray,i.name());
                    if (i instanceof game.objects.items.AbstractItemTrinket) {
                        trinketCount++;
                    }
                } else if (i instanceof game.objects.items.AbstractItemFixture) {
                    fixtureArray = noDuplicates(fixtureArray,i.name());
                }
                //print(i.name());
            }

            log(("Number of interactions: " + ((Integer)interactions.size()).toString()),LOW);

            if (interactions.size() == 0) {
                quantity = "zero";
            }
            if (interactions.size() == 1) {
                quantity = "one";
            }
            if (interactions.size() == 2) {
                quantity = "a couple";
            }
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

            monsterArray = game.core.inputs.GrammarGuy.numberify(monsterArray, room);
            itemArray = game.core.inputs.GrammarGuy.numberify(itemArray, room);
            fixtureArray = game.core.inputs.GrammarGuy.numberify(fixtureArray, room);

            /*
            print(monsterArray);
            print(itemArray);
            print(fixtureArray);
            */

            monsters = game.core.inputs.GrammarGuy.oxfordCommify(monsterArray);
            items = game.core.inputs.GrammarGuy.oxfordCommify(itemArray);
            fixtures = game.core.inputs.GrammarGuy.oxfordCommify(fixtureArray);

            List<String> genericArray = new ArrayList<String>();
            //genericArray.add("");
            genericArray.addAll(monsterArray);
            genericArray.addAll(itemArray);
            genericArray.addAll(fixtureArray);

            String generics = game.core.inputs.GrammarGuy.oxfordCommify(genericArray);

            String[] verbs = new String[]{"observes","astutely notes the things in","glances around",
                    "looks around","surveys"};


            speech = leadIn(player) + player.name() + " " + verbs[rand.nextInt(verbs.length)] +
                    " the room. " + "He sees " + generics + ".";

            /*
            if (monsterArray.size()>0) {
                speech += monsters + "! ";
            } else if (itemArray.size()>0) {
                speech += items + ". ";
            } else if (fixtureArray.size()>0) {
                speech += fixtures + ". ";
            } else {
                speech += "nothing...";
            }

            String fixtureComment = "";
            if (fixtureArray.size() > 0) fixtureComment = "Finally, he sees " + fixtures + "...";

            speech += fixtureComment;
            */

            if (trinketCount > 8) speech = speech + " So many trinkets!  Glittering prizes!";

            for (Interacter i : interactions) {
                //print(room.howManyOf(i.name()) + " " + i.name());
                //print(util.NumbersToText.convert(room.howManyOf(i.name())) + " " + i.name());
            }
        }

        wordWrapPrint(formatSpeech(speech));
    }

    private List<String> noDuplicates(List<String> list, String s) {
        if (!list.contains(s)) {
            list.add(s);
        }
      return list;
    }

    private String leadIn(Player player) {
        List<String> list = new ArrayList<String>();
        ArrayListMultimap<String, String> leadInMap = ArrayListMultimap.create();

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
        list.add(("What manner of foul beasts will " + PN + " encounter now? "));
        list.add((PN + " is relieved to find no Minotaur here. "));
        list.add((PN + " is aMAZEd. "));
        list.add("This new area is surprisingly ROOMy. ");
        list.add((PN + " takes a moment to ROOMinate on his next move. "));
        list.add("Everything in this room appears to be in disarray.  It's so MAZEy in here. ");
        list.add(("If only " + PN + " was a stone MAZEon, he could just dig his way out. "
                + "No suck luck, so, "));
        list.add("Of Maze and Man. Ba Dum Tss! ");
        //list.add((PN + " is still holding onto his coins. Don't be so MAZErly.");
        list.add((PN + " feels a rumble in his stomach. If only the walls were made of MA(I)ZE. "));
        list.add((PN + " feels a rumble in his stomach. Hopefully, there is a restroom in "
                + "this maze. "));
        list.add("The door slowly creaks open and ");
        list.add("The shadows melt away and ");

        leadInMap.putAll("visited", Arrays.asList(
                PN + " decides to backtrack. ",
                "Haven't you been here before? ",
                "This room looks very familiar... ",
                PN + " seems to be going in circles. ",
                ("Must've been a dead end. Or " + PN + " is completely lost. ")));

        /*
        I wonder what the weather's like outside.  Anyway,
        I wonder...

        would never find a more wretched hive of scum and villainy.

        ...
        */

        // if player is not in the first room
        if(player.location() != player.maze().center()) {
            if (player.getRoom().hasBeenVisited()) { // if player has already visited the room
                return leadInMap.get("visited").get(getRand(leadInMap.size()));
            }
            return (list.get(getRand(list.size())));
        } else { // if player is still in the first room
            return (list.get(getRand(x)));
        }
    }

    public void postFightCommentary(Player player, Fighter fighter) {
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


        String s = list.get(getRand(list.size()));

        wordWrapPrint(formatSpeech(s));
    }

    public void respondToGo() {
        List<String> list = new ArrayList<String>();

        list.add("Which way is the right way?");
        list.add("Are you lost yet?");
        list.add("Do you know where you are?");
        list.add("WWPD?  P is for Perseus.");
        list.add("Where's Waldo?");

        String s = list.get(getRand(list.size()));

        wordWrapPrint(formatSpeech(s));
    }

    public static void main(String[] args) {
        //print(leadIn());
        /*
        Narrator Narrator = new Narrator();
        for (int i=0;i<100;i++) {
            print(Narrator.getRand(10));
        }
        */
    }
}
