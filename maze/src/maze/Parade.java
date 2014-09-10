package maze;

import java.util.Random;
import java.util.Scanner;
import java.lang.reflect.*;

import static util.Print.*;
import static maze.References.*;

//Parade of Horribles
public class Parade {
    private String input;

    /*
    public void testPrint(Player player,String encounterName) {
        player.tracker().track(encounterName);
        System.out.println("Hello!");
    }
    */

    public void zombieEncounter(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("You hear moaning. It's a zombie! It lunges at you! How do you react?");
        wordWrapPrint("A) Kill it.");
        wordWrapPrint("B) Bite it.");

        input = MultipleChoiceInputHandler.run(2);

        if (input.equals("A")) {
            if (!player.skillCheck(STR, 4, 1)) {
                wordWrapPrint("You weren't strong enough to kill it with one blow. Before you "
                        + "finish it off, the zombie bites you on the arm.");
                player.setStatus("the zombie virus");
            } else {
                wordWrapPrint("You smash its head to pieces. Zombie apocalypse averted.");
            }
        } else if (input.equals("B")) {
            if (!player.skillCheck(DEX,4,1)) {
                wordWrapPrint("You are not quick enough to bite the zombie before it bites you.");
                player.setStatus("the zombie virus");
            } else {
                wordWrapPrint("Zombies are so slow. You easily dodge its attack and bite it. The "
                        + "zombie has no reaction. Fortunately, the zombie seems to think you're "
                        + "a kindred spirit and wanders off. However, you now have a mouthful of "
                        + "zombie flesh. What made you think that was a good idea?");
                player.setStatus("the zombie virus");
            }
        }
    }

    public void gym(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("A beefy bodybuilder is lifting some weights in a...gym? You decide not to "
                + "question it further. He points at a "
                + "squat rack and grunts.");
        wordWrapPrint("A) Do three sets, ten reps each.");
        wordWrapPrint("B) Take down the pansy man.");

        input = MultipleChoiceInputHandler.run(2);

        if (input.equals("A")) {
            if (!player.skillCheck(STR, 2, 1)) {
                wordWrapPrint("You collapse under the weight. The bodybuilder laughs and helps you"
                        + "up.");
            } else {
                wordWrapPrint("You knock'em out. You feel stronger.");
                player.skillChange(STR, 1);
            }
        } else if (input.equals("B")) {
            if (!player.skillCheck(STR, 4, 2)) {
                wordWrapPrint("The bodybuilder hands you the beating of a lifetime.");
            } else {
                wordWrapPrint("You defeat the bodybuilder handily and flex over his prone body. "
                        + "The exertion was good exercise.");
                player.skillChange(STR, 2);
            }
        }
    }

    public void poisonFountain(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("There is a beautiful water fountain in this room. And you are rather "
                + "thirsty.");
        wordWrapPrint("A) Drink from the fountain.");
        wordWrapPrint("B) Leave it alone.");

        input = MultipleChoiceInputHandler.run(2);

        if (input.equals("A")) {
            if (!player.skillCheck(INT, 0, 1)) {
                wordWrapPrint("You slurp up the delicious water and it quenches your thirst. "
                        + "Ahhh...");
                player.setStatus("dysentery");
            } else {
                wordWrapPrint("You are smart enough to question the conveniently placed water "
                        + "fountain. With MacGyver-like skills, you jury-rig a water filter "
                        + "from the random detritus in the room and then take a drink.");
                player.addHP(5);
            }
        } else if (input.equals("B")) {
            wordWrapPrint("You decide to leave the fountain alone. You're stuck in the maze and "
                    + "there isn't a bathroom in sight.");
        }
    }

