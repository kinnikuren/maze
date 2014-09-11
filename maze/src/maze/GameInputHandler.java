package maze;

import java.util.*;
import static util.TextParser.ParsedCommand;
import static util.TextParser.parseCommand;
import maze.Maze.Room;
import static util.Print.*;
import static maze.Commands.*;
import static util.Utilities.*;
import static util.Loggers.*;

public final class GameInputHandler {
    private GameInputHandler() { } //no instantiation

    public static void run(String input, Player you, Maze maze, List<Coordinate> path) {
        input = input.toLowerCase().trim();
        ParsedCommand cmd = parseCommand(input);
        String leadInput = cmd.command;
        String arg = cmd.object;
            log(leadInput);
            log(arg);

        String[] inputs = input.split("\\s+");

        /*
        int numSpaces = 0;
        int prevSpacePos = 0;
        int nextSpacePos = 0;

        //finds number of spaces between words
        do {
            prevSpacePos = nextSpacePos;
            nextSpacePos = input.indexOf(' ',prevSpacePos+1);
            //print("Next space position: " + nextSpacePos);
            //print("Previous space position: " + prevSpacePos);
            if (nextSpacePos != -1 && (nextSpacePos-prevSpacePos != 1)) {
                numSpaces++;
            }
        } while(nextSpacePos != -1);

        print("Number of spaces: " + numSpaces);
        String[] inputs = input.split("\\s+");
        String leadInput = input.split("\\s+", numSpaces+1)[0];
        print("Lead Input = " + leadInput);
        //print("Input = " + inputs);
        String arg = null;
        if (inputs.length > 1) {
            //arg = inputs[1];
            arg = "";
            for (int i = 1; i < inputs.length; i++) {
                //print(inputs[i]);
                arg += inputs[i];
            }
        }
        print("Arg = " + arg);

        */

        Commands fullCmd = get(input); //entire trimmed input, for checking intransitive actions
        Commands leadCmd = get(leadInput); //first word, delimited by space, for checking transitive actions (that allow or require arguments)


        if (check(leadCmd).in(APPROACH, FIGHT, PICKUP, TALK)) {
            if (arg == null) {
                if (check(leadCmd).in(TALK)) {
                    print("\nYou talk to yourself.  You find the conversation unstimulating.");
                }
                else {
                    print("What do you want to " + leadCmd + "?");
                }
            }
            else {
              performAction(you, leadCmd, arg, you.getRoom());
            }
        }
        else if (check(leadCmd).in(EQUIP, DROP, CONSUME, USE)) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            }
            else {
                performAction(you, leadCmd, arg, you.getInventory());
            }
        }
        else if (leadCmd == UNEQUIP) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            }
            else {
                performAction(you, leadCmd, arg, you.getPaperDoll());
            }
        }
        else if (leadCmd == DESCRIBE) {
            if (arg == null) {
                Room r = maze.getRoom(you.location());
                r.describeRoom();
            }
            else if (you.getRoom().contains(arg)){
                log("Room contains " + arg);
                performAction(you, leadCmd, arg, you.getRoom());
            }
            else if (you.getInventory().contains(arg)){
                log("Inventory contains " + arg);
                performAction(you, leadCmd, arg, you.getInventory());
            }
        }
        else if (leadCmd == MOVE) {
          if (arg == null) {
              print("Please supply a direction to move in.");
              you.narrator().respondToGo();
              }
          else {
            Cardinals direction = Cardinals.get(arg);
                    log("direction supplied was: " + direction, Priority.DORMANT); //debug
            Coordinate oldLocation = you.location();

            if (direction == null) {
                print("That's not a valid direction! Try again. "
                        + "(for a list of valid directions, type: help move)");
            }
            else if (you.move(direction, maze)) { //returns true if the move is valid
              print("You have moved " + direction + ".");
              path.add(oldLocation);
              Room room = maze.getRoom(you.location());

              if (you.getInventory().contains("Enc-None")) {
                  print("\nRandom encounters begone! Enc-None shields you.\n");
              } else {
                  EncounterGenerator.run(you);
              }

              Events.fire(you, MOVE, room);
              you.narrator().talksAboutRoom(you, room);
              if (!room.isVisited) {
                  you.stats().update("roomsExplored");
                  Statistics.globalUpdate("roomsExplored");
              }
              room.visitedBy(you);

              room.describeRoom();

              if(you.isAlive()) {
                print("\nYou can go in the following directions: ");
                you.whereCanIGo(maze);
                if (you.location().equals(maze.exit())) {
                  print("You have found the exit to the maze! "
                          + "You may exit this program at your convenience.");
                }
              }
            }
            else { //if you.move(direction) returned false
                print("There's no path to the " + direction + " from this room.");
            }
          }
        }
        else if (leadCmd == ATTRIB) {
            you.printAttribs();
        }
        else if (leadCmd == STATS) {
            Statistics.globalPrintStats();
        }
        else if (leadCmd == HELP) {
            if (arg == null) print(HELP.getUsage());
            else {
                Commands helpCmd = get(arg);
                //prints error message if the command wasn't recognized.
                print(helpCmd.getUsage());
            }
        }
        else if (leadCmd == SYSTEM) {
            if (arg == null) print(SYSTEM.getUsage());
            else {
              if (inputs[1].equals("log")) {
                if (inputs.length == 2) print("higher // lower // current // priority");
                else if (inputs.length == 3 && inputs[2].equals("higher")) {
                  programLog.higher();
                  print("new program log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3 && inputs[2].equals("lower")) {
                  programLog.lower();
                  print("new program log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3 && inputs[2].equals("current")) {
                  print("current log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3) {
                  Priority p = Priority.get(inputs[2]);
                  if (p != null) {
                    programLog.setLevel(p);
                    print("new program log level: " + programLog.getLevel());
                  }
                  else print("that priority level is not recognized.");
                }
              }
            }
        }
        else if(fullCmd == INVENTORY) {
            if(you.getInventory().isEmpty()) { print("You have nothing in your inventory."); }
            else you.printInventory();
        }
        else if(fullCmd == EQUIPPED) {
            you.printPaperDoll();
        }
        else if (fullCmd == WHEREGO) {
            print("\nYou can go in the following directions: ");
            you.whereCanIGo(maze);
        }
        else if (fullCmd == WHEREAM) {
            you.printLocation();
        }
        else if (fullCmd == MYSTATUS) {
            you.printStatus();
        }
        else if(fullCmd == EXITMAZE) {
            path.add(you.location());
            print("\nThank you for playing in the Maze.");
        }
        else if(fullCmd == COMMANDS) {
            print("\nList of commands:");
            for(String str : printList()) print(str);
        }
        else print("\nThis instruction is invalid. Type 'commands' for a list of commands.");
    }

    public static void performAction(Player you, Commands action, String object, Stage stage) {
        //Boolean result = r.respondsTo(you, trigger, arg);
        Boolean result = Events.fire(you, action, object, stage);
        String preposition = action.getPreposition() == null ? "" : action.getPreposition();
        //Grammar correction
        if (result == null) print("There isn't a " + object + " to " + action + preposition + " in " + stage.getName() + ".");
        else if (result == false) {
            print("You can't " + action + preposition + " the " + object + ".");
        }
    }
}
