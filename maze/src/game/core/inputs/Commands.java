package game.core.inputs;

import game.core.positional.Coordinates;

import java.util.*;

import static util.Print.*;

public enum Commands {
    MOVE(new String[] {"move", "mv", "go"},
        "move/mv/go usage: move <direction> to move your character in the specified direction."
                + "\n" + "e.g. move north || move n|| mv southeast || go sw"
                + "\n" + "Valid directions are: " + Coordinates.CARDINALS + "."
        ),
    RETURN(new String[] {"return"},
        "return to the previous room."
        ),
    WHEREGO(new String[] {"where can i go?", "go?"},
        "where can i go?/go? usage: print out a list of valid directions to move towards."
        ),
    WHEREAM(new String[] {"where am i?", "am?"},
        "where am i?/am? usage: print out your current coordinate position."
        ),
    EXITMAZE(new String[] {"exit maze", "xx"},
        "exit maze/xx usage: exit the game and print out the path you have traveled."
        ),
    MYSTATUS(new String[] {"my status", "status"},
        "my status/status usage: prints out your current life total and other status information."
        ),
    DESCRIBE(new String[] {"describe", "desc"},
        "describe/desc usage: describe <object> to describe an object or the environment."
                + "\n" + "e.g. describe room || desc artifact"
        ),
    APPROACH(new String[] {"approach", "appr"},
        "approach/appr usage: approach <object> to attempt interaction with an object."
                + "\n" + "e.g. approach treasure chest || appr statue"
        ),
    FIGHT(new String[] {"fight", "f", "attack"},
        "fight/f usage: use to start combat with an opponent."
        ),
    TALK(new String[] {"talk"},
                "talk usage: talk to <object> to attempt to talk to an object.",
                "to"
        ),
    FLEE(new String[] {"flee"},
        "flee usage: use to attempt an escape from combat."
        ),
    INVENTORY(new String[] {"inventory", "inv", "got?"},
        "display the contents of the inventory and how many of each item is available."
        ),
    EQUIPPED(new String[] {"equipped"},
        "display what items are equipped."
        ),
    PICKUP(new String[] {"pick up", "pickup", "pkup"},
        "pick up item in Room and place it in the inventory."
        ),
    USE( new String[] {"use", "u"},
        "use item in inventory."
        ),
    COMBINE( new String[] {"combine", "comb"},
        "combine items in inventory."
        ),
    CONSUME( new String[] {"consume", "drink", "eat"},
        "consume item in inventory."
        ),
    DROP( new String[] {"drop"},
        "drop item in inventory."
            ),
    EQUIP( new String[] {"equip"},
        "equip item in inventory."
            ),
    UNEQUIP(new String[] {"unequip"},
        "unequip item and return it to inventory."
            ),
    ATTRIB( new String[] {"attributes", "attrib", "attribs"},
        "print out current player attributes."
            ),
    STATS( new String[] {"stats", "statistics"},
        "print out current player statistics."
            ),
    LEAVE(new String[] {""},
        "(leave trigger, not used by player)."
            ),
    TELEPORT(new String[] {"teleport"},
        "teleport!"
            ),
    DEATH(new String[] {"harakiri"}, "kill yourself."
            ),
    MAP(new String[] {"map"}, "show the map."
            ),
    BSW(new String[] {"bsw"}, "map cheat."
            ),
    HELP(new String[] {"help"},
        "help usage: help <command name> to print out details of using the command."
                + "\n" + "e.g. help move || help appr|| help xx"
        ),
    COMMANDS(new String[] {"commands", "cmds"},
        "command usage: print out the list of commands."
        ),
    SYSTEM(new String[] {"system", "sys"},
        "log // viewall // exitloc // centerloc // astar"
        ),
    ERROR(new String[] {"ERROR"},
        "Sorry, that command is not recognized."
        );

        private final List<String> matchInputs;
        private final String usage;
        private final String preposition;

        public String getPreposition() { return preposition; }

        public String getUsage() { return usage; }

        Commands(String[] matches, String usage) {
            this.matchInputs = Arrays.asList(matches);
            this.usage = usage;
            this.preposition = null;
        }

        Commands(String[] matches, String usage, String preposition) {
            this.matchInputs = Arrays.asList(matches);
            this.usage = usage;
            this.preposition = preposition;
        }

        public String toString() { return matchInputs.get(0); }

        public static void printList() {
            print("List of Commands:");
            print("-----------------------------------------------------------------------------");
            print("| move (mv) (go) + <<direction>> | where can i go? (go?) | where am i? (am?)|");
            print("| fight (f) | approach (appr) + <<object>> | describe (desc) + <<object>>   |");
            print("| talk + <<object>> | pickup item (pkup) | use item | consume item (consume)|");
            print("| combine <<item1>> with <<item2>> (comb)                                   |");
            print("| equip item | unequip item | inventory (got?) | equipped | status (status) |");
            print("| return |                                                                  |");
            print("| attributes (attrib | statistics (stats) | exit maze (xx) | help           |");
            print("-----------------------------------------------------------------------------");

            /*
            return new String[] { "move (mv) (go) + <<direction>>",
                    "where can i go? (go?)", "where am i? (am?)",
                    "fight (f)",
                    "approach (appr) + <<object>>",
                    "describe (desc) + <<object>>",
                    "talk + <<object>>",
                    "pickup item (pkup)", "use item (use)", "consume item (consume)", "equip item",
                    "unequip item",
                    "inventory (got?)",
                    "equipped",
                    "my status (status)",
                    "my attributes (attrib)",
                    "statistics (stats)",
                    "exit maze (xx)", "help"
            }; */
        }

        public static Commands get(String str) {
            for (Commands command : Commands.values()) {
                if (command.matchInputs.contains(str))
                  return command;
            }
          return ERROR; //if nothing matches
        }

        public static String getUsage(String str) {
            for (Commands command : Commands.values()) {
                if (command.matchInputs.contains(str))
                  return command.usage;
            }
          return ERROR.usage;
        }
}