    public void tentacleMonster(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("The first thing you notice is this room is full of non-Euclidean "
                + "architecture. As you soon you realize what this means, you feel a tentacle "
                + "wrapping itself around your leg. You see an indescribably horrifying monster "
                + "in front of you. The nightmare threatens to envelop you.");
        wordWrapPrint("A) Attack the monster.");
        wordWrapPrint("B) It's just a bad dream.");
        wordWrapPrint("C) Run away screaming.");

        input = MultipleChoiceInputHandler.run(3);

        if (input.equals("A")) {
            if (!player.skillCheck(INT, 3, 2)) {
                wordWrapPrint("You flay wildly at the nightmarish creature. You realize you're "
                        + "you're hitting yourself after a few of your blows have connected.");
            } else {
                wordWrapPrint("Because of your high intellect, you realize it's all an "
                        + "illusion. You stop yourself mid-swing and untangle the rope from "
                        + "your leg.");
            }
        } else if (input.equals("B")) {
            if (!player.skillCheck(INT, 2, 2)) {
                wordWrapPrint("You rationalize that this all must be an illusion. It takes a great "
                        + "deal of effort and you emerge from the nightmare, sweating and shaking.");
            } else {
                wordWrapPrint("You easily dispel the illusion and the room returns to normal.");
            }
        } else if (input.equals("C")) {
            wordWrapPrint("You successfully run away and what was seen cannot be unseen.");
            player.loseHP(2);
        }
    }

    public void insultSwordFight(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("A gangly, blonde swordsman stands in your way. He says with a smile, "
                + "'I'm Guybrush Threepwood, mighty pirate and renowned insult swordfighter!'");
        wordWrapPrint("'I once owned a dog that was smarter then you!'");
        wordWrapPrint("A) I'm rubber and you're glue!");
        wordWrapPrint("B) Oh yea!?");
        wordWrapPrint("C) Your mom!");
        if (player.skillCheck(INT,0,1)) {
            wordWrapPrint("D) He must have taught you everything you know.");
            input = MultipleChoiceInputHandler.run(4);
        } else {
            input = MultipleChoiceInputHandler.run(3);
        }

        if (input.equals("A") || input.equals("B") || input.equals("C")) {
            wordWrapPrint("The swordsman raises an eyebrow, shrugs, and walks away. On the other "
                    + "hand, "
                    + "his insult hurt your feelings to such an extent that you feel physically "
                    + "weaker.");
            player.loseHP(2);
        } else if (input.equals("D")) {
            wordWrapPrint("The swordsman responds, 'Not bad! Maybe you can join my crew once "
                    + "you're out of here.' You knew you were smarter than his dog.");
        }
    }

    public void mathProf(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("You seem to be in a classroom. An old, professor-looking man is writing "
                + "on a chalkboard.");
        wordWrapPrint("On the board, you see:");
        wordWrapPrint("Solve for x: 3x + 5 = 11");
        wordWrapPrint("A) 2.3");
        wordWrapPrint("B) 0");
        wordWrapPrint("C) 2");
        wordWrapPrint("D) This is so easy. Solve it in your head and say the answer.");
        input = MultipleChoiceInputHandler.run(4);

        if (input.equals("A") || input.equals("B")) {
            wordWrapPrint("The old professor shakes his head in disappointment.");
            player.setStatus("low self-esteem");
        } else if (input.equals("C")) {
            wordWrapPrint("The professor nods approvingly and tosses you an apple.");
            player.addToInventory(new Consumables.Apple());
        }
        else if (input.equals("D")) {
            if (!player.skillCheck(INT,1,1)) {
                wordWrapPrint("You are not as smart as you think you are. The professor "
                        + "throws an eraser at you.");
                player.loseHP(1);
            } else {
                wordWrapPrint("You confidently say '2.' The professor smiles and gives you an "
                        + "apple");
                player.addToInventory(new Consumables.Apple());
            }
        }
    }

    public void indyBallTrap(Player player, String encounterName) {
        player.tracker().track(encounterName);
        wordWrapPrint("You are blinded by a brilliant light. Resting on a stone dais, there is "
                + "a beautiful golden statue.");
        wordWrapPrint("A) Take the statue regardless of the consequences.");
        wordWrapPrint("B) Fill a bag with sand such that the bag is approximately the same weight as "
                + "the statue. Swap it as quickly as you can.");
        wordWrapPrint("C) Leave it alone.");

        input = MultipleChoiceInputHandler.run(3);

        if (input.equals("A")) {
            wordWrapPrint("As you greedily grab the statue, you feel the entire room shake. "
                    + "The ceiling opens up and an enormous stone ball drops down!");
            if (player.getDex() > player.getStr()) {
                if (!player.skillCheck(DEX,100,3)) {
                    wordWrapPrint("Your attempt to dodge the ball is a miserable failure. You are "
                            + "crushed into paste.");
                } else {
                    wordWrapPrint("In an amazing display of acrobatics, you leap out of the way!");
                    player.addToInventory(new Trinkets.GoldenStatue());
                }
            } else {
                if (!player.skillCheck(STR, 100, 10)) {
                    wordWrapPrint("Foolishly, you try to catch the 10-ton ball with your hands. You "
                            + "are ground into a meaty jam.");
                } else {
                    wordWrapPrint("With your inhuman strength, you catch the ball and toss it aside.");
                    player.addToInventory(new Trinkets.GoldenStatue());
                }
            }
        } else if (input.equals("B")) {
            if (!player.skillCheck(DEX,100,2)) {
                wordWrapPrint("Thievery is not your true calling. Don't quit your day job. A "
                        + "giant stone ball drops from the ceiling and crushes you to death.");
            } else {
                wordWrapPrint("Quick hands allow you to swap the statue with the bag of sand. "
                        + "The dais doesn't notice a thing.");
                player.addToInventory(new Trinkets.GoldenStatue());
            }
        } else if (input.equals("C")) {
            wordWrapPrint("You decide to leave the statue alone. The dais recedes into the ground, "
                    + "never to be seen again.");
        }
    }

    public void spikeTrap(Player player, String encounterName) {
        player.tracker().track(encounterName);
        print("As you step into the room, you hear a faint click. It's a trap!");
        print("A) Try to deftly avoid the trap.");
        print("B) You're a tough guy. You can take it.");

        input = MultipleChoiceInputHandler.run(2);

        if (input.equals("A")) {
            //maybe player method to take hp penalty
            if (!player.skillCheck(DEX,4,1)) {
                wordWrapPrint("Too bad you're not as dexterous as you thought. While attempting to "
                        + "flip away from the spike trap, you hurt yourself more.");
            } else {
                wordWrapPrint("You quickly sidestep way from the spikes. So smooth!");
            }
            //if (!player.dexCheck()) player.loseHP(4);
        } else if (input.equals("B")) {
            //if (!player.strCheck()) player.loseHP(2);
            player.loseHP(2);
            wordWrapPrint("You steel yourself for the trap. Spikes emerge from the ground "
                        + "and pierce your foot.");
        }
    }
    /*
    public static void main(String[] args) {
        //how to retrieve random encounter?
        Random rand = new Random();
        int x = rand.nextInt(10); // generates 0 to 9
        Parade Parade = new Parade();
        //Player player = new Player()

        try {
            Class cls = Class.forName("maze.Parade");

            Constructor c[] = cls.getConstructors();
            System.out.println("Success!");
            for(int i = 0; i < c.length; i++) {
                System.out.println("Success");
               System.out.println(c[i]);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        for (Method m:Parade.class.getDeclaredMethods()) {
            System.out.println(m.getName());
            try {
                m.invoke(Parade);
            } catch (Exception e) {
                 System.out.println("Exception: " + e);
            }
        }

        //new randEncounter()
        for (Class c:Parade.class.getClasses()) {
            System.out.println(c.getName());
            try {
                //c.getMethod("testPrint", null);
            } catch (Exception e) {
                System.out.println("Exception");
            }
        }

    }
    */

}
